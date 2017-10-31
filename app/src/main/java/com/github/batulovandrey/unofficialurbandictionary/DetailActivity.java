package com.github.batulovandrey.unofficialurbandictionary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.batulovandrey.unofficialurbandictionary.bean.DefinitionResponse;
import com.github.batulovandrey.unofficialurbandictionary.presenter.DetailPresenter;
import com.github.batulovandrey.unofficialurbandictionary.presenter.DetailPresenterImpl;
import com.github.batulovandrey.unofficialurbandictionary.view.DetailView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.github.batulovandrey.unofficialurbandictionary.utils.Constants.EXTRA_DEFINITION_ID;

public class DetailActivity extends AppCompatActivity implements DetailView {

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

    private DetailPresenter mDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        mDetailPresenter = new DetailPresenterImpl(this);
        initToolbar();
        long definitionId = getIntent().getLongExtra(EXTRA_DEFINITION_ID, 0);
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
        final DefinitionResponse definition = mDetailPresenter.getDefinitionByDefIf(definitionId);
        if (definition != null) {
            mWordTextView.setText(definition.getWord());
            mDefinitionTextView.setText(definition.getDefinition());
            mExampleTextView.setText(definition.getExample());
            mAuthorTextView.setText(definition.getAuthor());
            mThumbsUpTextView.setText(String.valueOf(definition.getThumbsUp()));
            mThumbsDownTextView.setText(String.valueOf(definition.getThumbsDown()));
            mPermalinkTextView.setText(definition.getPermalink());
            mFavImageView.setImageResource(mDetailPresenter
                    .isFavoriteDefinition(definition) ? R.drawable.unfav : R.drawable.fav);
            mFavImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDetailPresenter.isAddedToFav(definitionId);
                }
            });
        }
    }

    @Override
    public void setImageResToImageView(int resId) {
        mFavImageView.setImageResource(resId);
        if (resId == R.drawable.fav) {
            mFavImageView.animate().rotationBy(-360).setDuration(500);
        } else {
            mFavImageView.animate().rotationBy(360).setDuration(500);
        }
    }
}