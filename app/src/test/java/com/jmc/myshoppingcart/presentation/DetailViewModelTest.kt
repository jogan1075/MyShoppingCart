package com.jmc.myshoppingcart.presentation

import androidx.lifecycle.SavedStateHandle
import com.jmc.myshoppingcart.data.DataRepository
import com.jmc.myshoppingcart.factory.TestFactory.makeProductItem
import com.jmc.myshoppingcart.presentation.contract.DetailContract
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
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: DetailViewModel
    private lateinit var repository: DataRepository

    private val fakeProduct = makeProductItem()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk(relaxed = true)
        viewModel = DetailViewModel(repository, SavedStateHandle())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should be loading`() {
        val state = viewModel.viewState.value
        assertTrue(state.isLoading)
        assertNull(state.productItem)
        assertTrue(state.cart.isEmpty())
    }

    @Test
    fun `getDetailProduct updates state with product`() = runTest {
        coEvery { repository.getProduct(1) } returns flowOf(fakeProduct)

        viewModel.handleEvents(DetailContract.Event.GetDetailProduct(1))
        advanceUntilIdle()

        val state = viewModel.viewState.value
        assertFalse(state.isLoading)
        assertEquals(fakeProduct, state.productItem)
    }

    @Test
    fun `addToCart updates cart correctly`() = runTest {
        coEvery { repository.getValueByCache() } returns flowOf(mutableMapOf())
        coEvery { repository.saveInCache(any()) } just Runs

        viewModel.handleEvents(DetailContract.Event.AddToCart(1))
        advanceUntilIdle()

        val state = viewModel.viewState.value
        assertEquals(1, state.cart[1])
        verify { repository.saveInCache(match { it[1] == 1 }) }
    }

    @Test
    fun `addToCart increases quantity if product already exists`() = runTest {
        val initialCart = mutableMapOf(1 to 2)
        coEvery { repository.getValueByCache() } returns flowOf(initialCart)
        coEvery { repository.saveInCache(any()) } just Runs

        viewModel.handleEvents(DetailContract.Event.AddToCart(1))
        advanceUntilIdle()

        val state = viewModel.viewState.value
        assertEquals(3, state.cart[1])
        verify { repository.saveInCache(match { it[1] == 3 }) }
    }
}
