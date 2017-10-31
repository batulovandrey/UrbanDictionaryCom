package com.github.batulovandrey.unofficialurbandictionary.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.batulovandrey.unofficialurbandictionary.R;
import com.github.batulovandrey.unofficialurbandictionary.bean.DefinitionResponse;

import java.util.List;

/**
 * @author Andrey Batulov on 26/10/2017
 */

public class DefinitionAdapter extends RecyclerView.Adapter<DefinitionViewHolder> {

    private List<DefinitionResponse> mDefinitions;
    private DefinitionClickListener mClickListener;

    public DefinitionAdapter(List<DefinitionResponse> definitions,
                             DefinitionClickListener clickListener) {
        mDefinitions = definitions;
        mClickListener = clickListener;
    }

    @Override
    public DefinitionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.definition_item, null);
        return new DefinitionViewHolder(view, mClickListener);
    }

    @Override
    public void onBindViewHolder(DefinitionViewHolder holder, int position) {
        DefinitionResponse definition = mDefinitions.get(position);
        holder.mDefinitionTextView.setText(definition.getDefinition());
        holder.mWordTextView.setText(definition.getWord());
        holder.mExampleTextView.setText(definition.getExample());
        holder.mAuthorTextView.setText(definition.getAuthor());
    }

    @Override
    public int getItemCount() {
        return mDefinitions.size();
    }
}