package com.nirviklabs.sportsnews

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.nirviklabs.sportsnews.navigation.AppNavHost
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
            val navController = rememberNavController()
            SportsNewsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                  AppNavHost(navController=navController)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(navController: NavController,newsViewModel: NewsViewModel = viewModel()) {
    val newsState by remember (newsViewModel.newsState) { newsViewModel.newsState }


   Scaffold (
      topBar = {
          CenterAlignedTopAppBar(
              title = {
                  Text("Latest Sports News")
              }
          )
      }
   ){
       Box(modifier = Modifier.padding(it).fillMaxSize(), contentAlignment = Alignment.Center){
           when (newsState) {
               is NewsScreenState.Loading -> {
                   // Show loading indicator
                   CircularProgressIndicator(modifier = Modifier.wrapContentSize())
               }
               is NewsScreenState.Success -> {
                   // Display news list
                   val news = newsState as NewsScreenState.Success

                   ArticleList(news.articles, LocalContext.current,navController)
               }
               is NewsScreenState.Error -> {
                   // Show error message
                   val errorMessage = (newsState as NewsScreenState.Error).message
                   Text(text = errorMessage, color = Color.Red)
               }
           }
       }

   }



    // Fetch news when the screen is initially shown
    LaunchedEffect(Unit) {
        newsViewModel.fetchNews()
    }
}



