package com.github.batulovandrey.unofficialurbandictionary.presenter

import com.github.batulovandrey.unofficialurbandictionary.data.DataManager
import com.github.batulovandrey.unofficialurbandictionary.ui.MvpView
import io.reactivex.disposables.CompositeDisposable

/**
 * Base class that implements the Presenter interface and provides a base implementation for
 * onAttach() and onDetach()
 */
open class BasePresenter<V : MvpView>(val dataManager: DataManager,
                                      val compositeDisposable: CompositeDisposable) : MvpPresenter<V> {

    var mvpView: V? = null
        private set

    override fun onAttach(mvpView: V) {
        this.mvpView = mvpView
    }

    override fun onDetach() {
        compositeDisposable.dispose()
        mvpView = null
    }

    /**
     * This method checks attached view.
     * If it is attached, @return true or false if not
     */
    fun isViewAttached(): Boolean {
        return mvpView != null
    }

}