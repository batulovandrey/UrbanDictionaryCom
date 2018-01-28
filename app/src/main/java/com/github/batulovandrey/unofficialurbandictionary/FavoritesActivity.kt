package com.github.batulovandrey.unofficialurbandictionary

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.github.batulovandrey.unofficialurbandictionary.adapter.DefinitionClickListener
import com.github.batulovandrey.unofficialurbandictionary.presenter.FavoritesPresenter
import com.github.batulovandrey.unofficialurbandictionary.presenter.FavoritesPresenterImpl
import com.github.batulovandrey.unofficialurbandictionary.utils.Constants.EXTRA_DEFINITION_ID
import com.github.batulovandrey.unofficialurbandictionary.view.FavoritesView
import kotterknife.bindView

/**
 * @author Andrey Batulov on 22/12/2017
 */


class FavoritesActivity : AppCompatActivity(), DefinitionClickListener, FavoritesView {

    private val mToolbar: Toolbar by bindView(R.id.toolbar)
    private val mRecyclerView: RecyclerView by bindView(R.id.definitions_recycler_view)
    private val mEmptyFavTextView: TextView by bindView(R.id.empty_fav_text_view)

    lateinit var mFavoritesPresenter: FavoritesPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
        mFavoritesPresenter = FavoritesPresenterImpl(this)
        initToolbar()
        mRecyclerView.adapter = mFavoritesPresenter.definitionAdapter
    }

    override fun onResume() {
        super.onResume()
        mRecyclerView.adapter = mFavoritesPresenter.definitionAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.favorites_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.clear_favorites -> {
                mFavoritesPresenter.showAlertDialog()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onItemClick(position: Int) {
        val definition = mFavoritesPresenter.favorites[position]
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(EXTRA_DEFINITION_ID, definition.defid)
        startActivity(intent)
    }

    override fun showAlertDialog() {
        AlertDialog.Builder(this)
                .setTitle("Clear list of favorites")
                .setMessage("All items from favorite list will be removed. Are you sure?")
                .setPositiveButton("yes") { _, _ ->
                    mFavoritesPresenter.clearList()
                    mRecyclerView.adapter = mFavoritesPresenter.definitionAdapter
                }.show()
    }

    override fun showToast(resId: Int) {
        Toast.makeText(this, resId, Toast.LENGTH_LONG).show()
    }

    override fun hideRecycler() {
        mRecyclerView.visibility = View.GONE
        mEmptyFavTextView.visibility = View.VISIBLE
    }

    override fun showRecycler() {
        mRecyclerView.visibility = View.VISIBLE
        mEmptyFavTextView.visibility = View.GONE
    }

    private fun initToolbar() {
        setSupportActionBar(mToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }
}