package com.jmc.myshoppingcart.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.jmc.myshoppingcart.presentation.CartViewModel
import com.jmc.myshoppingcart.presentation.contract.CartContract

@Composable
fun CartScreenView(viewModel: CartViewModel = hiltViewModel(),navController: NavController) {
    val onEventSent = viewModel::setEvent
    val uiState = viewModel.viewState.value

//    val cartItems = viewModel.cart
//    val allProducts = viewModel.products
//    val productsInCart = allProducts.filter { cartItems.containsKey(it.id) }
//    val total = viewModel.getCartTotal(productsInCart)

    LoadingScreen(uiState.isLoading) {
        ContentCart(uiState,onEventSent,navController)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ContentCart(
    uiState: CartContract.State,
    onEventSent: (CartContract.Event) -> Unit,
    navController: NavController
) {

    val productsInCart = uiState.products.filter { uiState.cart.containsKey(it.id) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Carrito Compras", maxLines = 1) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column (modifier = Modifier.padding(padding)){
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                items(productsInCart.size) { index ->
                    val quantity = uiState.cart.values.elementAt(index)
                    val quantityFilter = quantity
                    Spacer(Modifier.size(16.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        AsyncImage(
                            model = productsInCart[index].image,
                            contentDescription = productsInCart[index].title,
                            modifier = Modifier
                                .size(64.dp)
                                .padding(end = 8.dp)
                        )
                        Text(productsInCart[index].title, modifier = Modifier.weight(1f))
                        IconButton(onClick = { /*viewModel.removeFromCart(product.id)*/ }) {
                            Icon(Icons.Default.Clear, contentDescription = null)
                        }
                        Text("$quantity")
                        IconButton(onClick = { /*viewModel.addToCart(product.id) */ }) {
                            Icon(Icons.Default.Add, contentDescription = null)
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Total: ", style = MaterialTheme.typography.titleMedium)
                    Button(
                        onClick = {}, modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    ) {
                        Text("Purchase")
                    }
                }
            }
        }
    }
}