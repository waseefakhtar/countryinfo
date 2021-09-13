package com.waseefakhtar.countryinfo.countrylist.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.reset
import com.scope.MainCoroutineScopeRule
import com.waseefakhtar.countryinfo.api.CountriesAPIClient
import com.waseefakhtar.countryinfo.api.CountryNameResponse
import com.waseefakhtar.countryinfo.coroutines.DispatcherProvider
import com.waseefakhtar.countryinfo.randomString
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.lang.RuntimeException

class GetAllCountriesUseCaseTest  {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get: Rule
    val mainCoroutineScopeRule: MainCoroutineScopeRule = MainCoroutineScopeRule()

    private val dispatcherProvider: DispatcherProvider = mock()
    private val countriesAPIClient: CountriesAPIClient = mock()
    private val getAllCountriesUseCase: GetAllCountriesUseCase = GetAllCountriesUseCase(dispatcherProvider, countriesAPIClient)

    @Before
    fun setUp() {
        reset(countriesAPIClient, dispatcherProvider)
        When calling dispatcherProvider.io() doReturn mainCoroutineScopeRule.testDispatcher
    }

    @Test
    fun `Should get all country list successfully`() = mainCoroutineScopeRule.runBlockingTest {
        val countryNameResponseList = createCountryNameResponseList()
        When calling countriesAPIClient.getAllCountries() doReturn countryNameResponseList

        val result = getAllCountriesUseCase.getAllCountries()

        result.`should equal`(countryNameResponseList.toCountries())
        Verify on countriesAPIClient that countriesAPIClient.getAllCountries() was called
        `Verify no further interactions` on countriesAPIClient
    }

    @Test
    fun `Should throw error when trying to get all country list`() = mainCoroutineScopeRule.runBlockingTest {
        val exception = RuntimeException()
        When calling countriesAPIClient.getAllCountries() doThrow exception

        val result = try {
            getAllCountriesUseCase.getAllCountries()
            true
        } catch (throwable: Throwable) {
            false
        }

        result.`should be false`()
        Verify on countriesAPIClient that countriesAPIClient.getAllCountries() was called
        `Verify no further interactions` on countriesAPIClient
    }

    private fun createCountryNameResponseList(): List<CountryNameResponse> =
        listOf(
            CountryNameResponse(
                name = randomString()
            )
        )

    private fun List<CountryNameResponse>.toCountries(): List<String> = map { it.name }
}