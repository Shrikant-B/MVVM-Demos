package com.shrikantbadwaik.weatherforcast.domain.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Forecast(
    @JsonProperty("forecastday")
    val forecastDay: ArrayList<ForecastDay>?
) {
    data class ForecastDay(
        @JsonProperty("date")
        val date: String?,
        @JsonProperty("day")
        val day: Day?
    )

    data class Day(
        @JsonProperty("maxtemp_c")
        val maxTempInC: Double,
        @JsonProperty("maxtemp_f")
        val maxTempInF: Double,
        @JsonProperty("mintemp_c")
        val minTempInC: Double,
        @JsonProperty("mintemp_f")
        val minTempInF: Double
    )
}