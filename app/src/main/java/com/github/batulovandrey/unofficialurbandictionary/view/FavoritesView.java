package com.github.batulovandrey.unofficialurbandictionary.view;

/**
 * @author Andrey Batulov on 30/10/2017
 */

public interface FavoritesView {

    void showAlertDialog();

    void showToast(int resId);

    void hideRecycler();

    void showRecycler();
}