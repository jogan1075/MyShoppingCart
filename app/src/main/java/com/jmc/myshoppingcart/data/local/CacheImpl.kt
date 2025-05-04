package com.jmc.myshoppingcart.data.local

import com.jmc.core.dataLocal.CacheManager
import com.jmc.myshoppingcart.data.repository.Cache
import javax.inject.Inject
import kotlin.text.clear

class CacheImpl  @Inject constructor(private val cacheManager: CacheManager) : Cache {

    override fun <T> saveValueInCache(key: String, value: T) {
        cacheManager.write(key,value)
    }

    override fun <T> getValueByCache(key: String, default: T): T {
        return cacheManager.read(key,default)
    }

    override fun clearValuesCache(key: String) {
        cacheManager.clear(key)
    }

}