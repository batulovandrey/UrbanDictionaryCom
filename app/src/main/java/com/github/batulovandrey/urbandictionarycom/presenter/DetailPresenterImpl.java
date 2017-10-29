package com.github.batulovandrey.urbandictionarycom.presenter;

import android.content.Context;

import com.github.batulovandrey.urbandictionarycom.R;
import com.github.batulovandrey.urbandictionarycom.bean.DefinitionResponse;
import com.github.batulovandrey.urbandictionarycom.model.DetailModel;
import com.github.batulovandrey.urbandictionarycom.realm.RealmManager;
import com.github.batulovandrey.urbandictionarycom.view.DetailView;

import io.realm.Realm;

/**
 * @author Andrey Batulov on 29/10/2017
 */

public class DetailPresenterImpl implements DetailPresenter {

    private final DetailModel mDetailModel;
    private final DetailView mDetailView;

    public DetailPresenterImpl(DetailView detailView) {
        mDetailView = detailView;
        mDetailModel = new DetailModel(this);
    }

    @Override
    public void setImageResToImageView(DefinitionResponse definition) {
        if (mDetailModel.isFavoriteDefinition(definition)) {
            mDetailView.setImageResToImageView(R.drawable.unfav);
        } else {
            mDetailView.setImageResToImageView(R.drawable.fav);
        }
    }

    @Override
    public DefinitionResponse getDefinitionByDefIf(long defId) {
        return mDetailModel.getDefinitionByDefIf(defId);
    }

    @Override
    public boolean isFavoriteDefinition(DefinitionResponse definition) {
        return mDetailModel.isFavoriteDefinition(definition);
    }

    @Override
    public void isAddedToFav(long defId) {
        mDetailModel.isAddedToFav(defId);
    }

    @Override
    public Context getContext() {
        return mDetailView.getContext();
    }

    @Override
    public Realm getRealm(String nameDb) {
        return new RealmManager(getContext(), nameDb).getRealm();
    }
}