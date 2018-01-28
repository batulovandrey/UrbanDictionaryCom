package com.github.batulovandrey.unofficialurbandictionary.view

import android.app.SearchManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.ProgressBar
import android.widget.Toast

import com.github.batulovandrey.unofficialurbandictionary.R
import com.github.batulovandrey.unofficialurbandictionary.adapter.DefinitionAdapter
import com.github.batulovandrey.unofficialurbandictionary.adapter.DefinitionClickListener
import com.github.batulovandrey.unofficialurbandictionary.adapter.QueriesAdapter
import com.github.batulovandrey.unofficialurbandictionary.presenter.MainPresenter
import com.github.batulovandrey.unofficialurbandictionary.presenter.MainPresenterImpl
import com.github.batulovandrey.unofficialurbandictionary.utils.Utils
import kotterknife.bindView

class SearchFragment : Fragment(), SearchView.OnQueryTextListener, MainView, DefinitionClickListener {

    private val mSearchView: SearchView by bindView(R.id.search_view)
    private val mDefinitionsRecyclerView: RecyclerView by bindView(R.id.definitions_recycler_view)
    private val mUserQueriesRecyclerView: RecyclerView by bindView(R.id.user_queries_recycler_view)
    private val mProgressBar: ProgressBar by bindView(R.id.progress_bar)

    private var mListenerSearch: OnSearchFragmentInteractionListener? = null
    private var mMainPresenter: MainPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMainPresenter = MainPresenterImpl(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_search, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnSearchFragmentInteractionListener) {
            mListenerSearch = context
        } else {
//            throw RuntimeException(context!!.toString() + " must implement OnSearchFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListenerSearch = null
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        initSearchView()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null && query.isNotEmpty()) {
            mMainPresenter?.getData(query, this)
            mMainPresenter?.saveQueryToRealm(query)
            showDataInRecycler()
            Utils.hideKeyboard(mUserQueriesRecyclerView, context)
            return true
        }
        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {
        return mMainPresenter!!.textChanged(newText)
    }

    override fun showDataInRecycler() {
        mDefinitionsRecyclerView.visibility = View.VISIBLE
        mUserQueriesRecyclerView.visibility = View.GONE
    }

    override fun showQueriesInListView() {
        mUserQueriesRecyclerView.visibility = View.VISIBLE
        mDefinitionsRecyclerView.visibility = View.GONE
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

    override fun onItemClick(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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

    interface OnSearchFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onSearchFragmentInteraction(uri: Uri)
    }

    companion object {

        fun newInstance(): SearchFragment = SearchFragment()
    }
}