package com.github.batulovandrey.unofficialurbandictionary

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.github.batulovandrey.unofficialurbandictionary.utils.Utils
import com.github.batulovandrey.unofficialurbandictionary.view.*
import kotterknife.bindView

/**
 * @author Andrey Batulov on 22/12/2017
 */

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
        AlphabetFragment.OnLetterClickListener, WordsFragment.OnWordClickListener {

    private val mDrawerLayout: DrawerLayout by bindView(R.id.drawer_layout)
    private val mNavigationView: NavigationView by bindView(R.id.navigation_view)
    private lateinit var mToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initIU()
        showSearchFragment()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (mToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        mDrawerLayout.closeDrawer(GravityCompat.START)
        Utils.hideKeyboard(mNavigationView, this)
        return when (item.itemId) {
            R.id.search_item -> {
                showSearchFragment()
                true
            }
            R.id.favorites_item -> {
                showFavoritesFragment()
                return true
            }
            R.id.popular_item -> {
                showPopularWordsFragment()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onWordClick(word: String) {
        showSearchFragment(word)
    }

    override fun onLetterClick(letter: String) {
        val manager = supportFragmentManager
        val popularWordsFragment = manager.findFragmentById(R.id.frame_layout) as PopularWordsFragment
        popularWordsFragment.showWordsByLetter(letter)
    }

    private fun initIU() {
        initToolbar()
        mToggle = ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close)
        mDrawerLayout.addDrawerListener(mToggle)
        mNavigationView.setNavigationItemSelectedListener(this)
        mToggle.syncState()
    }

    private fun initToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun showSearchFragment() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val searchFragment = SearchFragment()
        fragmentTransaction.replace(R.id.frame_layout, searchFragment)
        fragmentTransaction.commit()
    }

    private fun showSearchFragment(word: String) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val searchFragment = SearchFragment.newInstance(word)
        fragmentTransaction.replace(R.id.frame_layout, searchFragment)
        fragmentTransaction.commit()
    }

    private fun showFavoritesFragment() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val favoritesFragment = FavoritesFragment()
        fragmentTransaction.replace(R.id.frame_layout, favoritesFragment)
        fragmentTransaction.commit()
    }

    private fun showPopularWordsFragment() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val popularWordsFragment = PopularWordsFragment()
        fragmentTransaction.replace(R.id.frame_layout, popularWordsFragment)
        fragmentTransaction.commit()
    }
}