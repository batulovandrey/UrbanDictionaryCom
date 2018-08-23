package com.github.batulovandrey.unofficialurbandictionary.ui.main

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
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
import com.github.batulovandrey.unofficialurbandictionary.adapter.QueriesAdapter
import com.github.batulovandrey.unofficialurbandictionary.utils.Utils
import javax.inject.Inject

class MainSearchFragment : Fragment(), SearchView.OnQueryTextListener, MainMvpView {

    @Inject
    lateinit var presenter: MainMvpPresenter<MainMvpView>

    private lateinit var searchView: SearchView
    private lateinit var definitionsRecyclerView: RecyclerView
    private lateinit var userQueriesRecyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var hintTextView: TextView

    private lateinit var query: String

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_search, container, false)
        searchView = view.findViewById(R.id.search_view)
        definitionsRecyclerView = view.findViewById(R.id.definitions_recycler_view)
        userQueriesRecyclerView = view.findViewById(R.id.user_queries_recycler_view)
        progressBar = view.findViewById(R.id.progress_bar)
        hintTextView = view.findViewById(R.id.hint_text_view)
        UrbanDictionaryApp.getNetComponent().inject(this)
        return view
    }

    override fun onStart() {
        super.onStart()

        if (arguments != null && arguments.containsKey(EXTRA_QUERY)) {
            query = arguments.getString(EXTRA_QUERY)
            initializeQueryToServer(query)
            arguments = null
        }

        searchView.clearFocus()
        hideKeyboard()
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        initSearchView()
        presenter.onAttach(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()) {
            presenter.getData(query!!)
            presenter.saveUserQuery(query)
            return true
        }
        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {
        presenter.filterQueries(newText)
        return true
    }

    override fun showToast(resId: Int) {
        Toast.makeText(context, getString(resId), Toast.LENGTH_LONG).show()
    }

    override fun showHint() {
        hintTextView.visibility = View.VISIBLE
        userQueriesRecyclerView.visibility = View.GONE
        definitionsRecyclerView.visibility = View.GONE
    }

    override fun initializeQueryToServer(query: String) {
        onQueryTextSubmit(query)
    }

    // mainMvpView's methods

    override fun showDefinitions() {
        definitionsRecyclerView.visibility = View.VISIBLE
        userQueriesRecyclerView.visibility = View.GONE
        hintTextView.visibility = View.GONE
    }

    override fun showQueries() {
        userQueriesRecyclerView.visibility = View.VISIBLE
        definitionsRecyclerView.visibility = View.GONE
        hintTextView.visibility = View.GONE    }

    override fun setDefinitionAdapter(definitionAdapter: DefinitionAdapter) {
        definitionsRecyclerView.adapter = definitionAdapter
    }

    override fun setQueriesAdapter(queriesAdapter: QueriesAdapter) {
        userQueriesRecyclerView.adapter = queriesAdapter
    }

    override fun closeNavigationDrawer() {}

    override fun showSearchFragment() {}

    override fun showPopularWordsFragment() {}

    override fun showFavoritesFragment() {}

    override fun showDetailFragment() {
        (activity as MainActivity).showDetailFragment()
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    override fun hideKeyboard() {
        Utils.hideKeyboard(searchView, context)
    }

    override fun isNetworkConnected(): Boolean {
        return true
    }

    override fun showSnackbar() {
        Snackbar.make(searchView, getString(R.string.no_internet), Snackbar.LENGTH_LONG).show()
    }

    private fun initSearchView() {
        val searchManager = activity.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity.componentName))
        searchView.isFocusable = true
        searchView.isIconified = false
        searchView.setOnQueryTextListener(this)
    }

    companion object {

        private const val EXTRA_QUERY = "extra_query"

        fun newInstance(query: String): MainSearchFragment {
            val fragment = MainSearchFragment()
            val args = Bundle()
            args.putString(EXTRA_QUERY, query)
            fragment.arguments = args
            return fragment
        }
    }
}