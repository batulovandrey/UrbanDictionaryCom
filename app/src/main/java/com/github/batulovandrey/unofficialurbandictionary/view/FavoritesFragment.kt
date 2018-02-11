package com.github.batulovandrey.unofficialurbandictionary.view

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast

import com.github.batulovandrey.unofficialurbandictionary.R
import com.github.batulovandrey.unofficialurbandictionary.adapter.DefinitionClickListener
import com.github.batulovandrey.unofficialurbandictionary.presenter.FavoritesPresenter
import com.github.batulovandrey.unofficialurbandictionary.presenter.FavoritesPresenterImpl

class FavoritesFragment : Fragment(), FavoritesView, DefinitionClickListener {

    private lateinit var  mFavoritesDefinitionsRecyclerView: RecyclerView
    private lateinit var  mEmptyFavTextView: TextView
    private lateinit var mClearFavFAB: FloatingActionButton

    private lateinit var mFavoritesPresenter: FavoritesPresenter
    private var mListenerFavorites: OnFavoritesFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFavoritesPresenter = FavoritesPresenterImpl(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_favorites, container, false)
        mFavoritesDefinitionsRecyclerView = view.findViewById(R.id.definitions_recycler_view)
        mEmptyFavTextView = view.findViewById(R.id.empty_fav_text_view)
        mClearFavFAB = view.findViewById(R.id.clear_favorites_action_button)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mFavoritesDefinitionsRecyclerView.adapter = mFavoritesPresenter.definitionAdapter
        mClearFavFAB.setOnClickListener({ mFavoritesPresenter.showAlertDialog() })
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFavoritesFragmentInteractionListener) {
            mListenerFavorites = context
        } else {
//            throw RuntimeException(context!!.toString() + " must implement OnFavoritesFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListenerFavorites = null
    }

    override fun showAlertDialog() {
        AlertDialog.Builder(context)
                .setTitle("Clear list of favorites")
                .setMessage("All items from favorite list will be removed. Are you sure?")
                .setPositiveButton("yes") { _, _ ->
                    mFavoritesPresenter.clearList()
                    mFavoritesDefinitionsRecyclerView.adapter = mFavoritesPresenter.definitionAdapter
                }.show()
    }

    override fun showToast(resId: Int) {
        Toast.makeText(context, resId, Toast.LENGTH_LONG).show()
    }

    override fun hideRecycler() {
        mFavoritesDefinitionsRecyclerView.visibility = View.GONE
        mEmptyFavTextView.visibility = View.VISIBLE
    }

    override fun showRecycler() {
        mFavoritesDefinitionsRecyclerView.visibility = View.VISIBLE
        mEmptyFavTextView.visibility = View.GONE
    }

    override fun onItemClick(position: Int) {
        val definition = mFavoritesPresenter.favorites[position]
        val detailFragment = DetailFragment.newInstance(definition.defid)
        val fragmentManager = activity.supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout, detailFragment)
        transaction.setTransition(TRANSIT_FRAGMENT_OPEN)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    interface OnFavoritesFragmentInteractionListener {

        fun onFavoriteDefinitionClick(uri: Uri)
    }
}