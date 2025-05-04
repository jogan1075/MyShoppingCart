package com.jmc.myshoppingcart.data.factory

import com.jmc.myshoppingcart.data.repository.Cache
import com.jmc.myshoppingcart.data.repository.Remote
import javax.inject.Inject

class Factory @Inject constructor(
    private val remote: Remote,
    private val cache: Cache
) {
    fun getRemote(): Remote = remote
    fun getCache(): Cache = cache
}