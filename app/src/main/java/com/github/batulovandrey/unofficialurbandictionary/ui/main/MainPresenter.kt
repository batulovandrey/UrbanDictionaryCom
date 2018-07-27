package com.github.batulovandrey.unofficialurbandictionary.ui.main

import com.github.batulovandrey.unofficialurbandictionary.adapter.DefinitionAdapter
import com.github.batulovandrey.unofficialurbandictionary.adapter.DefinitionClickListener
import com.github.batulovandrey.unofficialurbandictionary.adapter.QueriesAdapter
import com.github.batulovandrey.unofficialurbandictionary.adapter.QueriesClickListener
import com.github.batulovandrey.unofficialurbandictionary.data.DataManager
import com.github.batulovandrey.unofficialurbandictionary.presenter.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainPresenter<V : MainMvpView>(dataManager: DataManager,
                                     compositeDisposable: CompositeDisposable) :
        BasePresenter<V>(dataManager, compositeDisposable),
        MainMvpPresenter<V>,
        DefinitionClickListener,
        QueriesClickListener {

    private var definitionAdapter: DefinitionAdapter? = null
    private var queriesAdapter: QueriesAdapter? = null

    override fun onViewInitialized() {

        compositeDisposable.add(dataManager.getDefinitions()
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
        compositeDisposable.add(dataManager.getDefinitions()
                .subscribe {
                    dataManager.setActiveDefinition(it.get(position))
                })
    }

    override fun onQueryClick(position: Int) {
        val userQuery = queriesAdapter?.getQuery(position)
        val text = userQuery?.query
        text?.let { dataManager.getData(it) }
    }

    override fun deleteQueryFromRealm(position: Int) {
        val userQuery = queriesAdapter?.getQuery(position)
        userQuery?.let { dataManager.deleteQuery(it) }
    }
}