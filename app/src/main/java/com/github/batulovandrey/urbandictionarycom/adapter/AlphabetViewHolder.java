package com.github.batulovandrey.urbandictionarycom.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.batulovandrey.urbandictionarycom.R;

/**
 * @author Andrey Batulov on 27/10/2017
 */

public class AlphabetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView mAlphabetTextView;

    private AlphabetClickListener mClickListener;

    AlphabetViewHolder(View itemView, AlphabetClickListener listener) {
        super(itemView);
        mClickListener = listener;
        mAlphabetTextView = itemView.findViewById(R.id.alphabet_text_view);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        mClickListener.onItemClick(getLayoutPosition());
    }
}