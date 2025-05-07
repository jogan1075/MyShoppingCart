package com.jmc.myshoppingcart.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jmc.myshoppingcart.navigation.Routes
import com.jmc.myshoppingcart.presentation.HomeViewModel
import com.jmc.myshoppingcart.presentation.contract.HomeContract
import com.jmc.myshoppingcart.ui.view.composables.FeaturedProductCard
import com.jmc.myshoppingcart.ui.view.composables.ProductCard
import kotlinx.coroutines.launch

@Composable
fun HomeScreenView(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
) {
    val onEventSent = viewModel::setEvent
    val uiState = viewModel.viewState.value

    onEventSent(HomeContract.Event.InitValues)



    LoadingScreen(uiState.isLoading) {
        Content(onEventSent, uiState,navController)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    onEventSent: (HomeContract.Event) -> Unit,
    uiState: HomeContract.State,
    navController: NavController
) {
    val featured = uiState.products.maxByOrNull { it.rating.rate * it.rating.count }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(end = 48.dp)
                    .background(Color.Gray),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Categorías", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(18.dp))
                Column(modifier = Modifier.padding(8.dp)) {
                    uiState.categories.forEach { category ->
                        Text(
                            text = category,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    scope.launch { drawerState.close() }
                                    navController.navigate("searchResult/$category")
                                }
                                .padding(8.dp)
                        )
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Walmart MVP") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                if (drawerState.isClosed) drawerState.open()
                                else drawerState.close()
                            }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
                    actions = {
                        BadgedBox(badge = {
                            if (uiState.cartCount > 0) {
                                Badge { Text(uiState.cartCount.toString()) }
                            }
                        }) {
                            IconButton(onClick = {
                                navController.navigate(Routes.CartScreen.route)
                            }) {
                                Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")
                            }
                        }
                    }
                )
            }
        ) { padding ->
            LazyColumn(contentPadding = padding) {
                featured?.let {
                    item {
                        FeaturedProductCard(
                            product = it,
                            onClick = { navController.navigate("productDetail/${it.id}") },
                            onAddToCart = { onEventSent(HomeContract.Event.AddToCart(it.id)) })
                    }
                }


                items(uiState.products.size) { product ->
                    ProductCard(
                        product = uiState.products[product],
                        onClick = { navController.navigate("productDetail/${uiState.products[product].id}") },
                        onAddToCart = { onEventSent(HomeContract.Event.AddToCart(uiState.products[product].id)) })
                }


            }
        }
    }


}