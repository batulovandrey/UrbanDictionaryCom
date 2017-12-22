package com.github.batulovandrey.unofficialurbandictionary.view

import com.github.batulovandrey.unofficialurbandictionary.adapter.DefinitionAdapter

/**
 * @author Andrey Batulov on 22/12/2017
 */

interface MainView {

    fun showDataInRecycler()

    fun showQueriesInListView()

    fun filterText(text: String)

    fun setAdapterToRecycler(definitionAdapter: DefinitionAdapter)

    fun showToast(resId: Int)

    fun showProgressbar()

    fun hideProgressbar()
}