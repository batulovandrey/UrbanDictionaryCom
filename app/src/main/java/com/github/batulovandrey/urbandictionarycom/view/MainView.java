package com.github.batulovandrey.urbandictionarycom.view;

import android.content.Context;

import com.github.batulovandrey.urbandictionarycom.adapter.DefinitionAdapter;

/**
 * @author Andrey Batulov on 27/10/2017
 */

public interface MainView {

    void showDataInRecycler();

    void showQueriesInListView();

    void filterText(String text);

    void setAdapterToRecycler(DefinitionAdapter definitionAdapter);

    void showToast(int resId);

    Context getContext();

    void showProgressbar();

    void hideProgressbar();
}