package com.jmc.myshoppingcart.factory

import com.jmc.myshoppingcart.data.model.ProductItem
import com.jmc.myshoppingcart.data.model.Rating
import com.jmc.myshoppingcart.utils.RandomFactory

object TestFactory {

    fun makeProductItemList(count: Int = 1) =
        (0 until count).map { makeProductItem() }

     fun makeProductItem(): ProductItem {
        return ProductItem(
            category = RandomFactory.generateString(),
            description = RandomFactory.generateString(),
            id = RandomFactory.generateInt(),
            image = RandomFactory.generateString(),
            price = RandomFactory.generateDouble(),
            rating = makeRating(),
            title = RandomFactory.generateString()
        )
    }

    private fun makeRating(): Rating {
        return Rating(
            count = RandomFactory.generateInt(),
            rate = RandomFactory.generateDouble()
        )
    }

    fun makeListString(count: Int): List<String> {
        return (0..count).map {
            RandomFactory.generateString()
        }
    }
}