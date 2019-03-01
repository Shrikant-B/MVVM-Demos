package com.shrikantbadwaik.weatherforcast.view.splash

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.shrikantbadwaik.weatherforcast.domain.Constants
import javax.inject.Inject

class SplashViewModel @Inject constructor() : ViewModel() {
    private val event: MutableLiveData<String> = MutableLiveData()

    fun getEvent(): MutableLiveData<String> = event

    fun navigate() {
        event.value = Constants.ActivityCallingState.CALL_MAIN_ACTIVITY.name
    }
}