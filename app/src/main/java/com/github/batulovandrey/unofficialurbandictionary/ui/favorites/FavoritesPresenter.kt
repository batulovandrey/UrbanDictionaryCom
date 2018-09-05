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
                                } else {
                                    mvpView?.showPlaceHolder()
                                }
                            }
                        })
    }

    override fun clearList() {
        compositeDisposable.add(
                dataManager.getFavoritesDefinitions()
                        .subscribeOn(Schedulers.io())
                        .flatMapIterable { it }
                        .doOnNext { it.favorite = 0 }
                        .map { dataManager.saveDefinition(it).subscribe()}
                        .subscribe {
                            dataManager.deleteFavoritesDefinitions()
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe { mvpView?.showPlaceHolder() }
                        })
    }

    override fun onItemClick(position: Int) {
        var selectDefinition = definitionAdapter.getDefinitionByPosition(position)

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
}