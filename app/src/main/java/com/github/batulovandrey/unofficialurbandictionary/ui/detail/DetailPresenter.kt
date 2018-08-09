package com.github.batulovandrey.unofficialurbandictionary.ui.detail

import com.github.batulovandrey.unofficialurbandictionary.R
import com.github.batulovandrey.unofficialurbandictionary.data.DataManager
import com.github.batulovandrey.unofficialurbandictionary.data.db.model.Definition
import com.github.batulovandrey.unofficialurbandictionary.presenter.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailPresenter<V : DetailMvpView> @Inject constructor(dataManager: DataManager,
                                                             compositeDisposable: CompositeDisposable)
    : DetailMvpPresenter<V>, BasePresenter<V>(dataManager, compositeDisposable) {

    override fun onAttach(mvpView: V) {
        super.onAttach(mvpView)
        onViewInitialized()
    }

    override fun onViewInitialized() {
        dataManager.getActiveDefinition()?.let { mvpView?.setValuesToViews(it) }
    }

    override fun handleClick() {
        dataManager.getActiveDefinition()?.let {
            if (it.favorite == 0) {
                mvpView?.setImage(R.drawable.favorite_black, -360f)
                it.favorite = 1
                putToFavorites(it)
            } else {
                mvpView?.setImage(R.drawable.favorite_white, 360f)
                it.favorite = 0
                removeFromFavorites(it)
            }
        }
    }

    override fun putToFavorites(definition: Definition) {
        compositeDisposable.add(
                dataManager.saveDefinitionToFavorites(definition)
                        .subscribeOn(Schedulers.io())
                        .subscribe())
    }

    override fun removeFromFavorites(definition: Definition) {
        compositeDisposable.add(
                dataManager.saveDefinition(definition)
                        .subscribeOn(Schedulers.io())
                        .subscribe())
    }
}