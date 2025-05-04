package com.jmc.myshoppingcart.data.remote

import com.jmc.myshoppingcart.data.model.ProductItem
import com.jmc.myshoppingcart.factory.TestFactory.makeListString
import com.jmc.myshoppingcart.factory.TestFactory.makeProductItem
import com.jmc.myshoppingcart.factory.TestFactory.makeProductItemList
import com.jmc.myshoppingcart.utils.RandomFactory
import com.jmc.myshoppingcart.utils.TestCoroutineRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class RemoteImplTest {

    private val restApi = mock<ApiService>()
    private val remoteImpl = RemoteImpl(restApi)

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private suspend fun stubRestApiGetProducts(
        single :List<ProductItem>
    ) {
        whenever(
            restApi.getAllProducts()
        ).thenReturn(single)
    }

    private suspend fun stubRestApiGetCategories(
        single :List<String>
    ) {
        whenever(
            restApi.getAllCategories()
        ).thenReturn(single)
    }

    private suspend fun stubRestApiGetProductsByCategory(
        value : String,
        single :List<ProductItem>
    ) {
        whenever(
            restApi.getProductsByCategory(value)
        ).thenReturn(single)
    }

    private suspend fun stubRestApiGetProductById(
        value : Int,
        single :ProductItem
    ) {
        whenever(
            restApi.getProductById(value)
        ).thenReturn(single)
    }

    @Test
    fun `given makeProductItemList, when getProducts, then return data`() = testCoroutineRule.runBlockingTest{

        val makeResponse = makeProductItemList()

        stubRestApiGetProducts(
            makeResponse
        )

        val testObserver = remoteImpl.getProducts()

        assertEquals(makeResponse, testObserver)
    }

    @Test
    fun `given makeListString, when getCategories, then return data`() = testCoroutineRule.runBlockingTest{

        val makeResponse = makeListString(2)

        stubRestApiGetCategories(
            makeResponse
        )

        val testObserver = remoteImpl.getCategories()

        assertEquals(makeResponse, testObserver)
    }

    @Test
    fun `given Random String, when getProductsByCategory, then return data`() = testCoroutineRule.runBlockingTest{

        val value = RandomFactory.generateString()
        val makeResponse = makeProductItemList()

        stubRestApiGetProductsByCategory(
            value,
            makeResponse
        )

        val testObserver = remoteImpl.getProductsByCategory(value)

        assertEquals(makeResponse, testObserver)
    }

    @Test
    fun `given Random Int, when getProductById, then return data`() = testCoroutineRule.runBlockingTest{

        val value = RandomFactory.generateInt()
        val makeResponse = makeProductItem()

        stubRestApiGetProductById(
            value,
            makeResponse
        )

        val testObserver = remoteImpl.getProduct(value)

        assertEquals(makeResponse, testObserver)
    }
}