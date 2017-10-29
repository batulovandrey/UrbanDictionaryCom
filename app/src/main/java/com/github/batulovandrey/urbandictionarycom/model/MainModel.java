package com.github.batulovandrey.urbandictionarycom.model;

import android.support.annotation.NonNull;

import com.github.batulovandrey.urbandictionarycom.R;
import com.github.batulovandrey.urbandictionarycom.adapter.DefinitionAdapter;
import com.github.batulovandrey.urbandictionarycom.adapter.DefinitionClickListener;
import com.github.batulovandrey.urbandictionarycom.bean.BaseResponse;
import com.github.batulovandrey.urbandictionarycom.bean.DefinitionResponse;
import com.github.batulovandrey.urbandictionarycom.presenter.MainPresenter;
import com.github.batulovandrey.urbandictionarycom.service.ApiClient;
import com.github.batulovandrey.urbandictionarycom.service.UrbanDictionaryService;

import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Andrey Batulov on 27/10/2017
 */

public class MainModel {

    private final MainPresenter mMainPresenter;
    private Realm mRealm;
    private DefinitionAdapter mDefinitionAdapter;
    private List<DefinitionResponse> mDefinitions;

    public MainModel(MainPresenter mainPresenter) {
        mMainPresenter = mainPresenter;
        mRealm = mMainPresenter.getRealm("definitions");
    }

    public boolean textChanged(String text) {
        if (mDefinitions != null && mDefinitions.size() > 0 && text.length() == 0) {
            mMainPresenter.showDataInRecycler();
        } else {
            mMainPresenter.showQueriesInListView();
            mMainPresenter.filterText(text);
        }
        return false;
    }

    public void getData(String query, final DefinitionClickListener listener) {
        mMainPresenter.showProgressbar();
        UrbanDictionaryService service = ApiClient.getRetrofit().create(UrbanDictionaryService.class);
        Call<BaseResponse> call = service.getDefine(query);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call,
                                   @NonNull Response<BaseResponse> response) {
                if (response.body() != null) {
                    mDefinitions = response.body().getDefinitionResponses();
                    mDefinitionAdapter = new DefinitionAdapter(mDefinitions, listener);
                    mMainPresenter.setAdapterToRecycler(mDefinitionAdapter);
                    mMainPresenter.hideProgressbar();
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call,
                                  @NonNull Throwable t) {
                System.out.println(call.toString());
                System.out.println(t.getMessage());
                mMainPresenter.showToast(R.string.error);
                mMainPresenter.hideProgressbar();
            }
        });
    }

    public long getDefinitionId(final int position) {
        saveDefinitionToRealm(position);
        return mDefinitions.get(position).getDefid();
    }

    private void saveDefinitionToRealm(final int position) {
        long defId = mDefinitions.get(position).getDefid();
        DefinitionResponse checkDef = mRealm.where(DefinitionResponse.class).equalTo("defid", defId).findFirst();
        if (checkDef == null) {
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    DefinitionResponse definition = realm.createObject(DefinitionResponse.class);
                    definition.setAuthor(mDefinitions.get(position).getAuthor());
                    definition.setDefid(mDefinitions.get(position).getDefid());
                    definition.setDefinition(mDefinitions.get(position).getDefinition());
                    definition.setExample(mDefinitions.get(position).getExample());
                    definition.setPermalink(mDefinitions.get(position).getPermalink());
                    definition.setThumbsDown(mDefinitions.get(position).getThumbsDown());
                    definition.setThumbsUp(mDefinitions.get(position).getThumbsUp());
                    definition.setWord(mDefinitions.get(position).getWord());
                }
            });
        }
    }
}