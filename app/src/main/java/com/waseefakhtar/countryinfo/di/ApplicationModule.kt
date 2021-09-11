package com.waseefakhtar.countryinfo.di

import android.content.Context
import com.waseefakhtar.countryinfo.CountryInfoApplication
import com.waseefakhtar.countryinfo.api.APIBase
import com.waseefakhtar.countryinfo.api.CountriesEndpoint
import com.waseefakhtar.countryinfo.api.EndpointFactory
import com.waseefakhtar.countryinfo.coroutines.DefaultDispatcherProvider
import com.waseefakhtar.countryinfo.coroutines.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ApplicationModule {

    @Singleton
    @Provides
    fun provideContext(@ApplicationContext application: Context): Context = application

    @Singleton
    @Provides
    fun provideEndpointFactory(context: Context) = EndpointFactory(APIBase.baseUrl, context.externalCacheDir ?: context.cacheDir, true)

    @Singleton
    @Provides
    fun provideCountriesEndpoint(endpointFactory: EndpointFactory): CountriesEndpoint = endpointFactory.create(CountriesEndpoint::class.java)

    @Singleton
    @Provides
    fun provideDispatcherProvider(): DispatcherProvider = DefaultDispatcherProvider()
}