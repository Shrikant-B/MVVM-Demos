package com.shrikantbadwaik.searchtweets.view.splash

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.shrikantbadwaik.searchtweets.BR
import com.shrikantbadwaik.searchtweets.R
import com.shrikantbadwaik.searchtweets.databinding.ActivitySplashBinding
import com.shrikantbadwaik.searchtweets.domain.util.Constants
import com.shrikantbadwaik.searchtweets.view.MainActivity
import dagger.android.AndroidInjection
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private lateinit var viewModel: SplashViewModel
    private lateinit var activityBinding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setupBindingAndViewModel()

        Thread(Runnable {
            try {
                Thread.sleep(Constants.DURATION_4000)
                runOnUiThread { viewModel.navigate() }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }).start()

        viewModel.getEvent().observe(this, Observer {
            when (it) {
                Constants.ActivityCallingState.CALL_SEARCH_TWEET_ACTIVITY.name -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        })
    }

    private fun setupBindingAndViewModel() {
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        viewModel = ViewModelProviders.of(this, factory).get(SplashViewModel::class.java)
        activityBinding.setVariable(BR.viewModel, viewModel)
        activityBinding.executePendingBindings()
    }
}