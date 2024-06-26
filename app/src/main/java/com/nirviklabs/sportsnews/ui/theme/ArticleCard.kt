package com.nirviklabs.sportsnews.ui.theme

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.nirviklabs.sportsnews.data.remote.Article
import com.nirviklabs.sportsnews.navigation.NavigationItem
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ArticleCard(article: Article,context: Context,navController: NavController) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth().clickable {
                val encodedUrl = URLEncoder.encode(article.url, StandardCharsets.UTF_8.toString())
                     navController.navigate("Browser/$encodedUrl")
            },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            // Column with a weight of 2
            Column(
                modifier = Modifier
                    .weight(2f)

            ) {
                val truncatedTitle = truncateString(article.title, 80)
                Text(
                    text = truncatedTitle,
                    style = MaterialTheme.typography.bodyLarge,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))

                article.description?.let {
                    val truncatedDescription = truncateString(article.description, 100)
                    Text(
                        text = truncatedDescription,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
                // Display a clickable link to open the article's URL
                Text(
                    text = "Read More",
                    style = MaterialTheme.typography.bodySmall,
                )
            }

            Image(
                painter = rememberImagePainter(article.urlToImage),
                contentDescription = article.title,
                modifier = Modifier.size(100.dp).clip(RoundedCornerShape(5.dp)),
                contentScale = ContentScale.Crop
            )


        }
    }

}

private fun truncateString(title: String, maxLength: Int): String {
    val truncatedTitle = if (title.length > maxLength) {
        title.take(maxLength) + "..." // Append ellipsis if description is longer than maxLength
    } else {
        title
    }
    return truncatedTitle
}

@Composable
fun ArticleList(articles: List<Article>,context: Context, navController: NavController) {
    LazyColumn {
        items(articles){
            ArticleCard(it, context, navController )
        }
    }
}
