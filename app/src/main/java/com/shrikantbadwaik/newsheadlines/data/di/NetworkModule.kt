package com.shrikantbadwaik.newsheadlines.data.di

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.shrikantbadwaik.newsheadlines.BuildConfig
import com.shrikantbadwaik.newsheadlines.data.remote.NewsApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level =
            if (BuildConfig.SHOW_LOGS) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return@single loggingInterceptor
    }

    single {
        val builder = OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .readTimeout(0, TimeUnit.MILLISECONDS)
            .connectTimeout(0, TimeUnit.MILLISECONDS)
        return@single builder.build()
    }

    single {
        val objectMapper = jacksonObjectMapper()
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        return@single JacksonConverterFactory.create(objectMapper)
    }

    single {
        val builder = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(get<OkHttpClient>())
            .addConverterFactory(get<JacksonConverterFactory>())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        return@single builder.build()
    }

    single { return@single get<Retrofit>().create(NewsApi::class.java) }
}