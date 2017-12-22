package com.github.batulovandrey.unofficialurbandictionary.model

import com.github.batulovandrey.unofficialurbandictionary.UrbanDictionaryApp
import com.github.batulovandrey.unofficialurbandictionary.data.PopularWords
import com.github.batulovandrey.unofficialurbandictionary.presenter.PopularWordsPresenter
import java.util.*
import javax.inject.Inject

/**
 * @author Andrey Batulov on 22/12/2017
 */

class PopularWordsModel(private val mPopularWordsPresenter: PopularWordsPresenter) {

    @Inject
    lateinit var mWords: PopularWords

    private val mDictionary: Map<String, List<String>>

    val alphabet: ArrayList<String>
        get() {
            val alphabet = ArrayList<String>()
            for ((key) in mDictionary) {
                alphabet.add(key)
            }
            return alphabet
        }

    init {
        UrbanDictionaryApp.getNetComponent().inject(this)
        mDictionary = mWords.dictionary
    }

    fun getWords(letter: String): ArrayList<String> {
        val words = ArrayList<String>()
        for ((key, value) in mDictionary) {
            if (key == letter) {
                words.addAll(value)
            }
        }
        return words
    }
}