package com.nirviklabs.sportsnews.data.remote

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://newsapi.org/v2/"

    val interceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }
    val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .addInterceptor { chain ->
            val request: Request = chain.request()
            println("Request URL: ${request.url}")
            chain.proceed(request)
        }
        .build()

    val api: NewsService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(NewsService::class.java)
    }
}