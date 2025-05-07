package com.jmc.myshoppingcart.presentation

import androidx.lifecycle.SavedStateHandle
import com.jmc.myshoppingcart.data.DataRepository
import com.jmc.myshoppingcart.factory.TestFactory.makeProductItemList
import com.jmc.myshoppingcart.presentation.contract.HomeContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.anyMap
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var repository: DataRepository

    private lateinit var viewModel: HomeViewModel

    private val fakeCart = mutableMapOf(1 to 2, 2 to 1)
    private val fakeProducts = makeProductItemList()
    private val fakeCategories = listOf("electronics", "jewelery")

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        `when`(repository.getValueByCache()).thenReturn(flowOf(fakeCart))
        `when`(repository.getProducts()).thenReturn(flowOf(fakeProducts))
        `when`(repository.getCategories()).thenReturn(flowOf(fakeCategories))

        viewModel = HomeViewModel(repository, SavedStateHandle())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init loads cart from cache`() = runTest {
        advanceUntilIdle()
        val state = viewModel.viewState.value
        Assert.assertEquals(fakeCart, state.cart)
        Assert.assertTrue(state.isLoading)
    }

    @Test
    fun `addToCart adds product to cart and updates state`() = runTest {
        advanceUntilIdle()
        viewModel.handleEvents(HomeContract.Event.AddToCart(1))
        advanceUntilIdle()

        val updated = viewModel.viewState.value.cart[1]
        Assert.assertEquals(3, updated)
        verify(repository).saveInCache(anyMap())
    }

    @Test
    fun `initValues loads categories and products`() = runTest {
        viewModel.handleEvents(HomeContract.Event.InitValues)
        advanceUntilIdle()

        val state = viewModel.viewState.value
        Assert.assertEquals(false, state.isLoading)
        Assert.assertEquals(fakeCategories, state.categories)
        Assert.assertEquals(fakeProducts, state.products)
    }
}
