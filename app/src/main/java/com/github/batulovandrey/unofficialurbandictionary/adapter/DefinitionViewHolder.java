package com.github.batulovandrey.unofficialurbandictionary.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.batulovandrey.unofficialurbandictionary.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Andrey Batulov on 26/10/2017
 */

public class DefinitionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.definition_text_view)
    TextView mDefinitionTextView;

    @BindView(R.id.word_text_view)
    TextView mWordTextView;

    @BindView(R.id.example_text_view)
    TextView mExampleTextView;

    @BindView(R.id.author_text_view)
    TextView mAuthorTextView;

    private DefinitionClickListener mClickListener;

    DefinitionViewHolder(View itemView, DefinitionClickListener listener) {
        super(itemView);
        mClickListener = listener;
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        mClickListener.onItemClick(getLayoutPosition());
    }
}