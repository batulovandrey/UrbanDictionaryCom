package com.github.batulovandrey.urbandictionarycom;

import android.app.SearchManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import com.github.batulovandrey.urbandictionarycom.bean.BaseResponse;
import com.github.batulovandrey.urbandictionarycom.bean.DefinitionResponse;
import com.github.batulovandrey.urbandictionarycom.service.ApiClient;
import com.github.batulovandrey.urbandictionarycom.service.UrbanDictionaryService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Andrey Batulov on 26/10/2017
 */

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initIU();
    }

    private void initIU() {
        initToolbar();
        initSearchView();
    }

    private void initToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    private void initSearchView() {
        mSearchView = findViewById(R.id.search_view);
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        mSearchView.setSearchableInfo(searchManager != null ?
                searchManager.getSearchableInfo(getComponentName()) : null);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() > 0) {
                    getData(query);
                    return true;
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void getData(String query) {
        UrbanDictionaryService service = ApiClient.getRetrofit().create(UrbanDictionaryService.class);
        Call<BaseResponse> call = service.getDefine(query);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.body() != null) {
                    List<DefinitionResponse> definitionResponses = response.body().getDefinitionResponses();
                    for (DefinitionResponse definitionResponse : definitionResponses) {
                        System.out.println(definitionResponse);
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }
}