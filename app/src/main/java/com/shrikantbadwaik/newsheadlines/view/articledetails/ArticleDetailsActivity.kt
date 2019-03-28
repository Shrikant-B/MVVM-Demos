package com.shrikantbadwaik.newsheadlines.view.articledetails

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.shrikantbadwaik.newsheadlines.BR
import com.shrikantbadwaik.newsheadlines.R
import com.shrikantbadwaik.newsheadlines.databinding.ActivityArticleDetailsBinding
import com.shrikantbadwaik.newsheadlines.domain.util.Constants
import dagger.android.AndroidInjection
import javax.inject.Inject

class ArticleDetailsActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private lateinit var viewModel: ArticleDetailsViewModel
    private lateinit var activityBinding: ActivityArticleDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
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
        viewModel = ViewModelProviders.of(this, factory).get(ArticleDetailsViewModel::class.java)
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