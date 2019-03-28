package com.shrikantbadwaik.newsheadlines.domain.model

import com.fasterxml.jackson.annotation.JsonProperty

data class News(
    @JsonProperty("status")
    val status: String? = null,
    @JsonProperty("totalResults")
    val totalResult: Int = 0,
    @JsonProperty("articles")
    val articles: ArrayList<Article> = ArrayList()
)