package com.github.batulovandrey.unofficialurbandictionary.model

import com.github.batulovandrey.unofficialurbandictionary.UrbanDictionaryApp
import com.github.batulovandrey.unofficialurbandictionary.bean.DefinitionResponse
import com.github.batulovandrey.unofficialurbandictionary.presenter.DetailPresenter
import com.github.batulovandrey.unofficialurbandictionary.realm.RealmManager
import io.realm.Realm
import javax.inject.Inject

/**
 * @author Andrey Batulov on 22/12/2017
 */

class DetailModel(private val mDetailPresenter: DetailPresenter) {

    @Inject
    lateinit var mRealmManager: RealmManager

    private lateinit var mDefinitionsRealm: Realm
    private lateinit var mFavoriteDefinitionsRealm: Realm

    init {
        UrbanDictionaryApp.getNetComponent().inject(this)
        initRealm()
    }

    fun getDefinitionByDefId(defId: Long): DefinitionResponse {
        return mDefinitionsRealm
                .where(DefinitionResponse::class.java).equalTo("defid", defId).findFirst()
    }

    fun isFavoriteDefinition(definition: DefinitionResponse): Boolean {
        val list = mFavoriteDefinitionsRealm.where(DefinitionResponse::class.java).findAll()
        return list.any { it.defid == definition.defid }
    }

    fun isAddedToFav(defId: Long) {
        val tempDefinition = mFavoriteDefinitionsRealm
                .where(DefinitionResponse::class.java).equalTo("defid", defId).findFirst()
        val definition = getDefinitionByDefId(defId)
        if (tempDefinition == null) {
            saveToFavorites(definition)
        } else {
            deleteFromFavorites(definition)
        }
        mDetailPresenter.setImageResToImageView(definition)
    }

    private fun initRealm() {
        mDefinitionsRealm = mRealmManager.realmDefinitions
        mFavoriteDefinitionsRealm = mRealmManager.realmFavorites
    }

    private fun saveToFavorites(definition: DefinitionResponse) {
        mFavoriteDefinitionsRealm.executeTransaction { realm ->
            val newDefinition = realm.createObject(DefinitionResponse::class.java)
            newDefinition.word = definition.word
            newDefinition.thumbsUp = definition.thumbsUp
            newDefinition.thumbsDown = definition.thumbsDown
            newDefinition.permalink = definition.permalink
            newDefinition.example = definition.example
            newDefinition.definition = definition.definition
            newDefinition.defid = definition.defid
            newDefinition.author = definition.author
        }
    }

    private fun deleteFromFavorites(definition: DefinitionResponse) {
        mFavoriteDefinitionsRealm.executeTransaction { realm ->
            val rows = realm
                    .where(DefinitionResponse::class.java)
                    .equalTo("defid", definition.defid)
                    .findAll()
            rows.deleteAllFromRealm()
        }
    }
}