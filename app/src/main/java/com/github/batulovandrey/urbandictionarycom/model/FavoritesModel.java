package com.github.batulovandrey.urbandictionarycom.model;

import com.github.batulovandrey.urbandictionarycom.R;
import com.github.batulovandrey.urbandictionarycom.UrbanDictionaryComApp;
import com.github.batulovandrey.urbandictionarycom.bean.DefinitionResponse;
import com.github.batulovandrey.urbandictionarycom.presenter.FavoritesPresenter;
import com.github.batulovandrey.urbandictionarycom.realm.RealmManager;

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
        UrbanDictionaryComApp.getNetComponent().inject(this);
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