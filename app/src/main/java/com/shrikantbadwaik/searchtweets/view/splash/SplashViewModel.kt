package com.shrikantbadwaik.searchtweets.view.splash

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.shrikantbadwaik.searchtweets.domain.util.Constants
import javax.inject.Inject

class SplashViewModel @Inject constructor() : ViewModel() {
    private val event: MutableLiveData<String> = MutableLiveData()

    fun getEvent(): MutableLiveData<String> = event

    fun navigate() {
        event.value = Constants.ActivityCallingState.CALL_SEARCH_TWEET_ACTIVITY.name
    }
}