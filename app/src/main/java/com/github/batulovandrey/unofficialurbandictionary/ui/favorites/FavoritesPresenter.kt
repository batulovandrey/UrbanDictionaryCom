package com.github.batulovandrey.unofficialurbandictionary.ui.favorites

import com.crashlytics.android.Crashlytics
import com.github.batulovandrey.unofficialurbandictionary.adapter.DefinitionAdapter
import com.github.batulovandrey.unofficialurbandictionary.adapter.DefinitionClickListener
import com.github.batulovandrey.unofficialurbandictionary.data.DataManager
import com.github.batulovandrey.unofficialurbandictionary.presenter.BasePresenter
import io.fabric.sdk.android.Fabric
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
                        .subscribe({
                            if (isViewAttached()) {
                                if (it.isNotEmpty()) {
                                    definitionAdapter = DefinitionAdapter(it, this)
                                    if (isViewAttached()) {
                                        mvpView?.setDefinitionAdapter(definitionAdapter)
                                        mvpView?.showData()
                                    }
                                } else {
                                    if (Fabric.isInitialized()) {
                                        Crashlytics.log("view is not attached")
                                    }
                                    if (isViewAttached()) {
                                        mvpView?.showPlaceHolder()
                                    }
                                }
                            }
                        },
                                {
                                    if (Fabric.isInitialized()) {
                                        Crashlytics.log("error load data")
                                    }
                                    if (isViewAttached()) {
                                        mvpView?.showPlaceHolder()
                                    }
                                }))
    }

    override fun clearList() {
        compositeDisposable.add(
                dataManager.getFavoritesDefinitions()
                        .subscribeOn(Schedulers.io())
                        .flatMapIterable { it }
                        .doOnNext { it.favorite = 0 }
                        .map { dataManager.saveDefinition(it).subscribe() }
                        .subscribe({
                            dataManager.deleteFavoritesDefinitions()
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe({
                                        if (isViewAttached()) {
                                            mvpView?.showPlaceHolder()
                                        }
                                    },
                                            { throwable ->
                                                if (Fabric.isInitialized()) {
                                                    Crashlytics.log(throwable.message)
                                                }
                                                if (isViewAttached()) {
                                                    mvpView?.showPlaceHolder()
                                                }
                                            })
                        },
                                {
                                    if (Fabric.isInitialized()) {
                                        Crashlytics.log(it.message)
                                    }
                                    if (isViewAttached()) {
                                        mvpView?.showPlaceHolder()
                                    }
                                }))
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
                .subscribe({
                    if (isViewAttached()) {
                        mvpView?.showDetailFragment()
                    }
                },
                        {
                            if (Fabric.isInitialized()) {
                                Crashlytics.log(it.message)
                            }
                        }))
    }
}