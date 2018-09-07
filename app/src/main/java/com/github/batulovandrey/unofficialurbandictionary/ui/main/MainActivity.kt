package com.github.batulovandrey.unofficialurbandictionary.ui.main

import android.content.Context
import android.media.AudioManager
import android.media.AudioManager.*
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.github.batulovandrey.unofficialurbandictionary.BuildConfig
import com.github.batulovandrey.unofficialurbandictionary.R
import com.github.batulovandrey.unofficialurbandictionary.ui.cached.CachedFragment
import com.github.batulovandrey.unofficialurbandictionary.ui.detail.DetailFragment
import com.github.batulovandrey.unofficialurbandictionary.ui.favorites.FavoritesFragment
import com.github.batulovandrey.unofficialurbandictionary.ui.top.TopWordsFragment
import com.github.batulovandrey.unofficialurbandictionary.utils.Utils
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import kotterknife.bindView
import java.util.concurrent.atomic.AtomicInteger

/**
 * @author Andrey Batulov on 22/12/2017
 */

val ADS_COUNT = AtomicInteger(1)

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val drawerLayout: DrawerLayout by bindView(R.id.drawer_layout)
    private val navigationView: NavigationView by bindView(R.id.navigation_view)
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var interstitial: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.UrbanTheme)
        super.onCreate(savedInstanceState)

        MobileAds.initialize(this, BuildConfig.AD_MOB_ID)
        loadAd()

        setContentView(R.layout.activity_main)
        initIU()
        showFragment(MainSearchFragment())

        supportFragmentManager.addOnBackStackChangedListener { checkFragmentFromBackStack() }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            Utils.hideKeyboard(navigationView, this)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Utils.hideKeyboard(navigationView, this)
        val handler = Handler()
        handler.postDelayed({ drawerLayout.closeDrawer(GravityCompat.START) }, 100)

        return when (item.itemId) {
            R.id.search_item -> {
                val currentFragment = supportFragmentManager.findFragmentById(R.id.frame_layout)
                if (currentFragment !is MainSearchFragment)
                    handler.post { showFragment(MainSearchFragment()) }
                true
            }
            R.id.favorites_item -> {
                val currentFragment = supportFragmentManager.findFragmentById(R.id.frame_layout)
                if (currentFragment !is FavoritesFragment)
                    handler.post { showFragment(FavoritesFragment()) }
                return true
            }
            R.id.popular_item -> {
                val currentFragment = supportFragmentManager.findFragmentById(R.id.frame_layout)
                if (currentFragment !is TopWordsFragment)
                    handler.post { showFragment(TopWordsFragment()) }
                return true
            }
            R.id.cached_item -> {
                val currentFragment = supportFragmentManager.findFragmentById(R.id.frame_layout)
                if (currentFragment !is CachedFragment)
                    handler.post { showFragment(CachedFragment()) }
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        val manager = supportFragmentManager
        val currentFragment = manager.findFragmentById(R.id.frame_layout)

        when {
            drawerLayout.isDrawerOpen(GravityCompat.START) ->
                drawerLayout.closeDrawer(GravityCompat.START)

            currentFragment is MainSearchFragment -> {
                showAlertDialog()
            }

            currentFragment is DetailFragment -> supportFragmentManager.popBackStack()

            else -> supportFragmentManager.popBackStack(null, POP_BACK_STACK_INCLUSIVE)
        }
    }

    fun redirectToSearchFragment(word: String) {
        showFragment(MainSearchFragment.newInstance(word))
    }

    fun showDetailFragment() {
        showFragment(DetailFragment())

        if (ADS_COUNT.get() % 5 == 0) {

            if (interstitial.isLoaded) {
                interstitial.show()
            } else {
                loadAd()
                ADS_COUNT.decrementAndGet()
            }
        }
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

    private fun checkFragmentFromBackStack() {
        val fragment = supportFragmentManager.findFragmentById(R.id.frame_layout)
        if (fragment is FavoritesFragment || fragment is CachedFragment) {
            fragment.onResume()
        }
    }

    private fun showFragment(fragment: Fragment) {
        val isNeedReplace = fragment is MainSearchFragment

        val manager = supportFragmentManager
        val currentFragment = manager.findFragmentById(R.id.frame_layout)
        val transaction = manager.beginTransaction()

        if (isNeedReplace) {
            transaction.replace(R.id.frame_layout, fragment)
        } else {
            transaction.addToBackStack(null)
            transaction.hide(currentFragment)
            transaction.add(R.id.frame_layout, fragment)
        }

        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction.commit()
    }

    private fun loadAd() {
        val request = AdRequest.Builder()
                .build()

        interstitial = InterstitialAd(this).apply {
            adUnitId = BuildConfig.AD_MOB_UNIT_ID
            adListener = object : AdListener() {
                override fun onAdOpened() {
                    muteSound()
                }

                override fun onAdClosed() {
                    unmuteSound()
                }
            }
            loadAd(request)
        }
    }

    private fun muteSound() {
        val manager = applicationContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            manager.adjustStreamVolume(STREAM_MUSIC, ADJUST_MUTE, 0)
        } else {
            manager.setStreamMute(STREAM_MUSIC, true)
        }
    }

    private fun unmuteSound() {
        val manager = applicationContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            manager.adjustStreamVolume(STREAM_MUSIC, ADJUST_UNMUTE, 0)
        } else {
            manager.setStreamMute(AudioManager.STREAM_MUSIC, false)
        }
    }

    private fun showAlertDialog() {
        AlertDialog.Builder(this)
                .setTitle(R.string.exit)
                .setMessage(R.string.are_you_sure)
                .setPositiveButton(R.string.yes, { _, _ -> finish() })
                .setNegativeButton(R.string.no, { dialogInterface, _ -> dialogInterface.dismiss() })
                .show()
    }
}