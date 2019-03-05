package com.shrikantbadwaik.searchtweets.view.searchtweets

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.shrikantbadwaik.searchtweets.BR
import com.shrikantbadwaik.searchtweets.R
import com.shrikantbadwaik.searchtweets.databinding.ActivitySearchTweetsBinding
import dagger.android.AndroidInjection
import javax.inject.Inject

class SearchTweetsActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private lateinit var viewModel: SearchTweetsViewModel
    private lateinit var activityBinding: ActivitySearchTweetsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setupBindingAndViewModel()
    }

    private fun setupBindingAndViewModel() {
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_search_tweets)
        viewModel = ViewModelProviders.of(this, factory).get(SearchTweetsViewModel::class.java)
        activityBinding.setVariable(BR.viewModel, viewModel)
        activityBinding.executePendingBindings()
    }
}