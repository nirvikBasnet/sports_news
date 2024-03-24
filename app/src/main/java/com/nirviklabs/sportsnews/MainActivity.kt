package com.nirviklabs.sportsnews

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nirviklabs.sportsnews.ui.theme.ArticleList
import com.nirviklabs.sportsnews.ui.theme.NewsScreenState
import com.nirviklabs.sportsnews.ui.theme.NewsViewModel

import com.nirviklabs.sportsnews.ui.theme.SportsNewsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val w = window
        w.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        installSplashScreen()
        setContent {
            SportsNewsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                  NewsScreen()
                }
            }
        }
    }
}

@Composable
fun NewsScreen(newsViewModel: NewsViewModel = viewModel()) {
    val newsState by remember (newsViewModel.newsState) { newsViewModel.newsState }

    when (newsState) {
        is NewsScreenState.Loading -> {
            // Show loading indicator
            CircularProgressIndicator(modifier = Modifier.wrapContentSize())
        }
        is NewsScreenState.Success -> {
            // Display news list
            val news = newsState as NewsScreenState.Success

            ArticleList(news.articles, LocalContext.current)
        }
        is NewsScreenState.Error -> {
            // Show error message
            val errorMessage = (newsState as NewsScreenState.Error).message
            Text(text = errorMessage, color = Color.Red)
        }
    }

    // Fetch news when the screen is initially shown
    LaunchedEffect(Unit) {
        newsViewModel.fetchNews()
    }
}

