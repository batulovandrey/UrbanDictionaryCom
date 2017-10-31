package com.github.batulovandrey.unofficialurbandictionary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.github.batulovandrey.unofficialurbandictionary.presenter.PopularWordsPresenter;
import com.github.batulovandrey.unofficialurbandictionary.presenter.PopularWordsPresenterImpl;
import com.github.batulovandrey.unofficialurbandictionary.view.PopularWordsView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.github.batulovandrey.unofficialurbandictionary.utils.Constants.EXTRA_SEARCH_QUERY;

public class PopularWordsActivity extends AppCompatActivity
        implements AlphabetFragment.OnLetterClickListener,
        WordsFragment.OnWordClickListener, PopularWordsView {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private PopularWordsPresenter mPopularWordsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_words);
        ButterKnife.bind(this);
        mPopularWordsPresenter = new PopularWordsPresenterImpl(this);
        initToolbar();
        initFragments();
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

    @Override
    public void onWordClick(String word) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(EXTRA_SEARCH_QUERY, word);
        startActivity(intent);
    }

    @Override
    public void onLetterClick(String letter) {
        ArrayList<String> words = mPopularWordsPresenter.getWords(letter);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.words_frame_layout, WordsFragment.newInstance(words));
        transaction.commit();
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initFragments() {
        ArrayList<String> alphabet = mPopularWordsPresenter.getAlphabet();
        ArrayList<String> words = mPopularWordsPresenter.getWords("a");
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.alphabet_frame_layout, AlphabetFragment.newInstance(alphabet));
        transaction.add(R.id.words_frame_layout, WordsFragment.newInstance(words));
        transaction.commit();
    }
}