package com.github.batulovandrey.unofficialurbandictionary.presenter

import com.github.batulovandrey.unofficialurbandictionary.adapter.DefinitionAdapter
import com.github.batulovandrey.unofficialurbandictionary.adapter.DefinitionClickListener

/**
 * @author Andrey Batulov on 22/12/2017
 */

interface MainPresenter {

    fun showDataInRecycler()

    fun showQueriesInListView()

    fun textChanged(text: String): Boolean

    fun filterText(text: String)

    fun setAdapterToRecycler(definitionAdapter: DefinitionAdapter)

    fun getData(query: String, listener: DefinitionClickListener)

    fun showToast(resId: Int)

    fun getDefinitionId(position: Int): Long

    fun showProgressbar()

    fun hideProgressbar()
}