package com.github.batulovandrey.urbandictionarycom;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.github.batulovandrey.urbandictionarycom.adapter.DefinitionAdapter;
import com.github.batulovandrey.urbandictionarycom.adapter.DefinitionClickListener;
import com.github.batulovandrey.urbandictionarycom.bean.DefinitionResponse;
import com.github.batulovandrey.urbandictionarycom.realm.RealmManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

import static com.github.batulovandrey.urbandictionarycom.utils.Constants.EXTRA_DEFINITION_ID;

public class FavoritesActivity extends AppCompatActivity implements DefinitionClickListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private DefinitionAdapter mDefinitionAdapter;
    private List<DefinitionResponse> mFavorites;
    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        ButterKnife.bind(this);
        initToolbar();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favorites_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.clear_favorites:
                clearFavoriteList();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(int position) {
        DefinitionResponse definition = mFavorites.get(position);
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(EXTRA_DEFINITION_ID, definition.getDefid());
        startActivity(intent);
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void clearFavoriteList() {
        new AlertDialog.Builder(this)
                .setTitle("Clear list of favorites")
                .setMessage("All items from favorite list will be removed. Are you sure?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mRealm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                RealmResults<DefinitionResponse> rows = realm
                                        .where(DefinitionResponse.class).findAll();
                                rows.deleteAllFromRealm();
                            }

                        });
                        mDefinitionAdapter.notifyDataSetChanged();
                    }
                }).show();
    }
}