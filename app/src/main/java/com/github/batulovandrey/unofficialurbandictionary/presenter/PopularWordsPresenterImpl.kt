package com.github.batulovandrey.unofficialurbandictionary.presenter

import com.github.batulovandrey.unofficialurbandictionary.model.PopularWordsModel
import com.github.batulovandrey.unofficialurbandictionary.view.PopularWordsView
import java.util.*

/**
 * @author Andrey Batulov on 22/12/2017
 */

class PopularWordsPresenterImpl(private val mPopularWordsView: PopularWordsView) : PopularWordsPresenter {

    private val mPopularWordsModel: PopularWordsModel

    override val alphabet: ArrayList<String>
        get() = mPopularWordsModel.alphabet

    init {
        mPopularWordsModel = PopularWordsModel(this)
    }

    override fun getWords(letter: String): ArrayList<String> {
        return mPopularWordsModel.getWords(letter)
    }
}