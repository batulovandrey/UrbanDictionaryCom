package com.github.batulovandrey.unofficialurbandictionary.ui.top

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.github.batulovandrey.unofficialurbandictionary.R
import com.github.batulovandrey.unofficialurbandictionary.UrbanDictionaryApp
import com.github.batulovandrey.unofficialurbandictionary.adapter.AlphabetAdapter
import com.github.batulovandrey.unofficialurbandictionary.adapter.WordAdapter
import com.github.batulovandrey.unofficialurbandictionary.ui.main.MainActivity
import kotterknife.bindView
import javax.inject.Inject

class TopWordsFragment : Fragment(), TopWordsMvpView {

    @Inject
    lateinit var topWordPresenter: TopWordsMvpPresenter<TopWordsMvpView>

    private val wordsRecyclerView: RecyclerView by bindView(R.id.words_recycler_view)
    private val lettersRecyclerView: RecyclerView by bindView(R.id.letters_recycler_view)

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        UrbanDictionaryApp.getNetComponent().inject(this)
        return inflater!!.inflate(R.layout.fragment_top_words, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        topWordPresenter.onAttach(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        topWordPresenter.onDetach()
    }

    override fun setWordAdapter(wordAdapter: WordAdapter) {
        wordsRecyclerView.adapter = wordAdapter
    }

    override fun setAlphabetAdapter(alphabetAdapter: AlphabetAdapter) {
        lettersRecyclerView.adapter = alphabetAdapter
    }

    override fun showData(query: String) {
        (activity as MainActivity).redirectToSearchFragment(query)
    }
}