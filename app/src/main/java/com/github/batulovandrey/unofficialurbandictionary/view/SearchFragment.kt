package com.github.batulovandrey.unofficialurbandictionary.view

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.github.batulovandrey.unofficialurbandictionary.R
import com.github.batulovandrey.unofficialurbandictionary.UrbanDictionaryApp
import com.github.batulovandrey.unofficialurbandictionary.adapter.DefinitionAdapter
import com.github.batulovandrey.unofficialurbandictionary.adapter.DefinitionClickListener
import com.github.batulovandrey.unofficialurbandictionary.adapter.QueriesAdapter
import com.github.batulovandrey.unofficialurbandictionary.ui.main.MainMvpPresenter
import com.github.batulovandrey.unofficialurbandictionary.ui.main.MainMvpView
import com.github.batulovandrey.unofficialurbandictionary.utils.Utils
import javax.inject.Inject

class SearchFragment : Fragment(), SearchView.OnQueryTextListener, DefinitionClickListener,
        MainMvpView {

    @Inject
    lateinit var mPresenter: MainMvpPresenter<MainMvpView>

    private lateinit var mSearchView: SearchView
    private lateinit var mDefinitionsRecyclerView: RecyclerView
    private lateinit var mUserQueriesRecyclerView: RecyclerView
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mHintTextView: TextView

    private lateinit var mQuery: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UrbanDictionaryApp.getNetComponent().inject(this)
        mPresenter.onAttach(this)
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
            mPresenter.getData(mQuery)
            initializeQueryToServer(mQuery)
            arguments = null
        }

        mSearchView.clearFocus()
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        initSearchView()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()) {
            mPresenter.getData(query!!)
            Utils.hideKeyboard(mUserQueriesRecyclerView, context)
            return true
        }
        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {
        mPresenter.filterQueries(newText)
        return true
    }

    override fun showToast(resId: Int) {
        Toast.makeText(context, getString(resId), Toast.LENGTH_LONG).show()
    }

    override fun showHint() {
        mHintTextView.visibility = View.VISIBLE
        mUserQueriesRecyclerView.visibility = View.GONE
        mDefinitionsRecyclerView.visibility = View.GONE
    }

    override fun onItemClick(position: Int) {

    }

    override fun initializeQueryToServer(query: String) {
        onQueryTextSubmit(query)
    }

    // mainMvpView's methods

    override fun showDefinitions() {
        mDefinitionsRecyclerView.visibility = View.VISIBLE
        mUserQueriesRecyclerView.visibility = View.GONE
        mHintTextView.visibility = View.GONE
    }

    override fun showQueries() {
        mUserQueriesRecyclerView.visibility = View.VISIBLE
        mDefinitionsRecyclerView.visibility = View.GONE
        mHintTextView.visibility = View.GONE    }

    override fun setDefinitionAdapter(definitionAdapter: DefinitionAdapter) {
        mDefinitionsRecyclerView.adapter = definitionAdapter
    }

    override fun setQueriesAdapter(queriesAdapter: QueriesAdapter) {
        mUserQueriesRecyclerView.adapter = queriesAdapter
    }

    override fun closeNavigationDrawer() {}

    override fun showSearchFragment() {}

    override fun showPopularWordsFragment() {}

    override fun showFavoritesFragment() {}

    override fun showDetailFragment() {}

    override fun showLoading() {
        mProgressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        mProgressBar.visibility = View.GONE
    }

    override fun hideKeyboard() {}

    override fun isNetworkConnected(): Boolean {
        return true
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