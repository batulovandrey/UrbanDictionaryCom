package com.github.batulovandrey.unofficialurbandictionary.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.batulovandrey.unofficialurbandictionary.R
import com.github.batulovandrey.unofficialurbandictionary.data.db.model.Definition

/**
 * @author Andrey Batulov on 22/12/2017
 */

class DefinitionAdapter(private val definitions: List<Definition>,
                        private val clickListener: DefinitionClickListener) : RecyclerView.Adapter<DefinitionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefinitionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.definition_item, null)
        return DefinitionViewHolder(view, clickListener)
    }

    override fun onBindViewHolder(holder: DefinitionViewHolder, position: Int) {
        val definition = definitions[position]
        holder.setDefinitionText(definition.definition)
        holder.setAuthorText(definition.author)
        holder.setExampleText(definition.example)
        holder.setWordText(definition.word)
    }

    override fun getItemCount(): Int {
        return definitions.size
    }

    fun getDefinitionByPosition(position: Int) = definitions[position]
}