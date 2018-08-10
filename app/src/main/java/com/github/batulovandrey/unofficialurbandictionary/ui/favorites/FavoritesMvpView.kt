package com.github.batulovandrey.unofficialurbandictionary.ui.favorites

import com.github.batulovandrey.unofficialurbandictionary.adapter.DefinitionAdapter
import com.github.batulovandrey.unofficialurbandictionary.presenter.MvpView

interface FavoritesMvpView: MvpView {

    fun showData()

    fun showPlaceHolder()

    fun showAlertDialog()

    fun showToast(resId: Int)

    fun showDetailFragment()

    fun setDefinitionAdapter(definitionAdapter: DefinitionAdapter)
}