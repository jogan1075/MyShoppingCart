package com.jmc.shoppingcart.data.model

import com.jmc.myshoppingcart.data.model.Rating

data class Category(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val rating: Rating,
    val title: String
)