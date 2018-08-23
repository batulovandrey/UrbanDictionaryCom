package com.github.batulovandrey.unofficialurbandictionary.ui.main

import com.github.batulovandrey.unofficialurbandictionary.adapter.DefinitionAdapter
import com.github.batulovandrey.unofficialurbandictionary.adapter.DefinitionClickListener
import com.github.batulovandrey.unofficialurbandictionary.adapter.QueriesAdapter
import com.github.batulovandrey.unofficialurbandictionary.adapter.QueriesClickListener
import com.github.batulovandrey.unofficialurbandictionary.data.DataManager
import com.github.batulovandrey.unofficialurbandictionary.data.db.model.SavedUserQuery
import com.github.batulovandrey.unofficialurbandictionary.presenter.BasePresenter
import com.github.batulovandrey.unofficialurbandictionary.utils.convertToDefinitionList
import io.reactivex.Single
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

                    } else {

                        return@subscribe

                    }
                }?.let { compositeDisposable.add(it) }
    }

    override fun getData(text: String) {
        dataManager.clearMap()
        mvpView?.showLoading()
        compositeDisposable.add(dataManager.getData(text)
                .subscribeOn(Schedulers.io())
                .flatMap {
                    Single.fromCallable { it.definitionResponses }
                            .subscribeOn(Schedulers.io())
                }
                .toObservable()
                .map { it.convertToDefinitionList() }
                .doOnNext {
                    it.forEach { definition ->
                        dataManager.saveDefinition(definition)
                                .subscribeOn(Schedulers.io())
                                .subscribe { id ->
                                    dataManager.putDefinitionToMap(id, definition)
                                }
                    }
                }.observeOn(AndroidSchedulers.mainThread())
                .subscribe {

                    mvpView?.hideLoading()

                    if (isViewAttached()) {
                        definitionAdapter = DefinitionAdapter(it, this)
                        definitionAdapter?.let { adapter -> mvpView?.setDefinitionAdapter(adapter) }
                        mvpView?.showDefinitions()
                    }

                })
    }

    override fun saveUserQuery(query: String) {
        compositeDisposable.add(
                dataManager.saveQuery(SavedUserQuery(null, query))
                        .subscribeOn(Schedulers.io())
                        .subscribe())
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
        val selectDefinition = dataManager.getSavedListOfDefinition()[position]
        val id = dataManager.getDefinitionId(selectDefinition)

        compositeDisposable.add(dataManager.getDefinitionById(id)
                .subscribeOn(Schedulers.io())
                .subscribe({ definition ->
                    definition?.let { dataManager.setActiveDefinition(it) }
                },
                        { _ ->
                            dataManager.setActiveDefinition(selectDefinition)
                        }))

        mvpView?.showDetailFragment()
    }

    override fun onQueryClick(position: Int) {
        val userQuery = queriesAdapter?.getQuery(position)
        val text = userQuery?.text
        text?.let { dataManager.getData(it) }
    }

    override fun deleteQueryFromRealm(position: Int) {
        val userQuery = queriesAdapter?.getQuery(position)
        userQuery?.let { dataManager.deleteQuery(it) }
    }
}