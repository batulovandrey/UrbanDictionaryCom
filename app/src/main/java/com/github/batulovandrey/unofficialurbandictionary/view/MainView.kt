package com.github.batulovandrey.unofficialurbandictionary.view

import com.github.batulovandrey.unofficialurbandictionary.adapter.DefinitionAdapter
import com.github.batulovandrey.unofficialurbandictionary.adapter.QueriesAdapter

/**
 * @author Andrey Batulov on 22/12/2017
 */

interface MainView {

    fun showDataInRecycler()

    fun showQueriesInListView()

    fun setAdapterToDefinitionsRecycler(definitionsAdapter: DefinitionAdapter)

    fun setAdapterToQueriesRecycler(queriesAdapter: QueriesAdapter)

    fun showToast(resId: Int)

    fun showProgressbar()

    fun hideProgressbar()

    fun initializeQueryToServer(query: String)

    fun showHint()
}