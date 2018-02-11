package com.github.batulovandrey.unofficialurbandictionary.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.github.batulovandrey.unofficialurbandictionary.R
import com.github.batulovandrey.unofficialurbandictionary.presenter.PopularWordsPresenter
import com.github.batulovandrey.unofficialurbandictionary.presenter.PopularWordsPresenterImpl

class PopularWordsFragment : Fragment(), PopularWordsView {

    private lateinit var mPopularWordsPresenter: PopularWordsPresenter
    private var mLetter: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPopularWordsPresenter = PopularWordsPresenterImpl(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_popular_words, container, false)
        when (mLetter) {
            null -> initFragments("a")
            "" -> initFragments("a")
            else -> initFragments(mLetter!!)
        }
        return view
    }

    fun showWordsByLetter(letter: String) {
        val words = mPopularWordsPresenter.getWords(letter)
        val manager = activity.supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.words_frame_layout, WordsFragment.newInstance(words))
        transaction.commit()
    }

    private fun initFragments(letter: String) {
        val alphabet = mPopularWordsPresenter.alphabet
        val words = mPopularWordsPresenter.getWords(letter)
        val manager = activity.supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.alphabet_frame_layout, AlphabetFragment.newInstance(alphabet))
        transaction.replace(R.id.words_frame_layout, WordsFragment.newInstance(words))
        transaction.commit()
    }
}