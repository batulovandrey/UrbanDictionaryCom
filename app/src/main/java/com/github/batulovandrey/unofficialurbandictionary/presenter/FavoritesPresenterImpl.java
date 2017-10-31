package com.github.batulovandrey.unofficialurbandictionary.presenter;

import com.github.batulovandrey.unofficialurbandictionary.bean.DefinitionResponse;
import com.github.batulovandrey.unofficialurbandictionary.model.FavoritesModel;
import com.github.batulovandrey.unofficialurbandictionary.view.FavoritesView;

import java.util.List;

/**
 * @author Andrey Batulov on 30/10/2017
 */

public class FavoritesPresenterImpl implements FavoritesPresenter {

    private final FavoritesModel mFavoritesModel;
    private final FavoritesView mFavoritesView;

    public FavoritesPresenterImpl(FavoritesView favoritesView) {
        mFavoritesView = favoritesView;
        mFavoritesModel = new FavoritesModel(this);
    }

    @Override
    public List<DefinitionResponse> getFavorites() {
        return mFavoritesModel.getFavorites();
    }

    @Override
    public void clearList() {
        mFavoritesModel.clearList();
    }

    @Override
    public void showAlertDialog() {
        mFavoritesView.showAlertDialog();
    }

    @Override
    public void showToast(int resId) {
        mFavoritesView.showToast(resId);
    }
}