package com.github.batulovandrey.urbandictionarycom.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.batulovandrey.urbandictionarycom.R;

/**
 * @author Andrey Batulov on 26/10/2017
 */

public class DefinitionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView mDefinitionTextView;
    TextView mWordTextView;

    private DefinitionClickListener mClickListener;

    public DefinitionViewHolder(View itemView, DefinitionClickListener listener) {
        super(itemView);
        mClickListener = listener;
        mDefinitionTextView = itemView.findViewById(R.id.definition_text_view);
        mWordTextView = itemView.findViewById(R.id.word_text_view);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        mClickListener.onItemClick(getLayoutPosition());
    }
}