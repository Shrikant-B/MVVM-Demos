package com.shrikantbadwaik.newsheadlines.view.splash

import android.arch.lifecycle.Observer
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.shrikantbadwaik.newsheadlines.BR
import com.shrikantbadwaik.newsheadlines.R
import com.shrikantbadwaik.newsheadlines.databinding.ActivitySplashBinding
import com.shrikantbadwaik.newsheadlines.domain.util.Constants
import com.shrikantbadwaik.newsheadlines.view.topheadline.TopHeadlineActivity
import org.koin.android.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {
    private val splashViewModel: SplashViewModel by viewModel()
    private lateinit var activityBinding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBindingAndViewModel()

        Thread(Runnable {
            try {
                Thread.sleep(Constants.DURATION_4000)
                runOnUiThread { splashViewModel.navigate() }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }).start()

        splashViewModel.getEvent().observe(this, Observer {
            when (it) {
                Constants.ActivityCallingState.CALL_TOP_HEADLINE_ACTIVITY.name -> {
                    startActivity(Intent(this, TopHeadlineActivity::class.java))
                    finish()
                }
            }
        })
    }

    private fun setupBindingAndViewModel() {
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        activityBinding.setVariable(BR.viewModel, splashViewModel)
        activityBinding.executePendingBindings()
    }
}