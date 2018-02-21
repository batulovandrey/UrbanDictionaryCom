package com.github.batulovandrey.unofficialurbandictionary.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.alphabet_item.view.*

/**
 * @author Andrey Batulov on 22/12/2017
 */

class AlphabetViewHolder internal constructor(itemView: View, private val mClickListener: AlphabetClickListener)
    : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    init {
        itemView.setOnClickListener(this)
    }

    fun setAlphabetText(text: String) {
        itemView.alphabet_text_view.text = text
    }

    override fun onClick(view: View) {
        mClickListener.onItemClick(layoutPosition)
    }
}