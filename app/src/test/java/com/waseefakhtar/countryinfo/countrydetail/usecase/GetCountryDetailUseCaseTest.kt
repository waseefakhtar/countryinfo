package com.waseefakhtar.countryinfo.countrydetail.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.reset
import com.scope.MainCoroutineScopeRule
import com.waseefakhtar.countryinfo.api.CountriesAPIClient
import com.waseefakhtar.countryinfo.api.CountryDetailResponse
import com.waseefakhtar.countryinfo.coroutines.DispatcherProvider
import com.waseefakhtar.countryinfo.data.CountryDetail
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.lang.RuntimeException
import java.util.concurrent.ThreadLocalRandom

class GetCountryDetailUseCaseTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get: Rule
    val mainCoroutineScopeRule: MainCoroutineScopeRule = MainCoroutineScopeRule()

    private val dispatcherProvider: DispatcherProvider = mock()
    private val countriesAPIClient: CountriesAPIClient = mock()
    private val getCountryDetailUseCase: GetCountryDetailUseCase = GetCountryDetailUseCase(dispatcherProvider, countriesAPIClient)

    @Before
    fun setUp() {
        reset(countriesAPIClient, dispatcherProvider)
        When calling dispatcherProvider.io() doReturn mainCoroutineScopeRule.testDispatcher
    }

    @Test
    fun `Should get country detail successfully`() = mainCoroutineScopeRule.runBlockingTest {
        val country = randomString()
        val countryDetailResponse = createCountryDetailResponse()
        When calling countriesAPIClient.getCountryDetail(country) doReturn countryDetailResponse

        val result = getCountryDetailUseCase.getCountryDetail(country)

        result.`should equal`(countryDetailResponse.toCountryDetail())
        Verify on countriesAPIClient that countriesAPIClient.getCountryDetail(country) was called
        `Verify no further interactions` on countriesAPIClient
    }

    @Test
    fun `Should throw error when trying to get country detail`() = mainCoroutineScopeRule.runBlockingTest {
        val country = randomString()
        val exception = RuntimeException()
        When calling countriesAPIClient.getCountryDetail(country) doThrow exception

        val result = try {
            getCountryDetailUseCase.getCountryDetail(country)
            true
        } catch (throwable: Throwable) {
            false
        }

        result.`should be false`()
        Verify on countriesAPIClient that countriesAPIClient.getCountryDetail(country) was called
        `Verify no further interactions` on countriesAPIClient
    }

    private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    private val random
        get() = ThreadLocalRandom.current()
    private fun randomString(size: Int = 20): String = (0..size)
        .map { charPool[random.nextInt(0, charPool.size)] }
        .joinToString("")

    private fun createCountryDetailResponse() =
        CountryDetailResponse(
            name = randomString(),
            capital = randomString(),
            population = randomString(),
            flag = randomString()
        )

    private fun CountryDetailResponse.toCountryDetail(): CountryDetail =
        CountryDetail(
            name = name,
            capital = capital,
            population = population,
            flag = flag
        )
}