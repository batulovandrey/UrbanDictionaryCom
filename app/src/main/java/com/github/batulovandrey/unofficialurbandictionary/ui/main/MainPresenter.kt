package com.github.batulovandrey.unofficialurbandictionary.ui.main

import com.github.batulovandrey.unofficialurbandictionary.adapter.DefinitionAdapter
import com.github.batulovandrey.unofficialurbandictionary.adapter.DefinitionClickListener
import com.github.batulovandrey.unofficialurbandictionary.bean.DefinitionResponse
import com.github.batulovandrey.unofficialurbandictionary.data.DataManager
import com.github.batulovandrey.unofficialurbandictionary.presenter.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainPresenter<V : MainMvpView>(dataManager: DataManager,
                                     compositeDisposable: CompositeDisposable) :
        BasePresenter<V>(dataManager, compositeDisposable),
        MainMvpPresenter<V>,
        DefinitionClickListener {

    private var definitionAdapter: DefinitionAdapter? = null
    private var activeDefinition: DefinitionResponse? = null

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

    override fun onItemClick(position: Int) {
        compositeDisposable.add(dataManager.getDefinitions()
                .subscribe {
                    activeDefinition = it.get(position)
                })
    }
}