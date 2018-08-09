package com.github.batulovandrey.unofficialurbandictionary.ui.main

import com.github.batulovandrey.unofficialurbandictionary.adapter.DefinitionAdapter
import com.github.batulovandrey.unofficialurbandictionary.adapter.DefinitionClickListener
import com.github.batulovandrey.unofficialurbandictionary.adapter.QueriesAdapter
import com.github.batulovandrey.unofficialurbandictionary.adapter.QueriesClickListener
import com.github.batulovandrey.unofficialurbandictionary.data.DataManager
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
        mvpView?.showLoading()
        compositeDisposable.add(dataManager.getData(text)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { Single.fromCallable { it.definitionResponses } }
                .toObservable()
                .map { it.convertToDefinitionList() }
                .subscribe {

                    it.forEach { definition ->
                        dataManager.saveDefinition(definition)
                                .subscribeOn(Schedulers.io())
                                .subscribe()
                    }

                    dataManager.saveCurrentListOfDefinition(it)

                    mvpView?.hideLoading()

                    if (isViewAttached()) {
                        definitionAdapter = DefinitionAdapter(it, this)
                        definitionAdapter?.let { adapter -> mvpView?.setDefinitionAdapter(adapter) }
                        mvpView?.showDefinitions()
                    }

                })
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
        compositeDisposable.add(dataManager.filterQueries(text).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
        dataManager.setActiveDefinition(selectDefinition)
        compositeDisposable.add(dataManager.getFavoritesDefinitions()
                .subscribeOn(Schedulers.io())
                .filter { it.contains(selectDefinition) }
                .subscribe {
                    if (it.isNotEmpty()) {
                        selectDefinition.favorite = 1
                    }
                })
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