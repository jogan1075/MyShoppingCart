package com.jmc.myshoppingcart.data.repository

import com.jmc.myshoppingcart.data.model.ProductItem

interface Remote {

  suspend fun getProducts(): List<ProductItem>
  suspend fun getProductsByCategory(category: String?): List<ProductItem>
  suspend fun getCategories(): List<String>
  suspend fun getProduct(productId: Int): ProductItem
}