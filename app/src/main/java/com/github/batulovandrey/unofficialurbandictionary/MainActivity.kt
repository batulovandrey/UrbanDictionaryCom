package com.github.batulovandrey.unofficialurbandictionary

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.Toast
import com.github.batulovandrey.unofficialurbandictionary.adapter.DefinitionAdapter
import com.github.batulovandrey.unofficialurbandictionary.adapter.DefinitionClickListener
import com.github.batulovandrey.unofficialurbandictionary.adapter.UserQueriesAdapter
import com.github.batulovandrey.unofficialurbandictionary.presenter.MainPresenter
import com.github.batulovandrey.unofficialurbandictionary.presenter.MainPresenterImpl
import com.github.batulovandrey.unofficialurbandictionary.utils.Constants.EXTRA_DEFINITION_ID
import com.github.batulovandrey.unofficialurbandictionary.utils.Constants.EXTRA_SEARCH_QUERY
import com.github.batulovandrey.unofficialurbandictionary.utils.Utils
import com.github.batulovandrey.unofficialurbandictionary.view.MainView
import kotterknife.bindView

/**
 * @author Andrey Batulov on 22/12/2017
 */

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener, DefinitionClickListener, MainView {

    private val mToolbar: Toolbar by bindView(R.id.toolbar)
    private val mSearchView: SearchView by bindView(R.id.search_view)
    private val mListView: ListView by bindView(R.id.list_view)
    private val mRecyclerView: RecyclerView by bindView(R.id.recycler_view)
    private val mProgressBar: ProgressBar by bindView(R.id.progress_bar)

    private var mUserQueriesAdapter: UserQueriesAdapter? = null
    private var mSearchQuery: String? = null
    private var mMainPresenter: MainPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mUserQueriesAdapter = UserQueriesAdapter(this)
        initIU()
        mMainPresenter = MainPresenterImpl(this)
    }

    override fun onResume() {
        super.onResume()
        mSearchView.clearFocus()
        Utils.hideKeyboard(mSearchView, this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.go_to_alphabet -> {
                startActivity(Intent(this, PopularWordsActivity::class.java))
                true
            }
            R.id.go_to_favorites -> {
                startActivity(Intent(this, FavoritesActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        if (query.isNotEmpty()) {
            mMainPresenter!!.getData(query, this)
            mUserQueriesAdapter!!.saveQueryToRealm(query)
            showDataInRecycler()
            Utils.hideKeyboard(mSearchView, this)
            return true
        }
        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {
        return mMainPresenter!!.textChanged(newText)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        if (intent.extras != null) {
            mSearchQuery = intent.extras!!.getString(EXTRA_SEARCH_QUERY)
        }
        mSearchView.post {
            if (mSearchQuery != null) {
                mSearchView.setQuery(mSearchQuery, true)
            }
        }
    }

    override fun onItemClick(position: Int) {
        val definitionId = mMainPresenter!!.getDefinitionId(position)
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(EXTRA_DEFINITION_ID, definitionId)
        startActivity(intent)
    }

    override fun showDataInRecycler() {
        mRecyclerView.visibility = View.VISIBLE
        mListView.visibility = View.GONE
    }

    override fun showQueriesInListView() {
        mListView.visibility = View.VISIBLE
        mRecyclerView.visibility = View.GONE
    }

    override fun filterText(text: String) {
        mUserQueriesAdapter!!.filter(text)
    }

    override fun setAdapterToRecycler(definitionAdapter: DefinitionAdapter) {
        mRecyclerView.adapter = definitionAdapter
    }

    override fun showToast(resId: Int) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_LONG).show()
    }

    override fun showProgressbar() {
        mProgressBar.visibility = View.VISIBLE
    }

    override fun hideProgressbar() {
        mProgressBar.visibility = View.GONE
    }

    private fun initIU() {
        initToolbar()
        initSearchView()
        mListView.adapter = mUserQueriesAdapter
    }

    private fun initToolbar() {
        setSupportActionBar(mToolbar)
    }

    private fun initSearchView() {
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        mSearchView.isFocusable = true
        mSearchView.isIconified = false
        mSearchView.setOnQueryTextListener(this)
    }
}