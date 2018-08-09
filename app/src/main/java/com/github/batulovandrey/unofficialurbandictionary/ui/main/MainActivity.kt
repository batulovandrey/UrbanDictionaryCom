package com.github.batulovandrey.unofficialurbandictionary.ui.main

import android.os.Bundle
import android.os.Handler
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.github.batulovandrey.unofficialurbandictionary.R
import com.github.batulovandrey.unofficialurbandictionary.ui.detail.DetailFragment
import com.github.batulovandrey.unofficialurbandictionary.utils.Utils
import com.github.batulovandrey.unofficialurbandictionary.view.AlphabetFragment
import com.github.batulovandrey.unofficialurbandictionary.view.FavoritesFragment
import com.github.batulovandrey.unofficialurbandictionary.view.PopularWordsFragment
import com.github.batulovandrey.unofficialurbandictionary.view.WordsFragment
import kotterknife.bindView

/**
 * @author Andrey Batulov on 22/12/2017
 */

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
        AlphabetFragment.OnLetterClickListener, WordsFragment.OnWordClickListener {

    private val drawerLayout: DrawerLayout by bindView(R.id.drawer_layout)
    private val navigationView: NavigationView by bindView(R.id.navigation_view)
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initIU()
        showFragment(MainSearchFragment())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Utils.hideKeyboard(navigationView, this)
        Handler().postDelayed({ drawerLayout.closeDrawer(GravityCompat.START) }, 100)

        return when (item.itemId) {
            R.id.search_item -> {
                val currentFragment = supportFragmentManager.findFragmentById(R.id.frame_layout)
                if (currentFragment !is MainSearchFragment)
                    showFragment(MainSearchFragment())
                true
            }
            R.id.favorites_item -> {
                val currentFragment = supportFragmentManager.findFragmentById(R.id.frame_layout)
                if (currentFragment !is FavoritesFragment)
                    showFragment(FavoritesFragment())
                return true
            }
            R.id.popular_item -> {
                val currentFragment = supportFragmentManager.findFragmentById(R.id.frame_layout)
                if (currentFragment !is PopularWordsFragment)
                    showFragment(PopularWordsFragment())
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        val manager = supportFragmentManager
        val currentFragment = manager.findFragmentById(R.id.frame_layout)
        if (currentFragment is DetailFragment) {
            supportFragmentManager.popBackStack()
        } else {
            showAlertDialog()
        }
    }

    override fun onWordClick(word: String) {
        showFragment(MainSearchFragment.newInstance(word))
    }

    override fun onLetterClick(letter: String) {
        val manager = supportFragmentManager
        val popularWordsFragment = manager.findFragmentById(R.id.frame_layout) as PopularWordsFragment
        popularWordsFragment.showWordsByLetter(letter)
    }

    fun showDetailFragment() {
        showFragment(DetailFragment())
    }

    private fun initIU() {
        initToolbar()
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        navigationView.setNavigationItemSelectedListener(this)
        toggle.syncState()
    }

    private fun initToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun showFragment(fragment: Fragment) {
        val handler = Handler()
        handler.post({
            val manager = supportFragmentManager
            val transaction = manager.beginTransaction()
            transaction.replace(R.id.frame_layout, fragment)
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            transaction.addToBackStack(null)
            transaction.commit()
        })
    }

    private fun showAlertDialog() {
        AlertDialog.Builder(this)
                .setTitle(R.string.exit)
                .setMessage(R.string.are_you_sure)
                .setPositiveButton(R.string.yeap, { _, _ -> finish() })
                .setNegativeButton(R.string.nope, { dialogInterface, _ -> dialogInterface.dismiss() })
                .show()
    }
}