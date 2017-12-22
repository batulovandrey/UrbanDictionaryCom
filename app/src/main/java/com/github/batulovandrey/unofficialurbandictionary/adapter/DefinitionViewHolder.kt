package com.github.batulovandrey.unofficialurbandictionary.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.github.batulovandrey.unofficialurbandictionary.R
import kotterknife.bindView

/**
 * @author Andrey Batulov on 22/12/2017
 */

class DefinitionViewHolder internal constructor(itemView: View, private val mClickListener: DefinitionClickListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private val mDefinitionTextView: TextView by bindView(R.id.definition_text_view)
    private val mWordTextView: TextView by bindView(R.id.word_text_view)
    private val mExampleTextView: TextView by bindView(R.id.example_text_view)
    private val mAuthorTextView: TextView by bindView(R.id.author_text_view)

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        mClickListener.onItemClick(layoutPosition)
    }

    fun setDefinitionText(text: String) {
        mDefinitionTextView.text = text
    }

    fun setWordText(text: String) {
        mWordTextView.text = text
    }

    fun setExampleText(text: String) {
        mExampleTextView.text = text
    }

    fun setAuthorText(text: String) {
        mAuthorTextView.text = text
    }
}