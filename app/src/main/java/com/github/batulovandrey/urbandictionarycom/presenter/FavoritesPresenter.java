package com.github.batulovandrey.urbandictionarycom.presenter;

import com.github.batulovandrey.urbandictionarycom.bean.DefinitionResponse;

import java.util.List;

/**
 * @author Andrey Batulov on 30/10/2017
 */

public interface FavoritesPresenter {

    List<DefinitionResponse> getFavorites();

    void clearList();

    void showAlertDialog();

    void showToast(int resId);
}