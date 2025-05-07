package com.jmc.myshoppingcart.presentation.contract

import com.jmc.myshoppingcart.data.model.ProductItem
import com.jmc.myshoppingcart.factory.TestFactory.makeProductItemList
import org.junit.Assert.*
import org.junit.Test

class HomeContractTest {

    @Test
    fun `State should initialize with default values`() {
        val state = HomeContract.State()

        assertFalse(state.isLoading)
        assertTrue(state.categories.isEmpty())
        assertTrue(state.products.isEmpty())
        assertTrue(state.cart.isEmpty())
        assertEquals(0, state.cartCount)
    }

    @Test
    fun `cartCount should return correct total quantity`() {
        val cart = mapOf(1 to 2, 2 to 3, 3 to 1) // Total = 6
        val state = HomeContract.State(cart = cart)

        assertEquals(6, state.cartCount)
    }

    @Test
    fun `clearErrors should return a copy with the same values`() {
        val cart = mapOf(1 to 1)
        val categories = listOf("electronics", "toys")
        val products = makeProductItemList()
        val original = HomeContract.State(
            isLoading = true,
            categories = categories,
            products = products,
            cart = cart
        )

        val cleared = original.clearErrors()

        assertEquals(original, cleared)
    }
}
