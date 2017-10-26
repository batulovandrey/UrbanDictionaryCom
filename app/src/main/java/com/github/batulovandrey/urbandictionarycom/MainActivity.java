package com.github.batulovandrey.urbandictionarycom;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.github.batulovandrey.urbandictionarycom.adapter.UserQueriesAdapter;
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

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static final String EXTRA_SEARCH_QUERY = "extra_search_query";

    private Toolbar mToolbar;
    private SearchView mSearchView;
    private ListView mListView;
    private UserQueriesAdapter mUserQueriesAdapter;
    private String mSearchQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUserQueriesAdapter = new UserQueriesAdapter(this);
        initIU();
    }

    private void initIU() {
        initToolbar();
        initSearchView();
        mListView = findViewById(R.id.list_view);
        mListView.setAdapter(mUserQueriesAdapter);
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
        mSearchView.setFocusable(true);
        mSearchView.setIconified(false);
        mSearchView.setOnQueryTextListener(this);
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

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (query.length() > 0) {
//                    getData(query);
            mUserQueriesAdapter.saveQueryToRealm(query);
            mListView.setVisibility(View.GONE);
            return true;
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(final String newText) {
        mListView.setVisibility(View.VISIBLE);
        mUserQueriesAdapter.filter(newText);
        return false;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (intent.getExtras() != null) {
            mSearchQuery = intent.getExtras().getString(EXTRA_SEARCH_QUERY);
        }
        mSearchView.post(new Runnable() {
            @Override
            public void run() {
                if (mSearchQuery != null) {
                    mSearchView.setQuery(mSearchQuery, true);
//                    getData(mSearchQuery);
                }
            }
        });
        mSearchView.setFocusable(true);
        mSearchView.setIconified(false);
    }
}