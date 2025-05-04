package com.jmc.myshoppingcart.data.remote

import com.jmc.myshoppingcart.data.model.ProductItem
import com.jmc.myshoppingcart.data.repository.Remote
import javax.inject.Inject

class RemoteImpl @Inject constructor(private val api: ApiService): Remote {
    override suspend fun getProducts(): List<ProductItem> {
        return api.getAllProducts()
    }

    override suspend fun getProductsByCategory(category: String?): List<ProductItem> {
      return api.getProductsByCategory(category)
    }

    override suspend fun getCategories(): List<String> {
        return api.getAllCategories()
    }

    override suspend fun getProduct(productId: Int): ProductItem {
        return api.getProductById(productId)
    }
}