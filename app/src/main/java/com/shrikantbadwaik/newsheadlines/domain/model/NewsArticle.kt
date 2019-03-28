package com.shrikantbadwaik.newsheadlines.domain.model

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Source(
    @JsonProperty("id")
    val sourceId: String? = null,
    @JsonProperty("name")
    val sourceName: String? = null
) : Parcelable

@Parcelize
data class Article(
    @JsonProperty("source")
    val source: Source? = null,
    @JsonProperty("author")
    val articleAuthor: String? = null,
    @JsonProperty("title")
    val articleTitle: String? = null,
    @JsonProperty("description")
    val articleDescription: String? = null,
    @JsonProperty("url")
    val articleUrl: String? = null,
    @JsonProperty("urlToImage")
    val imageUrl: String? = null,
    @JsonProperty("publishedAt")
    val publicationDate: String? = null,
    @JsonProperty("content")
    val articleContent: String? = null
) : Parcelable