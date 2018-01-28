package com.github.batulovandrey.unofficialurbandictionary.presenter

import com.github.batulovandrey.unofficialurbandictionary.adapter.DefinitionAdapter
import com.github.batulovandrey.unofficialurbandictionary.adapter.DefinitionClickListener
import com.github.batulovandrey.unofficialurbandictionary.adapter.QueriesAdapter
import com.github.batulovandrey.unofficialurbandictionary.model.MainModel
import com.github.batulovandrey.unofficialurbandictionary.view.MainView

/**
 * @author Andrey Batulov on 22/12/2017
 */

class MainPresenterImpl(private val mMainView: MainView) : MainPresenter {

    private val mMainModel: MainModel = MainModel(this)

    override fun showDefinitionsInRecycler() {
        mMainView.showDataInRecycler()
    }

    override fun showQueriesInRecycler() {
        mMainView.showQueriesInListView()
    }

    override fun textChanged(text: String): Boolean {
        return mMainModel.textChanged(text)
    }

    override fun setAdapterToDefinitionsRecycler(definitionAdapter: DefinitionAdapter) {
        mMainView.setAdapterToDefinitionsRecycler(definitionAdapter)
    }

    override fun setAdapterToQueriesRecycler(queriesAdapter: QueriesAdapter) {
        mMainView.setAdapterToQueriesRecycler(queriesAdapter)
    }

    override fun getData(query: String, listener: DefinitionClickListener) {
        mMainModel.getData(query, listener)
    }

    override fun showToast(resId: Int) {
        mMainView.showToast(resId)
    }

    override fun getDefinitionId(position: Int): Long {
        return mMainModel.getDefinitionId(position)
    }

    override fun showProgressbar() {
        mMainView.showProgressbar()
    }

    override fun hideProgressbar() {
        mMainView.hideProgressbar()
    }

    override fun saveQueryToRealm(query: String) {
        mMainModel.saveQueryToRealm(query)
    }

    override fun initializeQueryToServer(query: String) {
        mMainView.initializeQueryToServer(query)
    }
}