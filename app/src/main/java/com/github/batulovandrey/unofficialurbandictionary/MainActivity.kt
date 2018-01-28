package com.github.batulovandrey.unofficialurbandictionary

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.github.batulovandrey.unofficialurbandictionary.view.SearchFragment
import kotterknife.bindView

/**
 * @author Andrey Batulov on 22/12/2017
 */

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search_item -> {
                mDrawerLayout.closeDrawer(GravityCompat.START)
                showSearchFragment()
                true
            }
            R.id.favorites_item -> {
                startActivity(Intent(this, FavoritesActivity::class.java))
                return true
            }
            R.id.popular_item -> {
                startActivity(Intent(this, PopularWordsActivity::class.java))
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
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
}