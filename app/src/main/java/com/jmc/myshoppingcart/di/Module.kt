package com.jmc.myshoppingcart.di

import android.content.Context
import com.jmc.core.dataLocal.CacheManager
import com.jmc.core.network.RetrofitWebServiceFactory
import com.jmc.myshoppingcart.BuildConfig
import com.jmc.myshoppingcart.data.DataRepository
import com.jmc.myshoppingcart.data.factory.Factory
import com.jmc.myshoppingcart.data.local.CacheImpl
import com.jmc.myshoppingcart.data.remote.ApiService
import com.jmc.myshoppingcart.data.remote.RemoteImpl
import com.jmc.myshoppingcart.data.repository.Cache
import com.jmc.myshoppingcart.data.repository.Remote
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Module {

    @Provides
    @Singleton
    fun provideApiService(): ApiService =
        RetrofitWebServiceFactory<ApiService>().create(
            isDebug = BuildConfig.DEBUG,
            tClass = ApiService::class.java,
            baseUrl = "https://fakestoreapi.com/"
        )

    @Provides
    @Singleton
    fun provideCacheManager(@ApplicationContext context: Context): CacheManager {
        return CacheManager(context, "PREFS")
    }

    @Provides
    fun bindSourceFactory(remote: Remote, cache: Cache) = Factory(remote, cache)


    @Provides
    fun provideRemoteImpl(remoteImpl: RemoteImpl):Remote = remoteImpl

    @Provides
    fun bindDataRepository(factory: Factory) = DataRepository(factory)


    @Provides
    fun bindsCacheImpl(cacheImp: CacheImpl): Cache = cacheImp


}