package com.github.batulovandrey.urbandictionarycom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.github.batulovandrey.urbandictionarycom.bean.DefinitionResponse;

public class DetailActivity extends AppCompatActivity {

    private static final String EXTRA_DEFINITION = "extra_definition";

    private TextView mWordTextView;
    private TextView mDefinitionTextView;
    private TextView mExampleTextView;
    private TextView mAuthorTextView;
    private TextView mThumbsUpTextView;
    private TextView mThumbsDownTextView;
    private TextView mPermalinkTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initViews();
        Intent intent = getIntent();
        DefinitionResponse definition = intent.getParcelableExtra(EXTRA_DEFINITION);
        setValuesToViews(definition);
    }

    private void initViews() {
        mWordTextView = findViewById(R.id.word_text_view);
        mDefinitionTextView = findViewById(R.id.definition_text_view);
        mExampleTextView = findViewById(R.id.example_text_view);
        mAuthorTextView = findViewById(R.id.author_text_view);
        mThumbsUpTextView = findViewById(R.id.thumbs_up_text_view);
        mThumbsDownTextView = findViewById(R.id.thumbs_down_text_view);
        mPermalinkTextView = findViewById(R.id.permalink_text_view);
    }

    private void setValuesToViews(DefinitionResponse definition) {
        mWordTextView.setText(definition.getWord());
        mDefinitionTextView.setText(definition.getDefinition());
        mExampleTextView.setText(definition.getExample());
        mAuthorTextView.setText(definition.getAuthor());
        mThumbsUpTextView.setText(String.valueOf(definition.getThumbsUp()));
        mThumbsDownTextView.setText(String.valueOf(definition.getThumbsDown()));
        mPermalinkTextView.setText(definition.getPermalink());
    }
}
