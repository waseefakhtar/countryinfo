package com.waseefakhtar.countryinfo.api

import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

private const val TIME_OUT_CONNECT: Long = 60
private const val TIME_OUT_READ: Long = 60
private const val CACHE_SIZE: Long = 10 * 1024 * 1024

private class RetrofitBuilder {

    fun makeRetrofit(baseUrl: String, interceptors: List<Interceptor>, cacheFolder: File): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(getConverterFactory())
        .client(makeHttpClient(interceptors, cacheFolder))
        .build()

    private fun getConverterFactory(): Converter.Factory = GsonConverterFactory.create(GsonBuilder().create())

    private fun makeHttpClient(interceptors: List<Interceptor>, cacheFolder: File): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .cache(Cache(cacheFolder, CACHE_SIZE))
            .connectTimeout(TIME_OUT_CONNECT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT_READ, TimeUnit.SECONDS)
        interceptors.forEach {
            builder.addInterceptor(it)
        }
        return builder.build()
    }
}

class EndpointFactory(
    private val baseApiUrl: String,
    private val cacheFolder: File,
    enableLogs: Boolean
) {
    private val retrofitBuilder = RetrofitBuilder()
    private val interceptors = listOfNotNull(
        HttpLoggingInterceptor()
            .apply { level = HttpLoggingInterceptor.Level.BODY }
            .takeIf { enableLogs }
    )

    fun <T> create(api: Class<T>): T = retrofitBuilder.makeRetrofit(baseApiUrl, interceptors, cacheFolder).create(api)
}