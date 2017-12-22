package com.github.batulovandrey.unofficialurbandictionary.presenter

import com.github.batulovandrey.unofficialurbandictionary.adapter.DefinitionAdapter
import com.github.batulovandrey.unofficialurbandictionary.bean.DefinitionResponse

/**
 * @author Andrey Batulov on 22/12/2017
 */

interface FavoritesPresenter {

    val favorites: List<DefinitionResponse>

    val definitionAdapter: DefinitionAdapter

    fun clearList()

    fun showAlertDialog()

    fun showToast(resId: Int)

    fun createNewDefinitionAdapter(favorites: List<DefinitionResponse>): DefinitionAdapter

    fun hideRecycler()

    fun showRecycler()
}