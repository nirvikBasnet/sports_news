package com.nirviklabs.sportsnews.data.remote

data class News(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)