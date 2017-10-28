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

public class WordAdapter extends RecyclerView.Adapter<WordViewHolder> {

    private List<String> mWords;
    private WordClickListener mClickListener;

    public WordAdapter(List<String> words, WordClickListener listener) {
        mWords = words;
        mClickListener = listener;
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_item, null);
        return new WordViewHolder(view, mClickListener);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        String word = mWords.get(position);
        holder.mWordTextView.setText(word);
    }

    @Override
    public int getItemCount() {
        return mWords.size();
    }
}