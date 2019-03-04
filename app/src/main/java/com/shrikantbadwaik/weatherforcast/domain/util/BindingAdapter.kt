package com.shrikantbadwaik.weatherforcast.domain.util

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import com.shrikantbadwaik.weatherforcast.domain.model.Forecast
import com.shrikantbadwaik.weatherforcast.view.weatherforecast.WeatherForecastRecyclerAdapter

object BindingAdapter {
    @JvmStatic
    @BindingAdapter(value = ["android:weatherForecast"])
    fun showWeatherForecast(recyclerView: RecyclerView, weatherForecast: ArrayList<Forecast.ForecastDay>?) {
        val adapter = recyclerView.adapter as WeatherForecastRecyclerAdapter?
        adapter?.setForecast(weatherForecast)
    }
}