package com.shrikantbadwaik.weatherforcast.domain.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Current(
    @JsonProperty("last_updated")
    val lastUpdated: String,
    @JsonProperty("temp_c")
    val tempInC: Double,
    @JsonProperty("temp_f")
    val tempInF: Double
)