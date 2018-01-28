package com.github.batulovandrey.unofficialurbandictionary.model

import com.github.batulovandrey.unofficialurbandictionary.R
import com.github.batulovandrey.unofficialurbandictionary.UrbanDictionaryApp
import com.github.batulovandrey.unofficialurbandictionary.adapter.DefinitionAdapter
import com.github.batulovandrey.unofficialurbandictionary.bean.DefinitionResponse
import com.github.batulovandrey.unofficialurbandictionary.presenter.FavoritesPresenter
import com.github.batulovandrey.unofficialurbandictionary.realm.RealmManager
import io.realm.Realm
import javax.inject.Inject

/**
 * @author Andrey Batulov on 22/12/2017
 */

class FavoritesModel(private val mFavoritesPresenter: FavoritesPresenter) {

    @Inject
    lateinit var mRealmManager: RealmManager

    val favorites: List<DefinitionResponse>
    private val mDefinitionAdapter: DefinitionAdapter
    private val mRealm: Realm

    init {
        UrbanDictionaryApp.getNetComponent().inject(this)
        mRealm = mRealmManager.realmFavorites
        favorites = mRealm.where(DefinitionResponse::class.java).findAll()
        mDefinitionAdapter = mFavoritesPresenter.createNewDefinitionAdapter(favorites)
    }

    val definitionAdapter: DefinitionAdapter
        get() {
            if (favorites.isEmpty()) {
                mFavoritesPresenter.hideRecycler()
            } else {
                mFavoritesPresenter.showRecycler()
            }
            return mDefinitionAdapter
        }

    fun clearList() {
        mRealm.executeTransaction { realm ->
            val rows = realm
                    .where(DefinitionResponse::class.java).findAll()
            rows.deleteAllFromRealm()
        }
        mFavoritesPresenter.showToast(R.string.list_clear)
        mDefinitionAdapter.notifyDataSetChanged()
    }
}