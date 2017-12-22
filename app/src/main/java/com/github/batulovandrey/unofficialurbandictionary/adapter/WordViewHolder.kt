package com.github.batulovandrey.unofficialurbandictionary.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.github.batulovandrey.unofficialurbandictionary.R
import kotterknife.bindView

/**
 * @author Andrey Batulov on 22/12/2017
 */

class WordViewHolder internal constructor(itemView: View, private val mClickListener: WordClickListener)
    : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private val mWordTextView: TextView by bindView(R.id.word_text_view)

    init {
        itemView.setOnClickListener(this)
    }

    fun setWordText(text: String) {
        mWordTextView.text = text
    }

    override fun onClick(view: View) {
        mClickListener.onItemClick(layoutPosition)
    }
}