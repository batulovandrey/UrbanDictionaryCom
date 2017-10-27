package com.github.batulovandrey.urbandictionarycom.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.batulovandrey.urbandictionarycom.R;

import java.util.List;

/**
 * @author Andrey Batulov on 27/10/2017
 */

public class AlphabetAdapter extends RecyclerView.Adapter<AlphabetViewHolder> {

    private List<String> mAlphabet;

    public AlphabetAdapter(List<String> alphabet) {
        this.mAlphabet = alphabet;
    }

    @Override
    public AlphabetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alphabet_item, null);
        return new AlphabetViewHolder(view);
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