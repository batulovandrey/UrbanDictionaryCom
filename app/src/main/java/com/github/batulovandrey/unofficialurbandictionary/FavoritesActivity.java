package com.github.batulovandrey.unofficialurbandictionary;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.batulovandrey.unofficialurbandictionary.adapter.DefinitionAdapter;
import com.github.batulovandrey.unofficialurbandictionary.adapter.DefinitionClickListener;
import com.github.batulovandrey.unofficialurbandictionary.bean.DefinitionResponse;
import com.github.batulovandrey.unofficialurbandictionary.presenter.FavoritesPresenter;
import com.github.batulovandrey.unofficialurbandictionary.presenter.FavoritesPresenterImpl;
import com.github.batulovandrey.unofficialurbandictionary.view.FavoritesView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.github.batulovandrey.unofficialurbandictionary.utils.Constants.EXTRA_DEFINITION_ID;

public class FavoritesActivity extends AppCompatActivity
        implements DefinitionClickListener, FavoritesView {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private DefinitionAdapter mDefinitionAdapter;
    private FavoritesPresenter mFavoritesPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        mFavoritesPresenter = new FavoritesPresenterImpl(this);
        ButterKnife.bind(this);
        initToolbar();
        mDefinitionAdapter = new DefinitionAdapter(mFavoritesPresenter.getFavorites(), this);
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
                mFavoritesPresenter.showAlertDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(int position) {
        DefinitionResponse definition = mFavoritesPresenter.getFavorites().get(position);
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(EXTRA_DEFINITION_ID, definition.getDefid());
        startActivity(intent);
    }

    @Override
    public void showAlertDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Clear list of favorites")
                .setMessage("All items from favorite list will be removed. Are you sure?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mFavoritesPresenter.clearList();
                        mDefinitionAdapter.notifyDataSetChanged();
                    }
                }).show();
    }

    @Override
    public void showToast(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_LONG).show();
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}