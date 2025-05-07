package com.jmc.myshoppingcart.navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jmc.myshoppingcart.ui.view.CartScreenView
import com.jmc.myshoppingcart.ui.view.HomeScreenView
import com.jmc.myshoppingcart.ui.view.ProductDetailScreen
import com.jmc.myshoppingcart.ui.view.SearchResultScreen
import com.jmc.myshoppingcart.ui.view.SplashScreenView

@Composable
fun Navigation(startDestination: String = Routes.HomeScreen.route) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()


    NavHost(navController = navController, startDestination = startDestination) {

        composable(Routes.SplashScreen.route) {
            SplashScreenView()
        }

        composable(Routes.HomeScreen.route) {
            HomeScreenView(
               navController = navController)
        }

        composable(Routes.CartScreen.route) {
            CartScreenView(navController = navController)
        }

        composable("searchResult/{category}") { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: ""
            SearchResultScreen(category = category,navController= navController)
        }

        composable("productDetail/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")?.toIntOrNull()

            if (productId != null) {
                ProductDetailScreen(productId = productId, navController = navController)
            } else {
                Text("Producto no encontrado")
            }
        }
    }
}