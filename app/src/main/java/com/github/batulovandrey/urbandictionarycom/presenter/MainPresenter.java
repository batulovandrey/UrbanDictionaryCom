package com.github.batulovandrey.urbandictionarycom.presenter;

import com.github.batulovandrey.urbandictionarycom.adapter.DefinitionAdapter;
import com.github.batulovandrey.urbandictionarycom.adapter.DefinitionClickListener;

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
}