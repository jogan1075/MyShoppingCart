package com.jmc.myshoppingcart.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.jmc.myshoppingcart.data.DataRepository
import com.jmc.myshoppingcart.data.model.ProductItem
import com.jmc.myshoppingcart.factory.TestFactory.makeProductItemList
import com.jmc.myshoppingcart.presentation.contract.SearchContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule


@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var repository: DataRepository

    private lateinit var viewModel: SearchViewModel

    private val savedStateHandle = SavedStateHandle()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = SearchViewModel(repository, savedStateHandle)
    }

    @Test
    fun `should initialize with loading state`() {
        val state = viewModel.viewState.value

        assertTrue(state.isLoading)
        assertTrue(state.products.isEmpty())
        assertTrue(state.categories.isEmpty())
    }


    @Test
    fun `should handle empty product list when no products found`() = runTest {

        val category = "nonexistent_category"
        val expectedProducts = emptyList<ProductItem>()
        `when`(repository.getProductsByCategory(category)).thenReturn(flowOf(expectedProducts))

        viewModel.handleEvents(SearchContract.Event.SearchProductByCategory(category))

        val state = viewModel.viewState.value
        assertTrue(state.products.isEmpty())
    }

    @Test
    fun `should handle error during product fetch`() = runTest {
        val category = "electronics"
        `when`(repository.getProductsByCategory(category)).thenThrow(RuntimeException("Error fetching products"))

        viewModel.handleEvents(SearchContract.Event.SearchProductByCategory(category))

        val state = viewModel.viewState.value
        assertTrue(state.isLoading)
        assertTrue(state.products.isEmpty())
    }
}
