package com.github.batulovandrey.unofficialurbandictionary.data

import com.github.batulovandrey.unofficialurbandictionary.UrbanDictionaryApp
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WordsRepository @Inject constructor(private val words: PopularWords) {

    private val dictionary: Map<String, List<String>> = words.dictionary

    init {
        UrbanDictionaryApp.netComponent.inject(this)
    }

    val alphabet: ArrayList<String>
        get() {
            val alphabet = ArrayList<String>()
            for ((key) in dictionary) {
                alphabet.add(key)
            }
            return alphabet
        }

    fun getWords(letter: String): ArrayList<String> {
        val words = ArrayList<String>()
        for ((key, value) in dictionary) {
            if (key == letter) {
                words.addAll(value)
            }
        }
        return words
    }
}