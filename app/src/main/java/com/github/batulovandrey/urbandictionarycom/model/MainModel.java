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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Andrey Batulov on 27/10/2017
 */

public class MainModel {

    private final MainPresenter mMainPresenter;

    private DefinitionAdapter mDefinitionAdapter;
    private List<DefinitionResponse> mDefinitions;

    public MainModel(MainPresenter mainPresenter) {
        mMainPresenter = mainPresenter;
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
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call,
                                  @NonNull Throwable t) {
                mMainPresenter.showToast(R.string.error);
            }
        });
    }
}