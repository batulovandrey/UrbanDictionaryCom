package com.github.batulovandrey.urbandictionarycom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.batulovandrey.urbandictionarycom.bean.DefinitionResponse;
import com.github.batulovandrey.urbandictionarycom.realm.RealmManager;

import java.util.List;

import io.realm.Realm;

public class DetailActivity extends AppCompatActivity {

    private static final String EXTRA_DEFINITION_ID = "extra_definition_id";

    private TextView mWordTextView;
    private TextView mDefinitionTextView;
    private TextView mExampleTextView;
    private TextView mAuthorTextView;
    private TextView mThumbsUpTextView;
    private TextView mThumbsDownTextView;
    private TextView mPermalinkTextView;
    private Toolbar mToolbar;
    private ImageView mFavImageView;
    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initViews();
        Intent intent = getIntent();
        long definitionId = intent.getLongExtra(EXTRA_DEFINITION_ID, 0);
        mRealm = new RealmManager(this, "definitions").getRealm();
        setValuesToViews(definitionId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return false;
    }

    private void initViews() {
        initToolbar();
        mWordTextView = findViewById(R.id.word_text_view);
        mDefinitionTextView = findViewById(R.id.definition_text_view);
        mExampleTextView = findViewById(R.id.example_text_view);
        mAuthorTextView = findViewById(R.id.author_text_view);
        mThumbsUpTextView = findViewById(R.id.thumbs_up_text_view);
        mThumbsDownTextView = findViewById(R.id.thumbs_down_text_view);
        mPermalinkTextView = findViewById(R.id.permalink_text_view);
        mFavImageView = findViewById(R.id.fav_image_view);
    }

    private void initToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setValuesToViews(long definitionId) {
        List<DefinitionResponse> list = mRealm.where(DefinitionResponse.class).findAll();
        for (DefinitionResponse definition : list) {
            if (definition.getDefid() == definitionId) {
                mWordTextView.setText(definition.getWord());
                mDefinitionTextView.setText(definition.getDefinition());
                mExampleTextView.setText(definition.getExample());
                mAuthorTextView.setText(definition.getAuthor());
                mThumbsUpTextView.setText(String.valueOf(definition.getThumbsUp()));
                mThumbsDownTextView.setText(String.valueOf(definition.getThumbsDown()));
                mPermalinkTextView.setText(definition.getPermalink());
                mFavImageView.setImageResource(isFavorite(definition) ? R.drawable.fav : R.drawable.unfav);
            }
        }
    }

    private boolean isFavorite(DefinitionResponse definition) {
        return definition.getDefid() == mRealm.where(DefinitionResponse.class).findFirst().getDefid();
    }
}