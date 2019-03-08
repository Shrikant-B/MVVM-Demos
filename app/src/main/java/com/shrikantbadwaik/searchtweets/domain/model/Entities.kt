package com.shrikantbadwaik.searchtweets.domain.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Entities(
    @JsonProperty("media")
    val media: ArrayList<Media>?
)

data class Media(
    @JsonProperty("id")
    val mediaId: Long,
    @JsonProperty("media_url")
    val mediaUrl: String?,
    @JsonProperty("type")
    val mediaType: String?
)