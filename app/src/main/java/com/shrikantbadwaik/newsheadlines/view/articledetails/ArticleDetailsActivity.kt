package com.shrikantbadwaik.newsheadlines.view.articledetails

import android.arch.lifecycle.Observer
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.shrikantbadwaik.newsheadlines.BR
import com.shrikantbadwaik.newsheadlines.R
import com.shrikantbadwaik.newsheadlines.databinding.ActivityArticleDetailsBinding
import com.shrikantbadwaik.newsheadlines.domain.util.Constants
import org.koin.android.viewmodel.ext.android.viewModel

class ArticleDetailsActivity : AppCompatActivity() {
    private val viewModel: ArticleDetailsViewModel by viewModel()
    private lateinit var activityBinding: ActivityArticleDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBindingAndViewModel()
        setupToolbar()
        clickEventObserver()
        viewModel.showArticleDetails(intent.extras)
    }

    override fun onBackPressed() {
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupBindingAndViewModel() {
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_article_details)
        activityBinding.setVariable(BR.viewModel, viewModel)
        activityBinding.executePendingBindings()
    }

    private fun setupToolbar() {
        setSupportActionBar(activityBinding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        activityBinding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun clickEventObserver() {
        viewModel.getClickEventLiveData().observe(this, Observer {
            when (it) {
                Constants.ClickEventState.OPEN_WEB_BROWSER.name -> {
                    startActivity(Intent(Intent.ACTION_VIEW, viewModel.getWebUrl()))
                }
            }
        })
    }
}