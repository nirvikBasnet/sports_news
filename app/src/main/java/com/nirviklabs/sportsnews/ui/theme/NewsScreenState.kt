package com.nirviklabs.sportsnews.ui.theme

import com.nirviklabs.sportsnews.data.remote.Article
import com.nirviklabs.sportsnews.data.remote.News

sealed class NewsScreenState {
    object Loading : NewsScreenState()
    data class Success(val news: News, val articles: List<Article>) : NewsScreenState()
    data class Error(val message: String) : NewsScreenState()
}