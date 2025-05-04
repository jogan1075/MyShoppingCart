package com.jmc.myshoppingcart.data

import com.jmc.myshoppingcart.data.factory.Factory
import com.jmc.myshoppingcart.data.model.ProductItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DataRepository @Inject constructor(private val factory: Factory) {

    fun getProducts(): Flow<List<ProductItem>> = flow {
        val response = factory.getRemote().getProducts()
        emit(response)
    }

    fun getProductsByCategory(category: String?): Flow<List<ProductItem>> = flow {
        val response = factory.getRemote().getProductsByCategory(category)
        emit(response)
    }

    fun getCategories(): Flow<List<String>> = flow {
        val response = factory.getRemote().getCategories()
        emit(response)
    }

    fun getProduct(productId: Int): Flow<ProductItem> = flow {
        val response = factory.getRemote().getProduct(productId)
        emit(response)
    }
}