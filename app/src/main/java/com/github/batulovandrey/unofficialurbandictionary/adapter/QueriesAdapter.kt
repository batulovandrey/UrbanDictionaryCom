package com.github.batulovandrey.unofficialurbandictionary.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.batulovandrey.unofficialurbandictionary.R
import com.github.batulovandrey.unofficialurbandictionary.data.db.model.SavedUserQuery

/**
 * author butul0ve on 28/01/2018
 */

class QueriesAdapter(private val queries: List<SavedUserQuery>,
                     private val clickListener: QueriesClickListener) : RecyclerView.Adapter<QueriesViewHolder>() {

    override fun getItemCount(): Int {
        return queries.size
    }

    override fun onBindViewHolder(holder: QueriesViewHolder, position: Int) {
        val userQuery = queries[position]
        holder.mQueryTextView.text = userQuery.text
        holder.mQueryTextView.setOnClickListener({ clickListener.onQueryClick(position) })
        holder.mQueryImageButton.setOnClickListener({ clickListener.deleteQueryFromRealm(position) })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QueriesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.query_item, null)
        return QueriesViewHolder(view)
    }

    fun getQuery(position: Int): SavedUserQuery {
        return queries[position]
    }

    fun removeQuery(position: Int) {
        (queries as MutableList).removeAt(position)
        notifyItemChanged(position)
    }
}