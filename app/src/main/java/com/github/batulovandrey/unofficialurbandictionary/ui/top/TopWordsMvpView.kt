package com.github.batulovandrey.unofficialurbandictionary.ui.top

import com.github.batulovandrey.unofficialurbandictionary.adapter.AlphabetAdapter
import com.github.batulovandrey.unofficialurbandictionary.adapter.WordAdapter
import com.github.batulovandrey.unofficialurbandictionary.ui.MvpView

interface TopWordsMvpView: MvpView {

    fun setWordAdapter(wordAdapter: WordAdapter)

    fun setAlphabetAdapter(alphabetAdapter: AlphabetAdapter)

    fun showData(query: String)
}