package com.shrikantbadwaik.weatherforcast.data.repository

import com.shrikantbadwaik.weatherforcast.BuildConfig
import com.shrikantbadwaik.weatherforcast.data.remote.WeatherForecastApi
import com.shrikantbadwaik.weatherforcast.domain.model.CurrentJSON
import com.shrikantbadwaik.weatherforcast.domain.model.ForeCastJSON
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    private val api: WeatherForecastApi
) : Repository {

    override fun getCurrentWeatherForecast(query: String): Observable<CurrentJSON> {
        return api.getCurrentWeather(BuildConfig.API_KEY, query)
    }

    override fun getNextFourDayWeatherForecast(query: String): Observable<ForeCastJSON> {
        return api.getNextFourDaysWeather(BuildConfig.API_KEY, query, 5)
    }
}