package com.github.batulovandrey.urbandictionarycom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.github.batulovandrey.urbandictionarycom.adapter.DefinitionAdapter;
import com.github.batulovandrey.urbandictionarycom.bean.DefinitionResponse;
import com.github.batulovandrey.urbandictionarycom.realm.RealmManager;

import java.util.List;

import io.realm.Realm;

public class FavoritesActivity extends AppCompatActivity {

    private DefinitionAdapter mDefinitionAdapter;
    private List<DefinitionResponse> mFavorites;
    private Realm mRealm;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRealm = new RealmManager(this, "favorites").getRealm();
        mFavorites = mRealm.where(DefinitionResponse.class).findAll();
        mDefinitionAdapter = new DefinitionAdapter(mFavorites, null);
        mRecyclerView.setAdapter(mDefinitionAdapter);
    }
}