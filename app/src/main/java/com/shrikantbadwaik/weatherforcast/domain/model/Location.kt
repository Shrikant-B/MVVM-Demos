package com.shrikantbadwaik.weatherforcast.domain.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Location(
    @JsonProperty("name")
    val city: String?,
    @JsonProperty("region")
    val region: String?,
    @JsonProperty("country")
    val country: String?,
    @JsonProperty("lat")
    val latitude: Double,
    @JsonProperty("lon")
    val longitude: Double
)