package com.github.batulovandrey.urbandictionarycom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.github.batulovandrey.urbandictionarycom.adapter.DefinitionAdapter;
import com.github.batulovandrey.urbandictionarycom.adapter.DefinitionClickListener;
import com.github.batulovandrey.urbandictionarycom.bean.DefinitionResponse;
import com.github.batulovandrey.urbandictionarycom.realm.RealmManager;

import java.util.List;

import io.realm.Realm;

public class FavoritesActivity extends AppCompatActivity implements DefinitionClickListener {

    private static final String EXTRA_DEFINITION_ID = "extra_definition_id";

    private DefinitionAdapter mDefinitionAdapter;
    private List<DefinitionResponse> mFavorites;
    private Realm mRealm;
    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        initToolbar();
        mRecyclerView = findViewById(R.id.recycler_view);
        mRealm = new RealmManager(this, "favorites").getRealm();
        mFavorites = mRealm.where(DefinitionResponse.class).findAll();
        mDefinitionAdapter = new DefinitionAdapter(mFavorites, this);
        mRecyclerView.setAdapter(mDefinitionAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDefinitionAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int position) {
        DefinitionResponse definition = mFavorites.get(position);
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(EXTRA_DEFINITION_ID, definition.getDefid());
        startActivity(intent);
    }

    private void initToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}