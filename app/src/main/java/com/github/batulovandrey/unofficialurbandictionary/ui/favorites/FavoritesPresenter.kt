package com.github.batulovandrey.unofficialurbandictionary.ui.favorites

import com.github.batulovandrey.unofficialurbandictionary.adapter.DefinitionAdapter
import com.github.batulovandrey.unofficialurbandictionary.adapter.DefinitionClickListener
import com.github.batulovandrey.unofficialurbandictionary.data.DataManager
import com.github.batulovandrey.unofficialurbandictionary.presenter.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FavoritesPresenter<V : FavoritesMvpView> @Inject constructor(dataManager: DataManager,
                                                                   compositeDisposable: CompositeDisposable)
    : FavoritesMvpPresenter<V>,
        BasePresenter<V>(dataManager, compositeDisposable),
        DefinitionClickListener {

    private lateinit var definitionAdapter: DefinitionAdapter

    override fun onAttach(mvpView: V) {
        super.onAttach(mvpView)
        loadData()
    }

    override fun loadData() {
        compositeDisposable.add(
                dataManager.getFavoritesDefinitions()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe {
                            if (isViewAttached()) {
                                if (it.isNotEmpty()) {
                                    definitionAdapter = DefinitionAdapter(it, this)
                                    mvpView?.setDefinitionAdapter(definitionAdapter)
                                    mvpView?.showData()
                                }
                            }
                        })
    }

    override fun clearList() {
        compositeDisposable.add(
                dataManager.deleteAllDefinitions()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { mvpView?.showPlaceHolder() }
        )
    }

    override fun onItemClick(position: Int) {
        dataManager.setActiveDefinition(definitionAdapter.getDefinitionByPosition(position))
        mvpView?.showDetailFragment()
    }
}