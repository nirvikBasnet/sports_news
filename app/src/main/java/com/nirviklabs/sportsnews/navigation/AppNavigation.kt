package com.nirviklabs.sportsnews.navigation

enum class Screen {
    Home,
    Browser,
}


sealed class NavigationItem(val route: String) {
    object Browser : NavigationItem(Screen.Browser.name)
    object Home : NavigationItem(Screen.Home.name)

}