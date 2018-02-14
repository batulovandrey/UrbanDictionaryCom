package com.github.batulovandrey.unofficialurbandictionary

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
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
        showFragment(SearchFragment())
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
                showFragment(SearchFragment())
                true
            }
            R.id.favorites_item -> {
                showFragment(FavoritesFragment())
                return true
            }
            R.id.popular_item -> {
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
        showFragment(SearchFragment.newInstance(word))
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

    private fun showFragment(fragment: Fragment) {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.frame_layout, fragment)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction.commit()
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