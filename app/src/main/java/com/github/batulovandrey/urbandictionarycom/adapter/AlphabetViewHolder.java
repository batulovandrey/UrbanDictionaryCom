package com.github.batulovandrey.urbandictionarycom.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.batulovandrey.urbandictionarycom.R;

/**
 * @author Andrey Batulov on 27/10/2017
 */

public class AlphabetViewHolder extends RecyclerView.ViewHolder {

    TextView mAlphabetTextView;

    AlphabetViewHolder(View itemView) {
        super(itemView);
        mAlphabetTextView = itemView.findViewById(R.id.alphabet_text_view);
    }
}