package com.github.batulovandrey.unofficialurbandictionary.presenter

import java.util.*

/**
 * @author Andrey Batulov on 22/12/2017
 */

interface PopularWordsPresenter {

    val alphabet: ArrayList<String>

    fun getWords(letter: String): ArrayList<String>
}