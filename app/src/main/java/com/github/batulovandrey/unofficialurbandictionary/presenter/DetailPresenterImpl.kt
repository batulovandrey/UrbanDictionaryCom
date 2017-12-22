package com.github.batulovandrey.unofficialurbandictionary.presenter

import com.github.batulovandrey.unofficialurbandictionary.R
import com.github.batulovandrey.unofficialurbandictionary.bean.DefinitionResponse
import com.github.batulovandrey.unofficialurbandictionary.model.DetailModel
import com.github.batulovandrey.unofficialurbandictionary.view.DetailView

/**
 * @author Andrey Batulov on 22/12/2017
 */

class DetailPresenterImpl(private val mDetailView: DetailView) : DetailPresenter {

    private val mDetailModel: DetailModel = DetailModel(this)

    override fun setImageResToImageView(definition: DefinitionResponse) {
        if (mDetailModel.isFavoriteDefinition(definition)) {
            mDetailView.setImageResToImageView(R.drawable.unfav)
        } else {
            mDetailView.setImageResToImageView(R.drawable.fav)
        }
    }

    override fun getDefinitionByDefId(defId: Long): DefinitionResponse {
        return mDetailModel.getDefinitionByDefId(defId)
    }

    override fun isFavoriteDefinition(definition: DefinitionResponse): Boolean {
        return mDetailModel.isFavoriteDefinition(definition)
    }

    override fun isAddedToFav(defId: Long) {
        mDetailModel.isAddedToFav(defId)
    }
}