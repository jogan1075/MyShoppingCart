package com.jmc.myshoppingcart.presentation.contract

import com.jmc.myshoppingcart.data.model.ProductItem
import com.jmc.myshoppingcart.factory.TestFactory.makeProductItem
import com.jmc.myshoppingcart.utils.RandomFactory
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import org.junit.Test

class CartContractTest {


    private lateinit var event: CartContract.Event

    private lateinit var state: CartContract.State

    @Test
    fun `initial state should have empty product list and cart`() {
        val state = CartContract.State()

        assertTrue(state.products.isEmpty())
        assertTrue(state.cart.isEmpty())
        assertEquals(0, state.cartCount)
    }

    @Test
    fun `cartCount should sum quantities of items in cart`() {
        val cart = mapOf(
            1 to 2,
            2 to 3,
            3 to 1
        )
        val state = CartContract.State(cart = cart)

        assertEquals(6, state.cartCount)
    }

    @Test
    fun `clearErrors should return a copy of the same state`() {
        val initialState = CartContract.State(
            isLoading = true,
            products = listOf(makeProductItem()),
            cart = mapOf(1 to 2)
        )

        val clearedState = initialState.clearErrors()

        assertEquals(initialState, clearedState)
    }

    @Test
    fun `AddToCart event should accept nullable id`() {
        val event = CartContract.Event.AddToCart(id = null)
        assertNull(event.id)
    }

}