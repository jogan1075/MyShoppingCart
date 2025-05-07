package com.jmc.myshoppingcart.presentation.contract
import com.jmc.myshoppingcart.presentation.contract.SearchContract
import com.jmc.myshoppingcart.data.model.ProductItem
import com.jmc.myshoppingcart.factory.TestFactory.makeProductItemList
import org.junit.Assert.*
import org.junit.Test

class SearchContractTest {

    @Test
    fun `initial state should be empty and not loading`() {
        val state = SearchContract.State()

        assertFalse(state.isLoading)
        assertTrue(state.products.isEmpty())
        assertTrue(state.categories.isEmpty())
        assertTrue(state.cart.isEmpty())
        assertEquals(0, state.cartCount)
    }

    @Test
    fun `cart count should return correct total quantity`() {
        val cart = mapOf(1 to 2, 2 to 3)
        val state = SearchContract.State(cart = cart)

        assertEquals(5, state.cartCount)
    }

    @Test
    fun `clearErrors should return the same state`() {
        val state = SearchContract.State(
            isLoading = true,
            categories = listOf("electronics"),
            products = makeProductItemList(),
            cart = mapOf(1 to 1)
        )

        val cleared = state.clearErrors()

        assertEquals(state, cleared)
    }

    @Test
    fun `event SearchProductByCategory should hold correct category`() {
        val category = "electronics"
        val event = SearchContract.Event.SearchProductByCategory(category)

        assertEquals(category, event.category)
    }
}
