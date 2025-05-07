package com.jmc.myshoppingcart.presentation.contract

import com.jmc.myshoppingcart.data.model.ProductItem
import com.jmc.myshoppingcart.factory.TestFactory.makeProductItem
import org.junit.Assert.*
import org.junit.Test

class DetailContractTest {

    @Test
    fun `State should initialize with default values`() {
        val state = DetailContract.State()

        assertFalse(state.isLoading)
        assertNull(state.productItem)
        assertTrue(state.cart.isEmpty())
        assertEquals(0, state.cartCount)
    }

    @Test
    fun `cartCount should return total quantity of items in cart`() {
        val cart = mapOf(1 to 1, 2 to 2)
        val state = DetailContract.State(cart = cart)

        assertEquals(3, state.cartCount)
    }

    @Test
    fun `clearErrors should return a copy with same values`() {
        val product = makeProductItem()
        val cart = mapOf(1 to 2)

        val original = DetailContract.State(
            isLoading = true,
            productItem = product,
            cart = cart
        )

        val cleared = original.clearErrors()

        assertEquals(original, cleared)
    }
}
