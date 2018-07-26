package com.github.batulovandrey.unofficialurbandictionary.ui.main

import com.github.batulovandrey.unofficialurbandictionary.adapter.DefinitionAdapter
import com.github.batulovandrey.unofficialurbandictionary.adapter.QueriesAdapter
import com.github.batulovandrey.unofficialurbandictionary.presenter.MvpView

interface MainMvpView: MvpView {

    fun showDefinitions()

    fun showQueries()

    fun setDefinitionAdapter(definitionAdapter: DefinitionAdapter)

    fun setQueriesAdapter(queriesAdapter: QueriesAdapter)

    fun showToast(resId: Int)

    fun initializeQueryToServer(query: String)

    fun showHint()

    fun closeNavigationDrawer()

    fun showSearchFragment()

    fun showPopularWordsFragment()

    fun showFavoritesFragment()

    fun showDetailFragment()
}