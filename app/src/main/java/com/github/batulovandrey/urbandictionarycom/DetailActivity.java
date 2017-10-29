package com.github.batulovandrey.urbandictionarycom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.batulovandrey.urbandictionarycom.bean.DefinitionResponse;
import com.github.batulovandrey.urbandictionarycom.realm.RealmManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

import static com.github.batulovandrey.urbandictionarycom.utils.Constants.EXTRA_DEFINITION_ID;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.word_text_view)
    TextView mWordTextView;

    @BindView(R.id.definition_text_view)
    TextView mDefinitionTextView;

    @BindView(R.id.example_text_view)
    TextView mExampleTextView;

    @BindView(R.id.author_text_view)
    TextView mAuthorTextView;

    @BindView(R.id.thumbs_up_text_view)
    TextView mThumbsUpTextView;

    @BindView(R.id.thumbs_down_text_view)
    TextView mThumbsDownTextView;

    @BindView(R.id.permalink_text_view)
    TextView mPermalinkTextView;

    @BindView(R.id.fav_image_view)
    ImageView mFavImageView;

    private Realm mDefinitionsRealm;
    private Realm mFavoriteDefinitionsRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        initToolbar();
        long definitionId = getIntent().getLongExtra(EXTRA_DEFINITION_ID, 0);
        mDefinitionsRealm = new RealmManager(this, "definitions").getRealm();
        mFavoriteDefinitionsRealm = new RealmManager(this, "favorites").getRealm();
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

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setValuesToViews(final long definitionId) {
        final DefinitionResponse definition = mDefinitionsRealm
                .where(DefinitionResponse.class).equalTo("defid", definitionId).findFirst();
        if (definition != null) {
            mWordTextView.setText(definition.getWord());
            mDefinitionTextView.setText(definition.getDefinition());
            mExampleTextView.setText(definition.getExample());
            mAuthorTextView.setText(definition.getAuthor());
            mThumbsUpTextView.setText(String.valueOf(definition.getThumbsUp()));
            mThumbsDownTextView.setText(String.valueOf(definition.getThumbsDown()));
            mPermalinkTextView.setText(definition.getPermalink());
            mFavImageView.setImageResource(isFavorite(definition) ? R.drawable.unfav : R.drawable.fav);
            mFavImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DefinitionResponse temp = mFavoriteDefinitionsRealm
                            .where(DefinitionResponse.class).equalTo("defid", definitionId).findFirst();
                    if (temp == null) {
                        saveToFavorites(definition);
                    } else {
                        deleteFromFavorites(definition);
                    }
                }
            });
        }
    }

    private void saveToFavorites(final DefinitionResponse definition) {
        mFavoriteDefinitionsRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                DefinitionResponse newDefinition = realm.createObject(DefinitionResponse.class);
                newDefinition.setWord(definition.getWord());
                newDefinition.setThumbsUp(definition.getThumbsUp());
                newDefinition.setThumbsDown(definition.getThumbsDown());
                newDefinition.setPermalink(definition.getPermalink());
                newDefinition.setExample(definition.getExample());
                newDefinition.setDefinition(definition.getDefinition());
                newDefinition.setDefid(definition.getDefid());
                newDefinition.setAuthor(definition.getAuthor());
            }
        });
        mFavImageView.animate().rotationBy(360).setDuration(500);
        mFavImageView.setImageResource(isFavorite(definition) ? R.drawable.unfav : R.drawable.fav);
    }

    private void deleteFromFavorites(final DefinitionResponse definition) {
        mFavoriteDefinitionsRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<DefinitionResponse> rows = realm
                        .where(DefinitionResponse.class)
                        .equalTo("defid", definition.getDefid())
                        .findAll();
                rows.deleteAllFromRealm();
            }
        });
        mFavImageView.animate().rotationBy(-360).setDuration(500);
        mFavImageView.setImageResource(isFavorite(definition) ? R.drawable.unfav : R.drawable.fav);
    }

    private boolean isFavorite(DefinitionResponse definition) {
        List<DefinitionResponse> list = mFavoriteDefinitionsRealm.where(DefinitionResponse.class).findAll();
        for (DefinitionResponse def : list) {
            if (def.getDefid() == definition.getDefid())
                return true;
        }
        return false;
    }
}