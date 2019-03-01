package com.shrikantbadwaik.weatherforcast.domain.model

import com.fasterxml.jackson.annotation.JsonProperty

data class CurrentJSON(
    @JsonProperty("location")
    val location: Location?,
    @JsonProperty("current")
    val current: Current?
)

data class ForeCastJSON(
    @JsonProperty("location")
    val location: Location?,
    @JsonProperty("current")
    val current: Current?,
    @JsonProperty("forecast")
    val forecast: Forecast
)