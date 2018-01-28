package com.github.batulovandrey.unofficialurbandictionary.adapter

/**
 * author butul0ve on 28/01/2018
 */

interface QueriesClickListener {

    fun onQueryClick(position: Int)

    fun deleteQueryFromRealm(position: Int)
}