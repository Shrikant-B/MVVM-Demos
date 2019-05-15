package com.shrikantbadwaik.newsheadlines.domain.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import com.shrikantbadwaik.newsheadlines.domain.util.Constants
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Source(
    @JsonProperty("id")
    val sourceId: String? = null,
    @JsonProperty("name")
    val sourceName: String? = null
) : Parcelable

@Entity(tableName = "newsArticle")
@Parcelize
data class Article(
    @PrimaryKey(autoGenerate = true)
    val articleId: Long,
    var category: String = Constants.CATEGORIES[0],
    var country: String = Constants.COUNTRIES[0],
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