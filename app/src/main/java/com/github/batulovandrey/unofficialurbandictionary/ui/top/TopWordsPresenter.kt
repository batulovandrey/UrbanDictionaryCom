package com.github.batulovandrey.unofficialurbandictionary.ui.top

import com.github.batulovandrey.unofficialurbandictionary.adapter.AlphabetAdapter
import com.github.batulovandrey.unofficialurbandictionary.adapter.AlphabetClickListener
import com.github.batulovandrey.unofficialurbandictionary.adapter.WordAdapter
import com.github.batulovandrey.unofficialurbandictionary.adapter.WordClickListener
import com.github.batulovandrey.unofficialurbandictionary.data.DataManager
import com.github.batulovandrey.unofficialurbandictionary.data.WordsRepository
import com.github.batulovandrey.unofficialurbandictionary.presenter.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopWordsPresenter<V : TopWordsMvpView> @Inject constructor(private val repository: WordsRepository,
                                                                 dataManager: DataManager,
                                                                 compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, compositeDisposable),
        TopWordsMvpPresenter<V>,
        WordClickListener,
        AlphabetClickListener {

    private lateinit var wordAdapter: WordAdapter
    private lateinit var alphabetAdapter: AlphabetAdapter

    override fun onAttach(mvpView: V) {
        super.onAttach(mvpView)

        initializeAlphabet()
        updateWords()
    }

    override fun initializeAlphabet() {
        alphabetAdapter = AlphabetAdapter(repository.alphabet, this)
        mvpView?.setAlphabetAdapter(alphabetAdapter)
    }

    override fun updateWords(letter: String) {
        wordAdapter = WordAdapter(repository.getWords(letter), this)
        mvpView?.setWordAdapter(wordAdapter)
    }

    override fun onWordClick(position: Int) {
        val word = wordAdapter.getWord(position)
        mvpView?.showData(word)
    }

    override fun onLetterClick(position: Int) {
        val letter = repository.alphabet[position]
        updateWords(letter)
    }
}