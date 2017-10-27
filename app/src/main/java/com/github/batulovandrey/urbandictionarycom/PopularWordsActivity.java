package com.github.batulovandrey.urbandictionarycom;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.github.batulovandrey.urbandictionarycom.data.PopularWords;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PopularWordsActivity extends AppCompatActivity
        implements AlphabetFragment.OnLetterClickListener, WordsFragment.OnWordClickListener {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_words);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        PopularWords popularWords = new PopularWords(this);
        Map<String, List<String>> dictionary = popularWords.getDictionary();
        ArrayList<String> alphabet = new ArrayList<>();
        ArrayList<String> words = new ArrayList<>();
        for (Map.Entry<String, List<String>> map : dictionary.entrySet()) {
            alphabet.add(map.getKey());
            if (map.getKey().equals("a")) {
                words.addAll(map.getValue());
            }
        }
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.alphabet_frame_layout, AlphabetFragment.newInstance(alphabet));
        transaction.add(R.id.words_frame_layout, WordsFragment.newInstance(words));
        transaction.commit();
    }

    @Override
    public void onWordClick(String word) {
        Toast.makeText(this, word, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLetterClick(String letter) {
        Toast.makeText(this, letter, Toast.LENGTH_LONG).show();
    }
}