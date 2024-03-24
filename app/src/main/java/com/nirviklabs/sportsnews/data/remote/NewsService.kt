package com.nirviklabs.sportsnews.data.remote

import retrofit2.Response
import retrofit2.http.GET

interface NewsService {
    @GET("everything?q=sports&apiKey=")
    suspend fun getNews(
    ): Response<News>
}