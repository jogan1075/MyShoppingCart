package com.jmc.myshoppingcart.data.local

import com.jmc.core.dataLocal.CacheManager
import com.jmc.myshoppingcart.utils.DefaultValues
import com.jmc.myshoppingcart.utils.RandomFactory
import com.jmc.myshoppingcart.utils.TestCoroutineRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.mockk.Runs
import io.mockk.coJustRun
import io.mockk.every
import io.mockk.just
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CacheImplTest {

    private val cacheManager = mock<CacheManager>()
    private val cacheImpl = CacheImpl(cacheManager)

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private fun stubGetString(value: String, key: String, default: String) {
        whenever(cacheManager.read(key, default))
            .thenReturn(value)
    }

    private fun stubSaveString(key: String, value: String) {
        every { cacheImpl.saveValueInCache(key, value) } just Runs
    }

    @Test
    fun `given random value, when getValueByCache String, then return data`() =
        testCoroutineRule.runBlockingTest {

            val key = RandomFactory.generateString()
            val value = RandomFactory.generateString()
            val default = DefaultValues.emptyString()

            stubGetString(value = value, key = key, default = default)

            val testObserver = cacheImpl.getValueByCache(key, default)

            assertEquals(value, testObserver)
        }

    @Test
    fun `when random value, then saveValueInCache added`() = runBlockingTest {
        val key = RandomFactory.generateString()
        val value = RandomFactory.generateString()
        val default = DefaultValues.emptyString()

        cacheImpl.saveValueInCache(key,value)

        stubGetString(value = value, key = key, default = default)

        val result = cacheImpl.getValueByCache(key, default)

        assertEquals(value, result)
    }

    @Test
    fun `when random value, then clearValuesCache Completed`() = runBlockingTest {
        val key = RandomFactory.generateString()
        val value = RandomFactory.generateString()
        val default = DefaultValues.emptyString()

        cacheImpl.saveValueInCache(key,value)

        cacheImpl.clearValuesCache(key)
        val result = cacheImpl.getValueByCache(key, default)

        assertNull(result)
    }

}