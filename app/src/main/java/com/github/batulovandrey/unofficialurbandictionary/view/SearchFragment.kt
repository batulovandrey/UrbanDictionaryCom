package com.github.batulovandrey.unofficialurbandictionary.view

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast

import com.github.batulovandrey.unofficialurbandictionary.R
import com.github.batulovandrey.unofficialurbandictionary.adapter.DefinitionAdapter
import com.github.batulovandrey.unofficialurbandictionary.adapter.DefinitionClickListener
import com.github.batulovandrey.unofficialurbandictionary.adapter.QueriesAdapter
import com.github.batulovandrey.unofficialurbandictionary.presenter.MainPresenter
import com.github.batulovandrey.unofficialurbandictionary.presenter.MainPresenterImpl
import com.github.batulovandrey.unofficialurbandictionary.utils.Utils

class SearchFragment : Fragment(), SearchView.OnQueryTextListener, MainView, DefinitionClickListener {

    private lateinit var mSearchView: SearchView
    private lateinit var mDefinitionsRecyclerView: RecyclerView
    private lateinit var mUserQueriesRecyclerView: RecyclerView
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mHintTextView: TextView

    private lateinit var mMainPresenter: MainPresenter
    private lateinit var mQuery: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMainPresenter = MainPresenterImpl(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_search, container, false)
        mSearchView = view.findViewById(R.id.search_view)
        mDefinitionsRecyclerView = view.findViewById(R.id.definitions_recycler_view)
        mUserQueriesRecyclerView = view.findViewById(R.id.user_queries_recycler_view)
        mProgressBar = view.findViewById(R.id.progress_bar)
        mHintTextView = view.findViewById(R.id.hint_text_view)
        return view
    }

    override fun onStart() {
        super.onStart()

        if (arguments != null && arguments.containsKey(EXTRA_QUERY)) {
            mQuery = arguments.getString(EXTRA_QUERY)
            initializeQueryToServer(mQuery)
            arguments = null
        } else {
            mMainPresenter.getDataFromCache()
        }
        mSearchView.clearFocus()
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        initSearchView()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()) {
            mMainPresenter.getData(query!!, this)
            mMainPresenter.saveQueryToRealm(query)
            showDataInRecycler()
            Utils.hideKeyboard(mUserQueriesRecyclerView, context)
            return true
        }
        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {
        return mMainPresenter.textChanged(newText)
    }

    override fun showDataInRecycler() {
        mDefinitionsRecyclerView.visibility = View.VISIBLE
        mUserQueriesRecyclerView.visibility = View.GONE
        mHintTextView.visibility = View.GONE
    }

    override fun showQueriesInListView() {
        mUserQueriesRecyclerView.visibility = View.VISIBLE
        mDefinitionsRecyclerView.visibility = View.GONE
        mHintTextView.visibility = View.GONE
    }

    override fun setAdapterToDefinitionsRecycler(definitionsAdapter: DefinitionAdapter) {
        mDefinitionsRecyclerView.adapter = definitionsAdapter
    }

    override fun setAdapterToQueriesRecycler(queriesAdapter: QueriesAdapter) {
        mUserQueriesRecyclerView.adapter = queriesAdapter
    }

    override fun showToast(resId: Int) {
        Toast.makeText(context, getString(resId), Toast.LENGTH_LONG).show()
    }

    override fun showProgressbar() {
        mProgressBar.visibility = View.VISIBLE
    }

    override fun hideProgressbar() {
        mProgressBar.visibility = View.GONE
    }

    override fun showHint() {
        mHintTextView.visibility = View.VISIBLE
        mUserQueriesRecyclerView.visibility = View.GONE
        mDefinitionsRecyclerView.visibility = View.GONE
    }

    override fun onItemClick(position: Int) {
        val definitionId = mMainPresenter.getDefinitionId(position)
        val detailFragment = DetailFragment.newInstance(definitionId)
        val fragmentManager = activity.supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout, detailFragment)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun initializeQueryToServer(query: String) {
        onQueryTextSubmit(query)
    }

    private fun initSearchView() {
        val searchManager = activity.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(activity.componentName))
        mSearchView.isFocusable = true
        mSearchView.isIconified = false
        mSearchView.setOnQueryTextListener(this)
    }

    companion object {

        private const val EXTRA_QUERY = "extra_query"

        fun newInstance(query: String): SearchFragment {
            val fragment = SearchFragment()
            val args = Bundle()
            args.putString(EXTRA_QUERY, query)
            fragment.arguments = args
            return fragment
        }
    }
}