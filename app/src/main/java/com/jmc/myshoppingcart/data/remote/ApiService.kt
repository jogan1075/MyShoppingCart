package com.jmc.myshoppingcart.data.remote

import com.jmc.myshoppingcart.data.model.ProductItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("products")
    suspend fun getAllProducts(
        @Query("limit") limit: Int? = null, @Query("sort") sort: String? = null
    ): List<ProductItem>

    @GET("products/{id}")
    suspend fun getProductById(
        @Path("id") id: Int
    ): ProductItem

    @GET("products/categories")
    suspend fun getAllCategories(): List<String>

    @GET("products/category/{category}")
    suspend fun getProductsByCategory(
        @Path("category") category: String?
    ): List<ProductItem>
}