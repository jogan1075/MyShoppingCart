package com.jmc.myshoppingcart.presentation

import androidx.lifecycle.SavedStateHandle
import com.jmc.myshoppingcart.data.DataRepository
import com.jmc.myshoppingcart.factory.TestFactory.makeProductItemList
import com.jmc.myshoppingcart.presentation.contract.CartContract
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CartViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: DataRepository
    private lateinit var viewModel: CartViewModel

    private val fakeProducts = makeProductItemList()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk(relaxed = true)

        coEvery { repository.getValueByCache() } returns flowOf(mutableMapOf())
        coEvery { repository.getProducts() } returns flowOf(fakeProducts)

        viewModel = CartViewModel(repository, SavedStateHandle())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should load cart and products`() = runTest {
        advanceUntilIdle()
        val state = viewModel.viewState.value

        assertFalse(state.isLoading)
        assertEquals(fakeProducts, state.products)
        assertTrue(state.cart.isEmpty())
    }

    @Test
    fun `addToCart should insert product to empty cart`() = runTest {
        coEvery { repository.saveInCache(any()) } just Runs

        viewModel.handleEvents(CartContract.Event.AddToCart(1))
        advanceUntilIdle()

        val state = viewModel.viewState.value
        assertEquals(1, state.cart[1])
        verify { repository.saveInCache(match { it[1] == 1 }) }
    }

    @Test
    fun `addToCart should increment quantity if product exists`() = runTest {
        coEvery { repository.getValueByCache() } returns flowOf(mutableMapOf(2 to 2))
        coEvery { repository.saveInCache(any()) } just Runs

        viewModel.handleEvents(CartContract.Event.AddToCart(2))
        advanceUntilIdle()

        val state = viewModel.viewState.value
        assertEquals(3, state.cart[2])
        verify { repository.saveInCache(match { it[2] == 3 }) }
    }

    @Test
    fun `GetProducts should refresh product list`() = runTest {
        viewModel.handleEvents(CartContract.Event.GetProducts)
        advanceUntilIdle()

        val state = viewModel.viewState.value
        assertEquals(fakeProducts, state.products)
    }
}
