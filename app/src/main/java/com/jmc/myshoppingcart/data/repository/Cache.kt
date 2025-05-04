package com.jmc.myshoppingcart.data.repository

interface Cache {

    fun <T> saveValueInCache(key: String, value: T)

    fun <T> getValueByCache(key: String, default: T): T

    fun clearValuesCache(key: String)

}