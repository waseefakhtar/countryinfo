package com.waseefakhtar.countryinfo

import com.waseefakhtar.countryinfo.api.CountryDetailResponse
import com.waseefakhtar.countryinfo.api.CountryNameResponse
import com.waseefakhtar.countryinfo.data.CountryDetail
import java.util.concurrent.ThreadLocalRandom

private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
private val random
    get() = ThreadLocalRandom.current()

fun randomString(size: Int = 20): String = (0..size)
    .map { charPool[random.nextInt(0, charPool.size)] }
    .joinToString("")

fun createCountryNameResponseList(): List<CountryNameResponse> =
    listOf(
        CountryNameResponse(
            name = randomString()
        )
    )

fun List<CountryNameResponse>.toCountries(): List<String> = map { it.name }

fun createCountryDetailResponse() =
    CountryDetailResponse(
        name = randomString(),
        capital = randomString(),
        population = randomString(),
        callingCodes = listOf(randomString()),
        timezones = listOf(randomString()),
        flag = randomString()
    )

fun CountryDetailResponse.toCountryDetail(): CountryDetail =
    CountryDetail(
        name = name,
        capital = capital,
        population = population,
        callingCodes = callingCodes.joinToString(", "),
        timezones = timezones.joinToString(", "),
        flag = flag
    )