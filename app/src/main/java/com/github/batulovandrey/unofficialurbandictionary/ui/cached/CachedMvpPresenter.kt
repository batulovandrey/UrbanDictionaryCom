package com.github.batulovandrey.unofficialurbandictionary.ui.cached

import com.github.batulovandrey.unofficialurbandictionary.presenter.MvpPresenter

interface CachedMvpPresenter<V: CachedMvpView>: MvpPresenter<V> {

    fun loadData()

    fun clearCache()
}