package com.shrikantbadwaik.searchtweets.view.searchtweets

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.View
import android.widget.ImageView
import com.shrikantbadwaik.searchtweets.BR
import com.shrikantbadwaik.searchtweets.R
import com.shrikantbadwaik.searchtweets.databinding.ActivitySearchTweetsBinding
import com.shrikantbadwaik.searchtweets.domain.model.Tweet
import com.shrikantbadwaik.searchtweets.domain.util.Constants
import com.shrikantbadwaik.searchtweets.domain.util.DialogUtil
import dagger.android.AndroidInjection
import javax.inject.Inject

class SearchTweetsActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    @Inject
    lateinit var adapter: SearchTweetsRecyclerAdapter

    private lateinit var viewModel: SearchTweetsViewModel
    private lateinit var activityBinding: ActivitySearchTweetsBinding
    private lateinit var clearButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setupBindingAndViewModel()
        setupSearchView()
        setupRecyclerView()
        dialogStateObserver()
        apiResultObserver()
        apiErrorObserver()
        mostRecentTweetsObserver()
    }

    private fun setupBindingAndViewModel() {
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_search_tweets)
        viewModel = ViewModelProviders.of(this, factory).get(SearchTweetsViewModel::class.java)
        activityBinding.setVariable(BR.viewModel, viewModel)
        activityBinding.executePendingBindings()
    }

    private fun setupSearchView() {
        clearButton = activityBinding.searchView.findViewById(R.id.search_close_btn)
        activityBinding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.clear()
                clearButton.visibility = View.GONE
                viewModel.getMostRecentTweets(query)
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return false
            }
        })
    }

    private fun setupRecyclerView() {
        activityBinding.recyclerView.adapter = adapter
        activityBinding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun dialogStateObserver() {
        viewModel.getDialogStateLiveData().observe(this, Observer {
            when (it) {
                Constants.DialogState.QUERY_EMPTY_ERROR_DIALOG.name -> {
                    DialogUtil.showErrorDialog(this, getString(R.string.txt_text_empty), null)
                }
                Constants.DialogState.DEVICE_OFFLINE_DIALOG.name -> {
                    DialogUtil.showErrorDialog(this, getString(R.string.txt_no_internet_connection), null)
                }
            }
        })
    }

    private fun apiResultObserver() {
        viewModel.getApiResultLiveData().observe(this, Observer {
            when (it) {
                Constants.ApiResult.ON_SUCCESS.name -> {
                    activityBinding.recyclerView.visibility = View.VISIBLE
                    activityBinding.textView01.visibility = View.GONE
                    clearButton.visibility = View.VISIBLE
                }
                Constants.ApiResult.ON_FAILURE.name -> {
                    activityBinding.recyclerView.visibility = View.GONE
                    activityBinding.textView01.visibility = View.VISIBLE
                    clearButton.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun apiErrorObserver() {
        viewModel.getApiErrorLiveData().observe(this, Observer {
            DialogUtil.showErrorDialog(this, it, null)
        })
    }

    private fun mostRecentTweetsObserver() {
        viewModel.getTweetsLiveData().observe(this, Observer<ArrayList<Tweet>> {
            viewModel.setTweetsObservable(it)
        })
    }
}