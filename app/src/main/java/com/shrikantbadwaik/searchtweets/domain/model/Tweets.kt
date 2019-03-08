package com.shrikantbadwaik.searchtweets.domain.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Tweets(
    @JsonProperty("statuses")
    val tweets: ArrayList<Tweet>
)

data class Tweet(
    @JsonProperty("id")
    val tweetId: Long,
    @JsonProperty("user")
    val twitterUser: TwitterUser?,
    @JsonProperty("retweet_count")
    val retweetCount: Int,
    @JsonProperty("favorite_count")
    val favouriteCount: Int,
    @JsonProperty("text")
    val tweetText: String?,
    @JsonProperty("extended_entities")
    val extendedEntities: Entities?
)

data class TwitterUser(
    @JsonProperty("id")
    val userId: Long,
    @JsonProperty("name")
    val userName: String?,
    @JsonProperty("screen_name")
    val screenName: String?,
    @JsonProperty("profile_image_url")
    val profileImage: String?
)