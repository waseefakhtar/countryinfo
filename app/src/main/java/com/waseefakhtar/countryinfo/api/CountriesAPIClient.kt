package com.waseefakhtar.countryinfo.api

import android.util.Log
import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.IOException
import javax.inject.Inject

class CountriesAPIClient @Inject constructor(
    private val countriesEndpoint: CountriesEndpoint
) {

    @Throws(IOException::class, Exception::class)
    suspend fun getAllCountries(): List<CountryNameResponse> = countriesEndpoint.getAllCountries()

    @Throws(IOException::class, Exception::class)
    suspend fun getCountryDetail(country: String): CountryDetailResponse = countriesEndpoint.getCountryDetail(country).first()
}

interface CountriesEndpoint {

    @GET("all?fields=name")
    suspend fun getAllCountries(): List<CountryNameResponse>

    @GET("name/{country}?fulltext=true")
    suspend fun getCountryDetail(@Path("country") country: String): List<CountryDetailResponse>
}

data class CountryNameResponse(
    @SerializedName("name") val name: String
)

data class CountryDetailResponse(
    @SerializedName("name") val name: String,
    @SerializedName("capital") val capital: String,
    @SerializedName("population") val population: String,
    @SerializedName("callingCodes") val callingCodes: List<String>,
    @SerializedName("timezones") val timezones: List<String>,
    @SerializedName("flag") val flag: String
)