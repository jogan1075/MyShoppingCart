package com.jmc.myshoppingcart.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jmc.myshoppingcart.data.model.ProductItem

@Composable
fun ProductCard(product: ProductItem, onClick: () -> Unit, onAddToCart: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = product.image,
                contentDescription = null,
                modifier = Modifier.size(64.dp).padding(end = 16.dp)
            )
            Column(Modifier.weight(1f)) {
                Text(product.title)
                Text("$${product.price}")
            }
            IconButton(onClick = onAddToCart) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    }
}