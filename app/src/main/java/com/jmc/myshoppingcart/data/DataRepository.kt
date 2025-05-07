package com.jmc.myshoppingcart.data

import androidx.collection.mutableDoubleListOf
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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

    fun saveInCache(newCart: MutableMap<Int, Int>) {
        factory.getCache().saveValueInCache("CARRITO", Gson().toJson(newCart))
    }

    fun getValueByCache() : Flow<MutableMap<Int, Int>> = flow {
        val type = object : TypeToken<MutableMap<Int, Int>>() {}.type
        val response = factory.getCache().getValueByCache("CARRITO", "")
        val map: MutableMap<Int, Int> = Gson().fromJson(response, type)
        emit(map)
    }
}