package com.github.batulovandrey.unofficialurbandictionary.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.batulovandrey.unofficialurbandictionary.R

/**
 * @author Andrey Batulov on 22/12/2017
 */

class AlphabetAdapter(private val mAlphabet: List<String>,
                      private val mClickListener: AlphabetClickListener) : RecyclerView.Adapter<AlphabetViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlphabetViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.alphabet_item, null)
        return AlphabetViewHolder(view, mClickListener)
    }

    override fun onBindViewHolder(holder: AlphabetViewHolder, position: Int) {
        val letter = mAlphabet[position]
        holder.setAlphabetText(letter)
    }

    override fun getItemCount(): Int {
        return mAlphabet.size
    }
}