package com.github.batulovandrey.unofficialurbandictionary

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import com.github.batulovandrey.unofficialurbandictionary.presenter.DetailPresenter
import com.github.batulovandrey.unofficialurbandictionary.presenter.DetailPresenterImpl
import com.github.batulovandrey.unofficialurbandictionary.utils.Constants.EXTRA_DEFINITION_ID
import com.github.batulovandrey.unofficialurbandictionary.view.DetailView
import kotterknife.bindView
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * @author Andrey Batulov on 22/12/2017
 */

class DetailActivity : AppCompatActivity(), DetailView {

    private val mToolbar: Toolbar by bindView(R.id.toolbar)
    private val mWordTextView: TextView by bindView(R.id.word_text_view)
    private val mDefinitionTextView: TextView by bindView(R.id.definition_text_view)
    private val mExampleTextView: TextView by bindView(R.id.example_text_view)
    private val mAuthorTextView: TextView by bindView(R.id.author_text_view)
    private val mThumbsUpTextView: TextView by bindView(R.id.thumbs_up_text_view)
    private val mThumbsDownTextView: TextView by bindView(R.id.thumbs_down_text_view)
    private val mPermalinkTextView: TextView by bindView(R.id.permalink_text_view)
    private val mFavImageView: ImageView by bindView(R.id.fav_image_view)

    private var mDetailPresenter: DetailPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        mDetailPresenter = DetailPresenterImpl(this)
        initToolbar()
        val definitionId = intent.getLongExtra(EXTRA_DEFINITION_ID, 0)
        setValuesToViews(definitionId)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return false
    }

    private fun initToolbar() {
        setSupportActionBar(mToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun setValuesToViews(definitionId: Long) {
        val definition = mDetailPresenter!!.getDefinitionByDefId(definitionId)
        mWordTextView.text = definition.word
        supportActionBar!!.title = "Definition of " + definition.word
        mDefinitionTextView.text = definition.definition
        mExampleTextView.text = definition.example
        mAuthorTextView.text = definition.author
        mThumbsUpTextView.text = definition.thumbsUp.toString()
        mThumbsDownTextView.text = definition.thumbsDown.toString()
        mPermalinkTextView.text = definition.permalink
        mFavImageView.setImageResource(if (mDetailPresenter!!
                .isFavoriteDefinition(definition))
            R.drawable.unfav
        else
            R.drawable.fav)
        mFavImageView.setOnClickListener { mDetailPresenter!!.isAddedToFav(definitionId) }
    }

    override fun setImageResToImageView(resId: Int) {
        mFavImageView.setImageResource(resId)
        val executorService = Executors.newScheduledThreadPool(1)
        val animation: RotateAnimation = if (resId == R.drawable.fav) {
            RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        } else {
            RotateAnimation(0f, -360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        }
        animation.interpolator = LinearInterpolator()
        animation.repeatCount = Animation.INFINITE
        animation.duration = 300
        mFavImageView.startAnimation(animation)
        executorService.schedule({ mFavImageView.animation = null }, 300, TimeUnit.MILLISECONDS)
    }
}