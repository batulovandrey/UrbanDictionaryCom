package com.github.batulovandrey.unofficialurbandictionary.presenter

import com.github.batulovandrey.unofficialurbandictionary.adapter.DefinitionAdapter
import com.github.batulovandrey.unofficialurbandictionary.adapter.DefinitionClickListener
import com.github.batulovandrey.unofficialurbandictionary.model.MainModel
import com.github.batulovandrey.unofficialurbandictionary.view.MainView

/**
 * @author Andrey Batulov on 22/12/2017
 */

class MainPresenterImpl(private val mMainView: MainView) : MainPresenter {

    private val mMainModel: MainModel = MainModel(this)

    override fun showDataInRecycler() {
        mMainView.showDataInRecycler()
    }

    override fun showQueriesInListView() {
        mMainView.showQueriesInListView()
    }

    override fun textChanged(text: String): Boolean {
        return mMainModel.textChanged(text)
    }

    override fun filterText(text: String) {
        mMainView.filterText(text)
    }

    override fun setAdapterToRecycler(definitionAdapter: DefinitionAdapter) {
        mMainView.setAdapterToRecycler(definitionAdapter)
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
}