package com.github.batulovandrey.unofficialurbandictionary.ui.favorites

import com.github.batulovandrey.unofficialurbandictionary.presenter.MvpPresenter

interface FavoritesMvpPresenter<V: FavoritesMvpView>: MvpPresenter<V> {

    fun loadData()

    fun clearList()
}