package com.github.batulovandrey.unofficialurbandictionary.ui.detail

import com.crashlytics.android.Crashlytics
import com.github.batulovandrey.unofficialurbandictionary.R
import com.github.batulovandrey.unofficialurbandictionary.data.DataManager
import com.github.batulovandrey.unofficialurbandictionary.data.db.model.Definition
import com.github.batulovandrey.unofficialurbandictionary.presenter.BasePresenter
import io.fabric.sdk.android.Fabric
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
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
        val activeDefinition = dataManager.getActiveDefinition()

        activeDefinition!!.favorite = when (activeDefinition.favorite) {
            0 -> 1
            else -> 0
        }

        when (activeDefinition.favorite) {

            0 -> removeFromFavorites(activeDefinition)
            1 -> putToFavorites(activeDefinition)

        }
    }

    override fun putToFavorites(definition: Definition) {
        compositeDisposable.addAll(deleteDefinition(definition),
                dataManager.saveDefinitionToFavorites(definition)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            mvpView?.setImage(R.drawable.favorite_black, -360f)
                        },
                                {
                                    if (Fabric.isInitialized()) {
                                        Crashlytics.log(it.message)
                                    }
                                }))
    }

    override fun removeFromFavorites(definition: Definition) {
        compositeDisposable.addAll(deleteDefinition(definition),
                dataManager.deleteDefinitionFromFavorites(definition)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            mvpView?.setImage(R.drawable.favorite_white, 360f)
                        },
                                {
                                    if (Fabric.isInitialized()) {
                                        Crashlytics.log(it.message)
                                    }
                                })
        )
    }

    private fun deleteDefinition(definition: Definition): Disposable {
        return dataManager.deleteDefinition(definition)
                .subscribeOn(Schedulers.io())
                .subscribe()
    }
}