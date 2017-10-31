package com.github.batulovandrey.unofficialurbandictionary.model;

import com.github.batulovandrey.unofficialurbandictionary.R;
import com.github.batulovandrey.unofficialurbandictionary.UrbanDictionaryApp;
import com.github.batulovandrey.unofficialurbandictionary.bean.DefinitionResponse;
import com.github.batulovandrey.unofficialurbandictionary.presenter.FavoritesPresenter;
import com.github.batulovandrey.unofficialurbandictionary.realm.RealmManager;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * @author Andrey Batulov on 30/10/2017
 */

public class FavoritesModel {

    @Inject
    RealmManager mRealmManager;

    private final FavoritesPresenter mFavoritesPresenter;
    private List<DefinitionResponse> mFavorites;
    private Realm mRealm;

    public FavoritesModel(FavoritesPresenter favoritesPresenter) {
        mFavoritesPresenter = favoritesPresenter;
        UrbanDictionaryApp.getNetComponent().inject(this);
        mRealm = mRealmManager.getRealmFavorites();
        mFavorites = mRealm.where(DefinitionResponse.class).findAll();
    }


    public List<DefinitionResponse> getFavorites() {
        return mFavorites;
    }

    public void clearList() {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<DefinitionResponse> rows = realm
                        .where(DefinitionResponse.class).findAll();
                rows.deleteAllFromRealm();
            }
        });
        mFavoritesPresenter.showToast(R.string.list_clear);
    }
}