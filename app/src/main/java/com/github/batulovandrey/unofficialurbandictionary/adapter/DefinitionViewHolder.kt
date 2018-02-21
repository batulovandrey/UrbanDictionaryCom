package com.github.batulovandrey.unofficialurbandictionary.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.definition_item.view.*

/**
 * @author Andrey Batulov on 22/12/2017
 */

class DefinitionViewHolder internal constructor(itemView: View, private val mClickListener: DefinitionClickListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        mClickListener.onItemClick(layoutPosition)
    }

    fun setDefinitionText(text: String) {
        itemView.definition_text_view.text = text
    }

    fun setWordText(text: String) {
        itemView.word_text_view.text = text
    }

    fun setExampleText(text: String) {
        itemView.example_text_view.text = text
    }

    fun setAuthorText(text: String) {
        itemView.author_text_view.text = text
    }
}