package com.github.batulovandrey.unofficialurbandictionary.ui.detail

import android.content.Intent
import android.content.Intent.*
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView

import com.github.batulovandrey.unofficialurbandictionary.R
import com.github.batulovandrey.unofficialurbandictionary.UrbanDictionaryApp
import com.github.batulovandrey.unofficialurbandictionary.data.db.model.Definition
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import kotterknife.bindView
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DetailFragment : Fragment(), DetailMvpView {

    private val wordTextView: TextView by bindView(R.id.word_text_view)
    private val definitionTextView: TextView by bindView(R.id.definition_text_view)
    private val authorTextView: TextView by bindView(R.id.author_text_view)
    private val thumbsUpTextView: TextView by bindView(R.id.thumbs_up_text_view)
    private val thumbsDownTextView: TextView by bindView(R.id.thumbs_down_text_view)
    private val permalinkTextView: TextView by bindView(R.id.permalink_text_view)
    private val favImageView: ImageView by bindView(R.id.fav_image_view)
    private val adView: AdView by bindView(R.id.adView)

    @Inject
    lateinit var detailPresenter: DetailPresenter<DetailMvpView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        UrbanDictionaryApp.getNetComponent().inject(this)
        return inflater?.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailPresenter.onAttach(this)
        favImageView.setOnClickListener { clickToFavoriteIcon() }

        val request = AdRequest.Builder()
                .build()
        adView.loadAd(request)
    }

    override fun onDestroy() {
        detailPresenter.onDetach()
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.sharing_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_item_share -> shareDefinition()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun setImage(resId: Int, degrees: Float) {
        favImageView.setImageResource(resId)
        val executorService = Executors.newScheduledThreadPool(1)
        val animation = RotateAnimation(0f,
                degrees,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f)
        animation.interpolator = LinearInterpolator()
        animation.repeatCount = Animation.INFINITE
        animation.duration = 300
        favImageView.startAnimation(animation)
        executorService.schedule({ favImageView.animation = null },
                300,
                TimeUnit.MILLISECONDS)
    }

    override fun clickToFavoriteIcon() {
        detailPresenter.handleClick()
    }

    override fun setValuesToViews(definition: Definition) {
        wordTextView.text = definition.word
        definitionTextView.text = definition.definition
        definitionTextView.append("\n\n" + definition.example)
        authorTextView.text = definition.author
        thumbsUpTextView.text = definition.thumbsUp.toString()
        thumbsDownTextView.text = definition.thumbsDown.toString()
        permalinkTextView.text = definition.permalink

        favImageView.setImageResource(if (definition.favorite == 0)
            R.drawable.favorite_white
        else
            R.drawable.favorite_black)
    }

    private fun shareDefinition() {
        val shareIntent = Intent(ACTION_SEND)
        shareIntent.type = "text/plain"

        shareIntent.putExtra(EXTRA_SUBJECT, wordTextView.text)
        shareIntent.putExtra(EXTRA_TEXT, definitionTextView.text)

        startActivity(Intent.createChooser(shareIntent, getString(R.string.share)))
    }
}