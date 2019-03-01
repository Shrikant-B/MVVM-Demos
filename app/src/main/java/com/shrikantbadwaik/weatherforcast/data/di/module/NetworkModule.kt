package com.shrikantbadwaik.weatherforcast.data.di.module

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.shrikantbadwaik.weatherforcast.BuildConfig
import com.shrikantbadwaik.weatherforcast.data.remote.WeatherForecastApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun okHttp3Client(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level =
            if (BuildConfig.SHOW_LOGS) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .readTimeout(0, TimeUnit.MILLISECONDS)
            .connectTimeout(0, TimeUnit.MILLISECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun jacksonConverterFactory(): JacksonConverterFactory {
        val objectMapper = ObjectMapper()
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        return JacksonConverterFactory.create(objectMapper)
    }

    @Provides
    @Singleton
    fun retrofit(okHttpClient: OkHttpClient, jacksonConverterFactory: JacksonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(jacksonConverterFactory)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun weatherForecastApi(retrofit: Retrofit): WeatherForecastApi {
        return retrofit.create(WeatherForecastApi::class.java)
    }
}