package com.jmc.myshoppingcart.data.model

data class Category(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val rating: Rating,
    val title: String
)