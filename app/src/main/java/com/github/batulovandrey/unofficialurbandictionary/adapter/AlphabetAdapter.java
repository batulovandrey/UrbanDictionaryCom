package com.github.batulovandrey.unofficialurbandictionary.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.batulovandrey.unofficialurbandictionary.R;

import java.util.List;

/**
 * @author Andrey Batulov on 27/10/2017
 */

public class AlphabetAdapter extends RecyclerView.Adapter<AlphabetViewHolder> {

    private List<String> mAlphabet;
    private AlphabetClickListener mClickListener;

    public AlphabetAdapter(List<String> alphabet, AlphabetClickListener listener) {
        this.mAlphabet = alphabet;
        mClickListener = listener;
    }

    @Override
    public AlphabetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alphabet_item, null);
        return new AlphabetViewHolder(view, mClickListener);
    }

    @Override
    public void onBindViewHolder(AlphabetViewHolder holder, int position) {
        String letter = mAlphabet.get(position);
        holder.mAlphabetTextView.setText(letter);
    }

    @Override
    public int getItemCount() {
        return mAlphabet.size();
    }
}