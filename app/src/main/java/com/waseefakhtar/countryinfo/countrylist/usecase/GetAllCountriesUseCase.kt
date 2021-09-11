package com.waseefakhtar.countryinfo.countrylist.usecase

import com.waseefakhtar.countryinfo.api.CountriesAPIClient
import com.waseefakhtar.countryinfo.coroutines.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAllCountriesUseCase @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val countriesAPIClient: CountriesAPIClient
) {

    @Throws(Throwable::class)
    suspend fun getAllCountries(): List<String> {
        return withContext(dispatcherProvider.io()) {
            countriesAPIClient.getAllCountries()
        }
    }
}