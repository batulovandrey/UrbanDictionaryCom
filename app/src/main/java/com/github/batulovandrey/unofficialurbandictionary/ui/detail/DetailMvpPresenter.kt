package com.github.batulovandrey.unofficialurbandictionary.ui.detail

import com.github.batulovandrey.unofficialurbandictionary.dagger.PerActivity
import com.github.batulovandrey.unofficialurbandictionary.data.db.model.Definition
import com.github.batulovandrey.unofficialurbandictionary.presenter.MvpPresenter

@PerActivity
interface DetailMvpPresenter<V: DetailMvpView>: MvpPresenter<V> {

    fun onViewInitialized()

    fun handleClick()

    fun putToFavorites(definition: Definition)

    fun removeFromFavorites(definition: Definition)
}