package com.github.batulovandrey.unofficialurbandictionary.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView

import com.github.batulovandrey.unofficialurbandictionary.R
import com.github.batulovandrey.unofficialurbandictionary.presenter.DetailPresenter
import com.github.batulovandrey.unofficialurbandictionary.presenter.DetailPresenterImpl
import kotterknife.bindView
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class DetailFragment : Fragment(), DetailView {

    private var mDefinitionId: Long? = null

    private val mWordTextView: TextView by bindView(R.id.word_text_view)
    private val mDefinitionTextView: TextView by bindView(R.id.definition_text_view)
    private val mAuthorTextView: TextView by bindView(R.id.author_text_view)
    private val mThumbsUpTextView: TextView by bindView(R.id.thumbs_up_text_view)
    private val mThumbsDownTextView: TextView by bindView(R.id.thumbs_down_text_view)
    private val mPermalinkTextView: TextView by bindView(R.id.permalink_text_view)
    private val mFavImageView: ImageView by bindView(R.id.fav_image_view)

    private lateinit var mDetailPresenter: DetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mDefinitionId = arguments.getLong(EXTRA_DEFINITION_ID)
            mDetailPresenter = DetailPresenterImpl(this)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setValuesToViews(mDefinitionId!!)
    }

    override fun setImageResToImageView(resId: Int) {
        mFavImageView.setImageResource(resId)
        val executorService = Executors.newScheduledThreadPool(1)
        val animation: RotateAnimation = if (resId == R.drawable.favorite_white) {
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

    private fun setValuesToViews(definitionId: Long) {
        val definition = mDetailPresenter.getDefinitionByDefId(definitionId)
        mWordTextView.text = definition.word
        mDefinitionTextView.text = definition.definition
        mDefinitionTextView.append("\n\n" + definition.example)
        mAuthorTextView.text = definition.author
        mThumbsUpTextView.text = definition.thumbsUp.toString()
        mThumbsDownTextView.text = definition.thumbsDown.toString()
        mPermalinkTextView.text = definition.permalink
        mFavImageView.setImageResource(if (mDetailPresenter.isFavoriteDefinition(definition))
            R.drawable.favorite_black
        else
            R.drawable.favorite_white)
        mFavImageView.setOnClickListener { mDetailPresenter.isAddedToFav(definitionId) }
    }

    companion object {

        private val EXTRA_DEFINITION_ID = "extra_definition_id"

        fun newInstance(definitionId: Long): DetailFragment {
            val fragment = DetailFragment()
            val args = Bundle()
            args.putLong(EXTRA_DEFINITION_ID, definitionId)
            fragment.arguments = args
            return fragment
        }
    }
}