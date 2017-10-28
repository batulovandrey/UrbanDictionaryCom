package com.github.batulovandrey.urbandictionarycom.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.batulovandrey.urbandictionarycom.R;

/**
 * @author Andrey Batulov on 27/10/2017
 */

public class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView mWordTextView;

    private WordClickListener mClickListener;

    WordViewHolder(View itemView, WordClickListener listener) {
        super(itemView);
        mClickListener = listener;
        mWordTextView = itemView.findViewById(R.id.word_text_view);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        mClickListener.onItemClick(getLayoutPosition());
    }
}