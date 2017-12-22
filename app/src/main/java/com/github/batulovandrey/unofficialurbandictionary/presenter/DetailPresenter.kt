package com.github.batulovandrey.unofficialurbandictionary.presenter

import com.github.batulovandrey.unofficialurbandictionary.bean.DefinitionResponse

/**
 * @author Andrey Batulov on 22/12/2017
 */

interface DetailPresenter {

    fun setImageResToImageView(definition: DefinitionResponse)

    fun getDefinitionByDefId(defId: Long): DefinitionResponse

    fun isFavoriteDefinition(definition: DefinitionResponse): Boolean

    fun isAddedToFav(defId: Long)
}