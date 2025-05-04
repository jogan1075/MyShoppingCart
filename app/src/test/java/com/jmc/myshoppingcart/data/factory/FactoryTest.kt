package com.jmc.myshoppingcart.data.factory

import com.jmc.myshoppingcart.data.repository.Cache
import com.jmc.myshoppingcart.data.repository.Remote
import com.nhaarman.mockitokotlin2.mock
import org.junit.Assert.assertNotNull
import org.junit.Test

class FactoryTest {

    private val cache = mock<Cache>()
    private val remote = mock<Remote>()
    private val factory = Factory(cache = cache, remote = remote)

    @Test
    fun `when getCache, then Cache instance not null`() {
        assertNotNull(factory.getCache())
    }

    @Test
    fun `when getRemote, then Remote instance not null`() {
        assertNotNull(factory.getRemote())
    }

}