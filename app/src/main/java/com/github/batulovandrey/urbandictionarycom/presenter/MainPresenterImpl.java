package com.github.batulovandrey.urbandictionarycom.presenter;

import com.github.batulovandrey.urbandictionarycom.adapter.DefinitionAdapter;
import com.github.batulovandrey.urbandictionarycom.adapter.DefinitionClickListener;
import com.github.batulovandrey.urbandictionarycom.model.MainModel;
import com.github.batulovandrey.urbandictionarycom.realm.RealmManager;
import com.github.batulovandrey.urbandictionarycom.view.MainView;

import io.realm.Realm;

/**
 * @author Andrey Batulov on 27/10/2017
 */

public class MainPresenterImpl implements MainPresenter {

    private final MainModel mMainModel;
    private final MainView mMainView;

    public MainPresenterImpl(MainView mainView) {
        mMainView = mainView;
        mMainModel = new MainModel(this);
    }

    @Override
    public void showDataInRecycler() {
        mMainView.showDataInRecycler();
    }

    @Override
    public void showQueriesInListView() {
        mMainView.showQueriesInListView();
    }

    @Override
    public boolean textChanged(String text) {
        return mMainModel.textChanged(text);
    }

    @Override
    public void filterText(String text) {
        mMainView.filterText(text);
    }

    @Override
    public void setAdapterToRecycler(DefinitionAdapter definitionAdapter) {
        mMainView.setAdapterToRecycler(definitionAdapter);
    }

    @Override
    public void getData(String query, DefinitionClickListener listener) {
        mMainModel.getData(query, listener);
    }

    @Override
    public void showToast(int resId) {
        mMainView.showToast(resId);
    }

    @Override
    public long getDefinitionId(int position) {
        return mMainModel.getDefinitionId(position);
    }

    @Override
    public Realm getRealm(String nameDb) {
        return new RealmManager(mMainView.getContext(), nameDb).getRealm();
    }
}