package com.waseefakhtar.countryinfo.countrydetail.usecase

import android.util.Log
import com.waseefakhtar.countryinfo.api.CountriesAPIClient
import com.waseefakhtar.countryinfo.api.CountryDetailResponse
import com.waseefakhtar.countryinfo.coroutines.DispatcherProvider
import com.waseefakhtar.countryinfo.data.CountryDetail
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCountryDetailUseCase @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val countriesAPIClient: CountriesAPIClient
) {

    @Throws(Throwable::class)
    suspend fun getCountryDetail(country: String): CountryDetail {
        return withContext(dispatcherProvider.io()) {
            countriesAPIClient.getCountryDetail(country).toCountryDetail()
        }
    }

    private fun CountryDetailResponse.toCountryDetail(): CountryDetail =
        CountryDetail(
            name = name,
            capital = capital,
            population = population,
            flag = flag
        )

}