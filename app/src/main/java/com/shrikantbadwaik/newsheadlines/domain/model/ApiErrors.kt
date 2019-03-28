package com.shrikantbadwaik.newsheadlines.domain.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ApiErrors(
    @JsonProperty("status")
    val status: String? = null,
    @JsonProperty("code")
    val code: String? = null,
    @JsonProperty("message")
    val message: String? = null
)