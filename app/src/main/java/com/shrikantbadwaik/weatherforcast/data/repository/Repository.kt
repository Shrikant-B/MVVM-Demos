package com.shrikantbadwaik.weatherforcast.data.repository

import com.shrikantbadwaik.weatherforcast.domain.model.CurrentJSON
import com.shrikantbadwaik.weatherforcast.domain.model.ForeCastJSON
import io.reactivex.Observable

interface Repository {
    fun getCurrentWeatherForecast(query: String): Observable<CurrentJSON>
    fun getNextFourDayWeatherForecast(query: String): Observable<ForeCastJSON>
}