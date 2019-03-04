package com.shrikantbadwaik.weatherforcast.view.weatherforecast

import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shrikantbadwaik.weatherforcast.BR
import com.shrikantbadwaik.weatherforcast.R
import com.shrikantbadwaik.weatherforcast.databinding.LayoutWeatherForecastAdapterContentBinding
import com.shrikantbadwaik.weatherforcast.domain.model.Forecast
import com.shrikantbadwaik.weatherforcast.domain.util.AppUtil
import com.shrikantbadwaik.weatherforcast.domain.util.DateUtil
import javax.inject.Inject

class WeatherForecastRecyclerAdapter @Inject constructor(
    private val appUtil: AppUtil
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val weatherForecast: ArrayList<Forecast.ForecastDay> = ArrayList()

    fun setForecast(weatherForecast: ArrayList<Forecast.ForecastDay>?) {
        weatherForecast?.let {
            this.weatherForecast.clear()
            this.weatherForecast.addAll(it)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val adapterBinding = DataBindingUtil.inflate<LayoutWeatherForecastAdapterContentBinding>(
            LayoutInflater.from(parent.context), R.layout.layout_weather_forecast_adapter_content,
            parent, false
        )
        return ContentViewHolder(adapterBinding)
    }

    override fun getItemCount() = weatherForecast.size

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val holder = viewHolder as ContentViewHolder
        holder.bind(weatherForecast[position])
    }

    inner class ContentViewHolder(
        private val binding: LayoutWeatherForecastAdapterContentBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(forecastDay: Forecast.ForecastDay) {
            val viewModel = ViewModel(forecastDay)
            binding.setVariable(BR.viewModel, viewModel)
            binding.executePendingBindings()
        }

        inner class ViewModel(forecastDay: Forecast.ForecastDay) {
            private val dayOfWeek: ObservableField<String> = ObservableField()
            private val tempInC: ObservableField<String> = ObservableField()

            fun getDayOfWeek() = dayOfWeek

            fun getTempInC() = tempInC

            init {
                forecastDay.date?.let {
                    val day = DateUtil.dateToString(
                        DateUtil.eeee, DateUtil.stringToDate(DateUtil.yyyyMMdd, it)
                    )
                    dayOfWeek.set(day)
                }
                forecastDay.day?.let {
                    val averageTemp = (it.maxTempInC.plus(it.minTempInC)).div(2)
                    tempInC.set(appUtil.getString(R.string.format_02, averageTemp))
                }
            }
        }
    }
}