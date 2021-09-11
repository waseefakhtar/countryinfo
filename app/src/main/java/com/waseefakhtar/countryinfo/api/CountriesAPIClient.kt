package com.waseefakhtar.countryinfo.api

import android.util.Log
import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.io.IOException
import javax.inject.Inject

class CountriesAPIClient @Inject constructor(
    private val countriesEndpoint: CountriesEndpoint
) {

    @Throws(IOException::class, Exception::class)
    suspend fun getAllCountries(): List<String> = countriesEndpoint.getAllCountries().toNames()
}

private fun List<CountryNameResponse>.toNames(): List<String> = this.map { it.name }

interface CountriesEndpoint {

    @GET("all?fields=name")
    suspend fun getAllCountries(): List<CountryNameResponse>
}

data class CountryNameResponse(
    @SerializedName("name") val name: String
)