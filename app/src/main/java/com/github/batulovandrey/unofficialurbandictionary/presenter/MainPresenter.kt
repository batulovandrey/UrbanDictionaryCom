package com.github.batulovandrey.unofficialurbandictionary.presenter

import com.github.batulovandrey.unofficialurbandictionary.adapter.DefinitionAdapter
import com.github.batulovandrey.unofficialurbandictionary.adapter.DefinitionClickListener
import com.github.batulovandrey.unofficialurbandictionary.adapter.QueriesAdapter

/**
 * @author Andrey Batulov on 22/12/2017
 */

interface MainPresenter {

    fun showDefinitionsInRecycler()

    fun showQueriesInRecycler()

    fun textChanged(text: String): Boolean

    fun setAdapterToDefinitionsRecycler(definitionAdapter: DefinitionAdapter)

    fun setAdapterToQueriesRecycler(queriesAdapter: QueriesAdapter)

    fun getData(query: String, listener: DefinitionClickListener)

    fun showToast(resId: Int)

    fun getDefinitionId(position: Int): Long

    fun showProgressbar()

    fun hideProgressbar()

    fun saveQueryToRealm(query: String)

    fun initializeQueryToServer(query: String)
}