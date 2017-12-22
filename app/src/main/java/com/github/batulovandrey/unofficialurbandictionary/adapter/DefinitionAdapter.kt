package com.github.batulovandrey.unofficialurbandictionary.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.batulovandrey.unofficialurbandictionary.R
import com.github.batulovandrey.unofficialurbandictionary.bean.DefinitionResponse

/**
 * @author Andrey Batulov on 22/12/2017
 */

class DefinitionAdapter(private val mDefinitions: List<DefinitionResponse>,
                        private val mClickListener: DefinitionClickListener) : RecyclerView.Adapter<DefinitionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefinitionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.definition_item, null)
        return DefinitionViewHolder(view, mClickListener)
    }

    override fun onBindViewHolder(holder: DefinitionViewHolder, position: Int) {
        val definition = mDefinitions[position]
        holder.setDefinitionText(definition.definition)
        holder.setAuthorText(definition.author)
        holder.setExampleText(definition.example)
        holder.setWordText(definition.word)
    }

    override fun getItemCount(): Int {
        return mDefinitions.size
    }
}