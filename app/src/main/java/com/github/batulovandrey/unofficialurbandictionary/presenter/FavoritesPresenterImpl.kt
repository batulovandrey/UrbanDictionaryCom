package com.github.batulovandrey.unofficialurbandictionary.presenter

import com.github.batulovandrey.unofficialurbandictionary.adapter.DefinitionAdapter
import com.github.batulovandrey.unofficialurbandictionary.adapter.DefinitionClickListener
import com.github.batulovandrey.unofficialurbandictionary.bean.DefinitionResponse
import com.github.batulovandrey.unofficialurbandictionary.model.FavoritesModel
import com.github.batulovandrey.unofficialurbandictionary.view.FavoritesView

/**
 * @author Andrey Batulov on 22/12/2017
 */

class FavoritesPresenterImpl(private val mFavoritesView: FavoritesView) : FavoritesPresenter {

    private val mFavoritesModel: FavoritesModel = FavoritesModel(this)

    override val favorites: List<DefinitionResponse>
        get() = mFavoritesModel.favorites

    override val definitionAdapter: DefinitionAdapter
        get() = mFavoritesModel.definitionAdapter

    override fun clearList() {
        mFavoritesModel.clearList()
    }

    override fun showAlertDialog() {
        mFavoritesView.showAlertDialog()
    }

    override fun showToast(resId: Int) {
        mFavoritesView.showToast(resId)
    }

    override fun createNewDefinitionAdapter(favorites: List<DefinitionResponse>): DefinitionAdapter {
        return DefinitionAdapter(favorites, mFavoritesView as DefinitionClickListener)
    }

    override fun hideRecycler() {
        mFavoritesView.hideRecycler()
    }

    override fun showRecycler() {
        mFavoritesView.showRecycler()
    }
}