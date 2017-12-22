package com.github.batulovandrey.unofficialurbandictionary.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.github.batulovandrey.unofficialurbandictionary.R
import kotterknife.bindView

/**
 * @author Andrey Batulov on 22/12/2017
 */

class AlphabetViewHolder internal constructor(itemView: View, private val mClickListener: AlphabetClickListener)
    : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private val mAlphabetTextView: TextView by bindView(R.id.alphabet_text_view)

    init {
        itemView.setOnClickListener(this)
    }

    fun setAlphabetText(text: String) {
        mAlphabetTextView.text = text
    }

    override fun onClick(view: View) {
        mClickListener.onItemClick(layoutPosition)
    }
}