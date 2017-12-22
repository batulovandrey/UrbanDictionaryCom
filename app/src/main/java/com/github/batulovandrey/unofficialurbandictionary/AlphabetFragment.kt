package com.github.batulovandrey.unofficialurbandictionary

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.batulovandrey.unofficialurbandictionary.adapter.AlphabetAdapter
import com.github.batulovandrey.unofficialurbandictionary.adapter.AlphabetClickListener
import com.github.batulovandrey.unofficialurbandictionary.utils.Constants.EXTRA_ALPHABET_LIST
import kotterknife.bindView
import java.util.*

/**
 * @author Andrey Batulov on 22/12/2017
 */


class AlphabetFragment : Fragment(), AlphabetClickListener {

    private val mRecyclerView: RecyclerView by bindView(R.id.recycler_view)

    private lateinit var mAlphabetList: List<String>
    private var mListener: OnLetterClickListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mAlphabetList = arguments.getStringArrayList(EXTRA_ALPHABET_LIST)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = AlphabetAdapter(mAlphabetList, this)
        mRecyclerView.adapter = adapter
    }

    fun onButtonPressed(letter: String) {
        if (mListener != null) {
            mListener!!.onLetterClick(letter)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnLetterClickListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnLetterClickListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    override fun onItemClick(position: Int) {
        mListener!!.onLetterClick(mAlphabetList[position])
    }

    interface OnLetterClickListener {

        fun onLetterClick(letter: String)
    }

    companion object {

        @JvmStatic
        fun newInstance(list: ArrayList<String>): AlphabetFragment {
            val fragment = AlphabetFragment()
            val args = Bundle()
            args.putStringArrayList(EXTRA_ALPHABET_LIST, list)
            fragment.arguments = args
            return fragment
        }
    }
}