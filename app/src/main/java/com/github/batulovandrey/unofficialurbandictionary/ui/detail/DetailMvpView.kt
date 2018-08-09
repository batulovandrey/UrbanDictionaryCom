package com.github.batulovandrey.unofficialurbandictionary.ui.detail

import com.github.batulovandrey.unofficialurbandictionary.data.db.model.Definition
import com.github.batulovandrey.unofficialurbandictionary.presenter.MvpView

interface DetailMvpView: MvpView {

    fun setImage(resId: Int, degrees: Float = 0f)

    fun clickToFavoriteIcon()

    fun setValuesToViews(definition: Definition)
}