package com.github.batulovandrey.unofficialurbandictionary.ui

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.AudioManager.*
import android.net.Uri
import android.os.Build
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
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import com.crashlytics.android.Crashlytics
import com.github.batulovandrey.unofficialurbandictionary.BuildConfig
import com.github.batulovandrey.unofficialurbandictionary.R
import com.github.batulovandrey.unofficialurbandictionary.ui.cached.CachedFragment
import com.github.batulovandrey.unofficialurbandictionary.ui.detail.DetailFragment
import com.github.batulovandrey.unofficialurbandictionary.ui.favorites.FavoritesFragment
import com.github.batulovandrey.unofficialurbandictionary.ui.main.MainSearchFragment
import com.github.batulovandrey.unofficialurbandictionary.ui.top.TopWordsFragment
import com.github.batulovandrey.unofficialurbandictionary.utils.ThemesManager
import com.github.batulovandrey.unofficialurbandictionary.utils.Utils
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import io.fabric.sdk.android.Fabric
import kotterknife.bindView
import org.codechimp.apprater.AppRater
import java.util.concurrent.atomic.AtomicInteger

/**
 * @author Andrey Batulov on 22/12/2017
 */

val ADS_COUNT = AtomicInteger(1)

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
        PolicyDialogFragment.OnClickListener {

    private val drawerLayout: DrawerLayout by bindView(R.id.drawer_layout)
    private val navigationView: NavigationView by bindView(R.id.navigation_view)

    private var isAcceptedPrivacyPolicy = false
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var interstitial: InterstitialAd
    private lateinit var rateIV: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        ThemesManager(this).setTheme()
        super.onCreate(savedInstanceState)

        MobileAds.initialize(this, BuildConfig.AD_MOB_ID)

        setContentView(R.layout.activity_main)
        initIU()

        val fragment = supportFragmentManager.findFragmentById(R.id.container)
        if (fragment == null) {
            showFragment(MainSearchFragment())
        }

        val isUserChoice = getSharedPreferences(packageName, Context.MODE_PRIVATE).getBoolean(IS_USER_CHOICE, false)

        if (!isUserChoice) {
            PolicyDialogFragment().show(supportFragmentManager, "policy")
            findViewById<TextView>(android.R.id.message)?.movementMethod = LinkMovementMethod.getInstance()
        }

        isAcceptedPrivacyPolicy = getSharedPreferences(packageName, Context.MODE_PRIVATE).getBoolean(PRIVACY_POLICY_ACCEPTED, false)

        if (isAcceptedPrivacyPolicy) {
            initStatistics()
        }

        loadAd()

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
                clearBackStack()
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
            R.id.random_item -> {
                handler.post { showFragment(MainSearchFragment.newInstance("")) }
                return true
            }
            R.id.settings_item -> {
                val fragment = SettingsDialogFragment()
                fragment.show(supportFragmentManager, getString(R.string.settings))
                return true
            }
            R.id.policy_privacy_item -> {
                openPrivacyPolicy()
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

            else -> {
                clearBackStack()
            }
        }
    }

    private fun clearBackStack() {
        val count = supportFragmentManager.backStackEntryCount

        for (i in 0 until count) {
            supportFragmentManager.popBackStack()
        }
    }

    fun redirectToSearchFragment(word: String) {
        showFragment(MainSearchFragment.newInstance(word))
    }

    fun showDetailFragment() {
        showFragment(DetailFragment())
    }

    fun openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START)
    }

    override fun onUserChoice(isAccepted: Boolean) {
        isAcceptedPrivacyPolicy = isAccepted
        Log.d("loadAd isAccepted", "isAccepted = $isAcceptedPrivacyPolicy")
        if (isAcceptedPrivacyPolicy) {
            initStatistics()
        }
    }

    private fun initIU() {
        initToolbar()
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        navigationView.setNavigationItemSelectedListener(this)
        toggle.syncState()
        rateIV = navigationView.getHeaderView(0).findViewById(R.id.rate_iv)
        rateIV.setOnClickListener {
            AppRater.showRateDialog(this)
        }
    }

    private fun initToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initStatistics() {
        Fabric.with(this, Crashlytics())
        AppRater.app_launched(this)
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
        val transaction = manager.beginTransaction()

        if (isNeedReplace) {
            clearBackStack()
            transaction.replace(R.id.frame_layout, fragment)
        } else {
            transaction.addToBackStack(null)
            transaction.replace(R.id.frame_layout, fragment)
        }

        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction.commit()

        if (ADS_COUNT.get() % 4 != 0) {
            return
        }

        if (interstitial.isLoaded) {
            interstitial.show()
        } else {
            loadAd()
            ADS_COUNT.decrementAndGet()
        }
    }

    private fun loadAd() {
        val request =
                if (isAcceptedPrivacyPolicy) {
                    AdRequest.Builder()
                            .addTestDevice("4B2B6D802FD90E79BA0E4ED30CE2832C")
                            .build()
                } else {
                    val extras = Bundle()
                    extras.putString("npa", "1")
                    AdRequest.Builder()
                            .addNetworkExtrasBundle(AdMobAdapter::class.java, extras)
                            .build()
                }

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

    private fun openPrivacyPolicy() {
        val url = getString(R.string.policy_link)
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }
}