package com.github.batulovandrey.urbandictionarycom;

import android.app.SearchManager;
import android.content.Context;
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
import com.github.batulovandrey.urbandictionarycom.presenter.MainPresenter;
import com.github.batulovandrey.urbandictionarycom.presenter.MainPresenterImpl;
import com.github.batulovandrey.urbandictionarycom.utils.Utils;
import com.github.batulovandrey.urbandictionarycom.view.MainView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.github.batulovandrey.urbandictionarycom.utils.Constants.EXTRA_DEFINITION_ID;
import static com.github.batulovandrey.urbandictionarycom.utils.Constants.EXTRA_SEARCH_QUERY;

/**
 * @author Andrey Batulov on 26/10/2017
 */

public class MainActivity extends AppCompatActivity
        implements SearchView.OnQueryTextListener, DefinitionClickListener, MainView {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.search_view)
    SearchView mSearchView;

    @BindView(R.id.list_view)
    ListView mListView;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private UserQueriesAdapter mUserQueriesAdapter;
    private String mSearchQuery;
    private MainPresenter mMainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
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
                startActivity(new Intent(getContext(), PopularWordsActivity.class));
                return true;
            case R.id.go_to_favorites:
                startActivity(new Intent(getContext(), FavoritesActivity.class));
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
        long definitionId = mMainPresenter.getDefinitionId(position);
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(EXTRA_DEFINITION_ID, definitionId);
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

    @Override
    public Context getContext() {
        return this;
    }

    private void initIU() {
        initToolbar();
        initSearchView();
        mListView.setAdapter(mUserQueriesAdapter);
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
    }

    private void initSearchView() {
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        mSearchView.setSearchableInfo(searchManager != null ?
                searchManager.getSearchableInfo(getComponentName()) : null);
        mSearchView.setFocusable(true);
        mSearchView.setIconified(false);
        mSearchView.setOnQueryTextListener(this);
    }
}