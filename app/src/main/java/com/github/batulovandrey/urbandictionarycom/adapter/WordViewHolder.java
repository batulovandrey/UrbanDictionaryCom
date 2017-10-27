package com.github.batulovandrey.urbandictionarycom.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.batulovandrey.urbandictionarycom.R;

/**
 * @author Andrey Batulov on 27/10/2017
 */

public class WordViewHolder extends RecyclerView.ViewHolder {

    TextView mWordTextView;

    WordViewHolder(View itemView) {
        super(itemView);
        mWordTextView = itemView.findViewById(R.id.word_text_view);
    }
}