package com.shrikantbadwaik.searchtweets.domain.model

import com.fasterxml.jackson.annotation.JsonProperty

data class TwitterAccessToken(
    @JsonProperty("token_type")
    val tokenType: String?,
    @JsonProperty("access_token")
    val accessToken: String?
)