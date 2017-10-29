package com.github.batulovandrey.urbandictionarycom.model;

import com.github.batulovandrey.urbandictionarycom.bean.DefinitionResponse;
import com.github.batulovandrey.urbandictionarycom.presenter.DetailPresenter;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * @author Andrey Batulov on 29/10/2017
 */

public class DetailModel {

    private Realm mDefinitionsRealm;
    private Realm mFavoriteDefinitionsRealm;

    private final DetailPresenter mDetailPresenter;

    public DetailModel(DetailPresenter detailPresenter) {
        mDetailPresenter = detailPresenter;
        initRealm();
    }

    public DefinitionResponse getDefinitionByDefIf(long defId) {
        return mDefinitionsRealm
                .where(DefinitionResponse.class).equalTo("defid", defId).findFirst();
    }

    public boolean isFavoriteDefinition(DefinitionResponse definition) {
        List<DefinitionResponse> list = mFavoriteDefinitionsRealm.where(DefinitionResponse.class).findAll();
        for (DefinitionResponse def : list) {
            if (def.getDefid() == definition.getDefid())
                return true;
        }
        return false;
    }

    public void isAddedToFav(long defId) {
        DefinitionResponse tempDefinition = mFavoriteDefinitionsRealm
                .where(DefinitionResponse.class).equalTo("defid", defId).findFirst();
        DefinitionResponse definition = getDefinitionByDefIf(defId);
        if (tempDefinition == null) {
            saveToFavorites(definition);
        } else {
            deleteFromFavorites(definition);
        }
        mDetailPresenter.setImageResToImageView(definition);
    }

    private void initRealm() {
        mDefinitionsRealm = mDetailPresenter.getRealm("definitions");
        mFavoriteDefinitionsRealm = mDetailPresenter.getRealm("favorites");
    }

    private void saveToFavorites(final DefinitionResponse definition) {
        mFavoriteDefinitionsRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                DefinitionResponse newDefinition = realm.createObject(DefinitionResponse.class);
                newDefinition.setWord(definition.getWord());
                newDefinition.setThumbsUp(definition.getThumbsUp());
                newDefinition.setThumbsDown(definition.getThumbsDown());
                newDefinition.setPermalink(definition.getPermalink());
                newDefinition.setExample(definition.getExample());
                newDefinition.setDefinition(definition.getDefinition());
                newDefinition.setDefid(definition.getDefid());
                newDefinition.setAuthor(definition.getAuthor());
            }
        });
    }

    private void deleteFromFavorites(final DefinitionResponse definition) {
        mFavoriteDefinitionsRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<DefinitionResponse> rows = realm
                        .where(DefinitionResponse.class)
                        .equalTo("defid", definition.getDefid())
                        .findAll();
                rows.deleteAllFromRealm();
            }
        });
    }
}