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

public class AlphabetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.alphabet_text_view)
    TextView mAlphabetTextView;

    private AlphabetClickListener mClickListener;

    AlphabetViewHolder(View itemView, AlphabetClickListener listener) {
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