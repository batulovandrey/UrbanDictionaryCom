package com.github.batulovandrey.unofficialurbandictionary.ui.main

import com.github.batulovandrey.unofficialurbandictionary.dagger.PerActivity
import com.github.batulovandrey.unofficialurbandictionary.presenter.MvpPresenter

@PerActivity
interface MainMvpPresenter<V: MainMvpView>: MvpPresenter<V> {

    fun onViewInitialized()

    fun showSearch()

    fun showPopularWords()

    fun showFavorites()

    fun showDetail()

    fun filterQueries(text: String)

    fun getData(text: String)
}