package com.github.batulovandrey.unofficialurbandictionary.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.batulovandrey.unofficialurbandictionary.R

/**
 * @author Andrey Batulov on 22/12/2017
 */

class WordAdapter(private val mWords: List<String>, private val mClickListener: WordClickListener)
    : RecyclerView.Adapter<WordViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.word_item, null)
        return WordViewHolder(view, mClickListener)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val word = mWords[position]
        holder.setWordText(word)
    }

    override fun getItemCount(): Int {
        return mWords.size
    }

    fun getWord(position: Int) = mWords[position]
}