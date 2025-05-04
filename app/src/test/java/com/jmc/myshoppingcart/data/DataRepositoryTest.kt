package com.jmc.myshoppingcart.data

import com.jmc.myshoppingcart.data.factory.Factory
import com.jmc.myshoppingcart.data.model.ProductItem
import com.jmc.myshoppingcart.data.repository.Cache
import com.jmc.myshoppingcart.data.repository.Remote
import com.jmc.myshoppingcart.factory.TestFactory.makeListString
import com.jmc.myshoppingcart.factory.TestFactory.makeProductItem
import com.jmc.myshoppingcart.factory.TestFactory.makeProductItemList
import com.jmc.myshoppingcart.utils.RandomFactory
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class DataRepositoryTest {

    private val remote = mockk<Remote>()
    private val cache = mockk<Cache>()
    private val factory = Factory(remote,cache)

    private val repository = DataRepository(
        factory
    )

    private fun stubGetProducts(response: List<ProductItem>) {
        coEvery { remote.getProducts() } returns response
    }

    private fun stubGetCategories(response: List<String>) {
        coEvery { remote.getCategories() } returns response
    }

    private fun stubGetProduct(value: Int,response: ProductItem) {
        coEvery { remote.getProduct(value) } returns response
    }

    private fun stubGetProductsByCategory(value: String,response: List<ProductItem>) {
        coEvery { remote.getProductsByCategory(value) } returns response
    }

    @Test
    fun `when getProducts, then return data`() = runBlocking {
        val dataMock = makeProductItemList()
        stubGetProducts(dataMock)

        val flow = repository.getProducts()

        flow.collect { result ->
            assertEquals(dataMock, result)
        }

        coVerify { remote.getProducts() }
    }

    @Test
    fun `when getCategories, then return data`() = runBlocking {
        val dataMock = makeListString(2)
        stubGetCategories(dataMock)

        val flow = repository.getCategories()

        flow.collect { result ->
            assertEquals(dataMock, result)
        }

        coVerify { remote.getCategories() }
    }

    @Test
    fun `when getProduct, then return data`() = runBlocking {
        val dataMock = makeProductItem()
        val value = RandomFactory.generateInt()
        stubGetProduct(value,dataMock)

        val flow = repository.getProduct(value)

        flow.collect { result ->
            assertEquals(dataMock, result)
        }

        coVerify { remote.getProduct(value) }
    }

    @Test
    fun `when getProductsByCategory, then return data`() = runBlocking {
        val dataMock = makeProductItemList()
        val value = RandomFactory.generateString()
        stubGetProductsByCategory(value,dataMock)

        val flow = repository.getProductsByCategory(value)

        flow.collect { result ->
            assertEquals(dataMock, result)
        }

        coVerify { remote.getProductsByCategory(value) }
    }
}