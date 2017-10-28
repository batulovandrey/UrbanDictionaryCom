package com.github.batulovandrey.urbandictionarycom;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.github.batulovandrey.urbandictionarycom.adapter.DefinitionAdapter;
import com.github.batulovandrey.urbandictionarycom.adapter.DefinitionClickListener;
import com.github.batulovandrey.urbandictionarycom.adapter.UserQueriesAdapter;
import com.github.batulovandrey.urbandictionarycom.bean.DefinitionResponse;
import com.github.batulovandrey.urbandictionarycom.presenter.MainPresenter;
import com.github.batulovandrey.urbandictionarycom.presenter.MainPresenterImpl;
import com.github.batulovandrey.urbandictionarycom.utils.Utils;
import com.github.batulovandrey.urbandictionarycom.view.MainView;

/**
 * @author Andrey Batulov on 26/10/2017
 */

public class MainActivity extends AppCompatActivity
        implements SearchView.OnQueryTextListener, DefinitionClickListener, MainView {

    private static final String EXTRA_SEARCH_QUERY = "extra_search_query";
    private static final String EXTRA_DEFINITION = "extra_definition";

    private Toolbar mToolbar;
    private SearchView mSearchView;
    private ListView mListView;
    private UserQueriesAdapter mUserQueriesAdapter;
    private String mSearchQuery;
    private RecyclerView mRecyclerView;
    private MainPresenter mMainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUserQueriesAdapter = new UserQueriesAdapter(this);
        initIU();
        mMainPresenter = new MainPresenterImpl(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.go_to_alphabet:
                startActivity(new Intent(getApplicationContext(), PopularWordsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (query.length() > 0) {
            mMainPresenter.getData(query, this);
            mUserQueriesAdapter.saveQueryToRealm(query);
            showDataInRecycler();
            Utils.hideKeyboard(mSearchView, this);
            return true;
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(final String newText) {
        return mMainPresenter.textChanged(newText);
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
                    // put the value in SearchView, but don't submit
                    mSearchView.setQuery(mSearchQuery, false);
                }
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        DefinitionResponse definition = mMainPresenter.getDefinitionById(position);
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(EXTRA_DEFINITION, definition);
        startActivity(intent);
    }

    @Override
    public void showDataInRecycler() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mListView.setVisibility(View.GONE);
    }

    @Override
    public void showQueriesInListView() {
        mListView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void filterText(String text) {
        mUserQueriesAdapter.filter(text);
    }

    @Override
    public void setAdapterToRecycler(DefinitionAdapter definitionAdapter) {
        mRecyclerView.setAdapter(definitionAdapter);
    }

    @Override
    public void showToast(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_LONG).show();
    }

    private void initIU() {
        initToolbar();
        initSearchView();
        mListView = findViewById(R.id.list_view);
        mListView.setAdapter(mUserQueriesAdapter);
        mRecyclerView = findViewById(R.id.recycler_view);
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
}