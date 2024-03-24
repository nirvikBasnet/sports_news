package com.nirviklabs.sportsnews.ui.theme

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nirviklabs.sportsnews.data.remote.Article
import com.nirviklabs.sportsnews.data.remote.News
import com.nirviklabs.sportsnews.data.remote.RetrofitInstance
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {
    private val api = RetrofitInstance.api


        private val _newsState = mutableStateOf<NewsScreenState>(NewsScreenState.Loading)
        val newsState: State<NewsScreenState> = _newsState


    fun fetchNews() {
            viewModelScope.launch {
                try {
                    _newsState.value = NewsScreenState.Loading
                    val response = api.getNews()
                    if (response.isSuccessful) {
                        val news = response.body()
                        _newsState.value = news?.let { NewsScreenState.Success(news = it, articles = it.articles) }!!
                    } else {
                        _newsState.value = NewsScreenState.Error("Failed to fetch news: ${response.message()}")
                    }
                } catch (e: Exception) {
                    _newsState.value = NewsScreenState.Error("An error occurred: ${e.message}")
                }
            }
        }



}