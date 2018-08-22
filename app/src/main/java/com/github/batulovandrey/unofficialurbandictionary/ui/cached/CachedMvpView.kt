package com.github.batulovandrey.unofficialurbandictionary.ui.cached

import com.github.batulovandrey.unofficialurbandictionary.adapter.DefinitionAdapter
import com.github.batulovandrey.unofficialurbandictionary.ui.MvpView

interface CachedMvpView: MvpView {

    fun setDefinitionAdapter(adapter: DefinitionAdapter)

    fun showData()

    fun showPlaceHolder()

    fun showDetailFragment()

    fun showAlertDialog()
}