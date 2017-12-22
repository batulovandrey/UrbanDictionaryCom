package com.github.batulovandrey.unofficialurbandictionary.view

/**
 * @author Andrey Batulov on 22/12/2017
 */

interface FavoritesView {

    fun showAlertDialog()

    fun showToast(resId: Int)

    fun hideRecycler()

    fun showRecycler()
}