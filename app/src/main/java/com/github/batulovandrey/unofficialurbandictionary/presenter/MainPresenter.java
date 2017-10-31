package com.github.batulovandrey.unofficialurbandictionary.presenter;

import com.github.batulovandrey.unofficialurbandictionary.adapter.DefinitionAdapter;
import com.github.batulovandrey.unofficialurbandictionary.adapter.DefinitionClickListener;

/**
 * @author Andrey Batulov on 27/10/2017
 */

public interface MainPresenter {

    void showDataInRecycler();

    void showQueriesInListView();

    boolean textChanged(String text);

    void filterText(String text);

    void setAdapterToRecycler(DefinitionAdapter definitionAdapter);

    void getData(String query, DefinitionClickListener listener);

    void showToast(int resId);

    long getDefinitionId(int position);

    void showProgressbar();

    void hideProgressbar();
}