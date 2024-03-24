package com.nirviklabs.sportsnews.data.remote

import retrofit2.Response
import retrofit2.http.GET

interface NewsService {
    @GET("everything?q=sports&apiKey=a96cbf9068b64ea8acb15db3e5524353")
    suspend fun getNews(
    ): Response<News>
}