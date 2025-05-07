package com.jmc.myshoppingcart.ui.view

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.jmc.myshoppingcart.data.model.ProductItem
import com.jmc.myshoppingcart.presentation.DetailViewModel
import com.jmc.myshoppingcart.presentation.contract.DetailContract
import com.jmc.myshoppingcart.presentation.contract.DetailContract.Event.GetDetailProduct

@Composable
fun ProductDetailScreen(
    navController: NavController,
    productId: Int,
    viewModel: DetailViewModel = hiltViewModel()
) {

    val onEventSent = viewModel::setEvent
    val uiState = viewModel.viewState.value
    onEventSent(GetDetailProduct(productId))

    LoadingScreen(uiState.isLoading) {
        ContentDetail(navController, uiState.productItem,onEventSent, uiState)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ContentDetail(
    navController: NavController,
    product: ProductItem?,
    onEventSent: (DetailContract.Event) -> Unit,
    uiState: DetailContract.State
) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(product?.title.toString(), maxLines = 1) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AsyncImage(
                model = product?.image,
                contentDescription = product?.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = product?.title.toString(), style = MaterialTheme.typography.headlineSmall)
            Text(text = "Precio: $${product?.price}", style = MaterialTheme.typography.titleMedium)
            Text(
                text = "Rating: ${product?.rating?.rate} ⭐ (${product?.rating?.count} votos)",
                style = MaterialTheme.typography.bodySmall
            )
            Text(text = product?.description.toString(), style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = {
                onEventSent(DetailContract.Event.AddToCart(product?.id ?: 0))
                Toast.makeText(context, "Producto agregado al carrito, con exito!!!", Toast.LENGTH_SHORT).show()
            }) {
                Text("Agregar al carro")
            }
        }
    }
}