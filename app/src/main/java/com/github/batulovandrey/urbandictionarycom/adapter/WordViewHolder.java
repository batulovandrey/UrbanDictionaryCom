package com.github.batulovandrey.urbandictionarycom.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.batulovandrey.urbandictionarycom.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Andrey Batulov on 27/10/2017
 */

public class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.word_text_view)
    TextView mWordTextView;

    private WordClickListener mClickListener;

    WordViewHolder(View itemView, WordClickListener listener) {
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