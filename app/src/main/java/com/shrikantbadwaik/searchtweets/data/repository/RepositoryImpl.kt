package com.shrikantbadwaik.searchtweets.data.repository

import com.shrikantbadwaik.searchtweets.data.remote.TwitterApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    private val api: TwitterApi
) : Repository {

}