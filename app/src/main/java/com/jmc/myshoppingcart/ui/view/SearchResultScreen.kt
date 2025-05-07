package com.jmc.myshoppingcart.ui.view

import android.R.attr.category
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.jmc.myshoppingcart.data.model.ProductItem
import com.jmc.myshoppingcart.presentation.SearchViewModel
import com.jmc.myshoppingcart.presentation.contract.SearchContract
import com.jmc.myshoppingcart.presentation.contract.SearchContract.Event.SearchProductByCategory

@Composable
fun SearchResultScreen(
    category: String,
    viewModel: SearchViewModel = hiltViewModel(),
    navController: NavController
) {
    val onEventSent = viewModel::setEvent
    val uiState = viewModel.viewState.value

    onEventSent(SearchProductByCategory(category))
    LoadingScreen(uiState.isLoading) {
        ContentSearch(uiState,onEventSent,navController)
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentSearch(
    uiState: SearchContract.State,
    onEventSent: (SearchContract.Event) -> Unit,
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Categoría: $category") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
            ) {
                    LazyColumn {
                        items(uiState.products.size) { index ->
                            ProductResultItem(product = uiState.products[index]) {
                                navController.navigate("productDetail/${uiState.products[index].id}")
                            }
                        }
                    }

            }
        }
    )
}

@Composable
fun ProductResultItem(product: ProductItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = product.image,
                contentDescription = product.title,
                modifier = Modifier
                    .size(64.dp)
                    .padding(end = 16.dp),
                contentScale = ContentScale.Crop
            )
            Column {
                Text(product.title, style = MaterialTheme.typography.titleMedium)
                Text("$${product.price}", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
