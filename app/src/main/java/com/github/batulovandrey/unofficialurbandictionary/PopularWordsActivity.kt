package com.github.batulovandrey.unofficialurbandictionary

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import butterknife.ButterKnife
import com.github.batulovandrey.unofficialurbandictionary.presenter.PopularWordsPresenter
import com.github.batulovandrey.unofficialurbandictionary.presenter.PopularWordsPresenterImpl
import com.github.batulovandrey.unofficialurbandictionary.utils.Constants.EXTRA_SEARCH_QUERY
import com.github.batulovandrey.unofficialurbandictionary.view.PopularWordsView
import kotterknife.bindView

/**
 * @author Andrey Batulov on 22/12/2017
 */

class PopularWordsActivity : AppCompatActivity(), AlphabetFragment.OnLetterClickListener, WordsFragment.OnWordClickListener, PopularWordsView {

    private val mToolbar: Toolbar by bindView(R.id.toolbar)

    lateinit var mPopularWordsPresenter: PopularWordsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popular_words)
        ButterKnife.bind(this)
        mPopularWordsPresenter = PopularWordsPresenterImpl(this)
        initToolbar()
        initFragments()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return false
    }

    override fun onWordClick(word: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(EXTRA_SEARCH_QUERY, word)
        startActivity(intent)
    }

    override fun onLetterClick(letter: String) {
        val words = mPopularWordsPresenter.getWords(letter)
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.words_frame_layout, WordsFragment.newInstance(words))
        transaction.commit()
    }

    private fun initToolbar() {
        setSupportActionBar(mToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun initFragments() {
        val alphabet = mPopularWordsPresenter.alphabet
        val words = mPopularWordsPresenter.getWords("a")
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.add(R.id.alphabet_frame_layout, AlphabetFragment.newInstance(alphabet))
        transaction.add(R.id.words_frame_layout, WordsFragment.newInstance(words))
        transaction.commit()
    }
}