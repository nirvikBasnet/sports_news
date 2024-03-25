package com.nirviklabs.sportsnews.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.nirviklabs.sportsnews.NewsScreen
import com.nirviklabs.sportsnews.ui.theme.InAppBrowser

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.Home.route,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {

        composable(NavigationItem.Home.route) {
            NewsScreen(navController)
        }
        /*
        Main Screen or Home Screen
         */
        composable(route = "Browser/{url}",
            arguments = listOf(
                navArgument(name = "url"){
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
          val url = backStackEntry.arguments?.getString("url") ?: ""
            InAppBrowser(
                url = url
            )
        }
    }
}