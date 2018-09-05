package com.github.batulovandrey.unofficialurbandictionary.ui.cached

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.github.batulovandrey.unofficialurbandictionary.R
import com.github.batulovandrey.unofficialurbandictionary.UrbanDictionaryApp
import com.github.batulovandrey.unofficialurbandictionary.adapter.DefinitionAdapter
import com.github.batulovandrey.unofficialurbandictionary.ui.main.MainActivity
import javax.inject.Inject

class CachedFragment : Fragment(), CachedMvpView {

    @Inject
    lateinit var cachedPresenter: CachedPresenter<CachedMvpView>

    private lateinit var cachedDefinitionsRecyclerView: RecyclerView
    private lateinit var emptyCacheTextView: TextView
    private lateinit var clearCacheFAB: FloatingActionButton
    private lateinit var relativeLayout: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UrbanDictionaryApp.getNetComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_list, container, false)
        cachedDefinitionsRecyclerView = view.findViewById(R.id.definitions_recycler_view)
        emptyCacheTextView = view.findViewById(R.id.empty_fav_text_view)
        emptyCacheTextView.setText(R.string.empty_cached_list)
        clearCacheFAB = view.findViewById(R.id.clear_favorites_action_button)
        relativeLayout = view.findViewById(R.id.relative_layout)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cachedPresenter.onAttach(this)
        clearCacheFAB.setOnClickListener { showAlertDialog() }
    }

    override fun onResume() {
        super.onResume()
        cachedPresenter.loadData()
    }

    override fun onDestroy() {
        cachedPresenter.onDetach()
        super.onDestroy()
    }

    override fun setDefinitionAdapter(adapter: DefinitionAdapter) {
        cachedDefinitionsRecyclerView.adapter = adapter
    }

    override fun showData() {
        cachedDefinitionsRecyclerView.visibility = View.VISIBLE
        emptyCacheTextView.visibility = View.GONE
        relativeLayout.setBackgroundColor(ContextCompat.getColor(activity, R.color.background))
    }

    override fun showPlaceHolder() {
        emptyCacheTextView.visibility = View.VISIBLE
        cachedDefinitionsRecyclerView.visibility = View.GONE
        relativeLayout.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorWhite))
    }

    override fun showDetailFragment() {
        (activity as MainActivity).showDetailFragment()
    }

    override fun showAlertDialog() {
        AlertDialog.Builder(context)
                .setTitle("Clear list of cache")
                .setMessage("All items from cache list will be removed. Are you sure?")
                .setPositiveButton("yes") { _, _ ->
                    cachedPresenter.clearCache()
                }
                .setNegativeButton("no") { dialog, _ -> dialog.dismiss() }
                .show()
    }
}