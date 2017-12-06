package com.github.batulovandrey.unofficialurbandictionary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.batulovandrey.unofficialurbandictionary.bean.DefinitionResponse;
import com.github.batulovandrey.unofficialurbandictionary.presenter.DetailPresenter;
import com.github.batulovandrey.unofficialurbandictionary.presenter.DetailPresenterImpl;
import com.github.batulovandrey.unofficialurbandictionary.view.DetailView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
            getSupportActionBar().setTitle("Definition of " + definition.getWord());
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
        final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        RotateAnimation animation;
        if (resId == R.drawable.fav) {
            animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        } else {
            animation = new RotateAnimation(0f, -360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        }
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setDuration(300);
        mFavImageView.startAnimation(animation);
        executorService.schedule(new Runnable() {
            @Override
            public void run() {
                mFavImageView.setAnimation(null);
            }
        }, 300, TimeUnit.MILLISECONDS);
    }
}