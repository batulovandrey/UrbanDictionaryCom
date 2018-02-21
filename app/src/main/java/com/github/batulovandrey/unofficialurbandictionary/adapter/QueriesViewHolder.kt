package com.github.batulovandrey.unofficialurbandictionary.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.github.batulovandrey.unofficialurbandictionary.R
import kotterknife.bindView

/**
 * author butul0ve on 28/01/2018
 */

class QueriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val mQueryTextView: TextView by bindView(R.id.query_text_view)
    val mQueryImageButton: ImageButton by bindView(R.id.query_delete_button)
}