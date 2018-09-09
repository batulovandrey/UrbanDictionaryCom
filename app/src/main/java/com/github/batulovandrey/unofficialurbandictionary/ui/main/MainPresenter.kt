package com.github.batulovandrey.unofficialurbandictionary.ui.main

import com.github.batulovandrey.unofficialurbandictionary.R
import com.github.batulovandrey.unofficialurbandictionary.adapter.DefinitionAdapter
import com.github.batulovandrey.unofficialurbandictionary.adapter.DefinitionClickListener
import com.github.batulovandrey.unofficialurbandictionary.adapter.QueriesAdapter
import com.github.batulovandrey.unofficialurbandictionary.adapter.QueriesClickListener
import com.github.batulovandrey.unofficialurbandictionary.data.DataManager
import com.github.batulovandrey.unofficialurbandictionary.data.db.model.SavedUserQuery
import com.github.batulovandrey.unofficialurbandictionary.presenter.BasePresenter
import com.github.batulovandrey.unofficialurbandictionary.utils.convertToDefinitionList
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainPresenter<V : MainMvpView> @Inject constructor(dataManager: DataManager,
                                                         compositeDisposable: CompositeDisposable) :
        BasePresenter<V>(dataManager, compositeDisposable),
        MainMvpPresenter<V>,
        DefinitionClickListener,
        QueriesClickListener {

    private var definitionAdapter: DefinitionAdapter? = null
    private var queriesAdapter: QueriesAdapter? = null

    override fun onAttach(mvpView: V) {
        super.onAttach(mvpView)
        if (dataManager.getSavedListOfDefinition().isNotEmpty()) {
            definitionAdapter = DefinitionAdapter(dataManager.getSavedListOfDefinition(), this)
            mvpView.setDefinitionAdapter(definitionAdapter!!)
            mvpView.showDefinitions()
        }
    }

    override fun onViewInitialized() {
        dataManager.getDefinitions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (isViewAttached()) {

                        definitionAdapter = DefinitionAdapter(it, this)
                        definitionAdapter?.let { adapter -> mvpView?.setDefinitionAdapter(adapter) }
                        mvpView?.showDefinitions()
                        mvpView?.hideKeyboard()

                    } else {

                        return@subscribe

                    }
                }?.let { compositeDisposable.add(it) }
    }

    override fun getData(text: String) {
        ADS_COUNT.incrementAndGet()
        dataManager.clearMap()
        compositeDisposable.clear()
        mvpView?.showLoading()
        compositeDisposable.add(dataManager.getData(text)
                .subscribeOn(Schedulers.io())
                .flatMap {
                    Flowable.fromCallable { it.definitionResponses }
                            .subscribeOn(Schedulers.io())
                }
                .toObservable()
                .flatMap { Observable.fromArray(it.convertToDefinitionList()) }
                .map {
                    it.forEach { definition ->

                        dataManager.getDefinitions()
                                .subscribeOn(Schedulers.io())
                                .subscribe {
                                    if (it.contains(definition)) {
                                        dataManager.putDefinitionToSavedList(definition)
                                    } else {
                                        compositeDisposable.add(dataManager.saveDefinition(definition)
                                                .subscribeOn(Schedulers.io())
                                                .subscribe { _ ->
                                                    dataManager.putDefinitionToSavedList(definition)
                                                })
                                    }
                                }
                    }

                    definitionAdapter = DefinitionAdapter(dataManager.getSavedListOfDefinition(), this)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    mvpView?.hideLoading()
                    mvpView?.hideKeyboard()

                    definitionAdapter?.let { adapter ->
                        adapter.notifyDataSetChanged()
                        mvpView?.setDefinitionAdapter(adapter)
                    }
                    mvpView?.showDefinitions()

                },
                        {
                            mvpView?.showToast(R.string.error)
                            mvpView?.hideKeyboard()
                            mvpView?.hideLoading()
                            mvpView?.showSnackbar()
                        }))
    }

    override fun saveUserQuery(query: String) {
        compositeDisposable.add(dataManager.getAllQueries()
                .subscribeOn(Schedulers.io())
                .flatMapIterable { it }
                .filter { it.text.toLowerCase() == query.toLowerCase() }
                .toList()
                .toObservable()
                .subscribe {
                    if (it.isEmpty()) {
                        dataManager.saveQuery(SavedUserQuery(null, query))
                                .subscribeOn(Schedulers.io())
                                .subscribe()
                    }
                }
        )
    }

    override fun showSearch() {
        mvpView?.closeNavigationDrawer()
        mvpView?.showSearchFragment()
    }

    override fun showPopularWords() {
        mvpView?.closeNavigationDrawer()
        mvpView?.showPopularWordsFragment()
    }

    override fun showFavorites() {
        mvpView?.closeNavigationDrawer()
        mvpView?.showFavoritesFragment()
    }

    override fun showDetail() {
        mvpView?.closeNavigationDrawer()
        mvpView?.showDetailFragment()
    }

    override fun filterQueries(text: String) {
        compositeDisposable.add(dataManager.getAllQueries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapIterable { it }
                .filter { it.text.toLowerCase().contains(text.toLowerCase()) }
                .toList()
                .toObservable()
                .subscribe {
                    if (isViewAttached()) {

                        queriesAdapter = QueriesAdapter(it, this)
                        queriesAdapter?.let { adapter -> mvpView?.setQueriesAdapter(adapter) }
                        mvpView?.showQueries()

                    } else {

                        return@subscribe

                    }
                })
    }

    override fun onItemClick(position: Int) {
        var selectDefinition = dataManager.getSavedListOfDefinition()[position]

        compositeDisposable.add(dataManager.getDefinitions()
                .subscribeOn(Schedulers.io())
                .map {
                    val definition = it.findLast { item -> item == selectDefinition }
                    selectDefinition = definition!!
                    dataManager.setActiveDefinition(selectDefinition)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    mvpView?.showDetailFragment()
                })
    }

    override fun onQueryClick(position: Int) {
        val userQuery = queriesAdapter?.getQuery(position)
        val text = userQuery?.text
        text?.let {
            getData(text)
        }
    }

    override fun deleteQueryFromRealm(position: Int) {
        val userQuery = queriesAdapter?.getQuery(position)
        userQuery?.let {
            dataManager.deleteQuery(it)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe {
                        queriesAdapter?.removeQuery(position)
                        mvpView?.setQueriesAdapter(queriesAdapter!!)
                    }
        }
    }
}