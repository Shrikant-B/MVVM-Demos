package com.shrikantbadwaik.weatherforcast.data.remote

import com.shrikantbadwaik.weatherforcast.domain.model.CurrentJSON
import com.shrikantbadwaik.weatherforcast.domain.model.ForeCastJSON
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherForecastApi {
    @GET("current.json")
    fun getCurrentWeather(
        @Query("key") apiKey: String, @Query("q") query: String
    ): Observable<CurrentJSON>

    @GET("forecast.json")
    fun getNextFourDaysWeather(
        @Query("key") apiKey: String, @Query("q") query: String,
        @Query("days") days: Int
    ): Observable<ForeCastJSON>
}