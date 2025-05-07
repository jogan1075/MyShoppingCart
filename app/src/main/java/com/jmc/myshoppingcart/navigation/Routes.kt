package com.jmc.myshoppingcart.navigation

sealed class Routes(val route: String) {

    data object SplashScreen : Routes(NavigationScreens.SPLASH)
    data object HomeScreen : Routes(NavigationScreens.HOME)
    data object CartScreen : Routes(NavigationScreens.CART)
    data object SearchScreen : Routes(NavigationScreens.SEARCH)
}


object NavigationScreens {
    const val SPLASH = "splash_screen"
    const val HOME = "home_screen"
    const val CART = "cart_screen"
    const val  SEARCH= "searchResult_screen"
}