package com.github.batulovandrey.unofficialurbandictionary.view

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.batulovandrey.unofficialurbandictionary.R
import com.github.batulovandrey.unofficialurbandictionary.adapter.WordAdapter
import com.github.batulovandrey.unofficialurbandictionary.adapter.WordClickListener
import kotterknife.bindView
import java.util.*

/**
 * @author Andrey Batulov on 22/12/2017
 */

class WordsFragment : Fragment(), WordClickListener {

    private val mRecyclerView: RecyclerView by bindView(R.id.definitions_recycler_view)

    private lateinit var mWords: List<String>
    private var mListener: OnWordClickListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mWords = arguments.getStringArrayList(EXTRA_WORDS_LIST)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val wordAdapter = WordAdapter(mWords, this)
        mRecyclerView.adapter = wordAdapter
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnWordClickListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnWordClickListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    override fun onItemClick(position: Int) {
        mListener!!.onWordClick(mWords[position])
    }

    interface OnWordClickListener {

        fun onWordClick(word: String)
    }

    companion object {

        private val EXTRA_WORDS_LIST = "extra_words_list"

        fun newInstance(list: ArrayList<String>): WordsFragment {
            val fragment = WordsFragment()
            val args = Bundle()
            args.putStringArrayList(EXTRA_WORDS_LIST, list)
            fragment.arguments = args
            return fragment
        }
    }
}