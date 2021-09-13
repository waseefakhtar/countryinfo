package com.waseefakhtar.countryinfo.countrylist.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.scope.MainCoroutineScopeRule
import com.waseefakhtar.countryinfo.R
import com.waseefakhtar.countryinfo.countrylist.usecase.GetAllCountriesUseCase
import com.waseefakhtar.countryinfo.createCountryNameResponseList
import com.waseefakhtar.countryinfo.toCountries
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito
import java.lang.RuntimeException

class CountryListViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get: Rule
    val mainCoroutineScopeRule: MainCoroutineScopeRule = MainCoroutineScopeRule()

    private val countriesState = mutableListOf<CountryListViewModel.CountriesState>()

    private val getAllCountriesUseCase: GetAllCountriesUseCase = mock()
    private val countryListViewModel = CountryListViewModel(getAllCountriesUseCase)

    @Before
    fun setUp() {
        countriesState.clear()
        observeViewModel(countryListViewModel)
        Mockito.reset(getAllCountriesUseCase)
    }

    @Test
    fun `Should show all countries successfully`() = mainCoroutineScopeRule.runBlockingTest {
        val countries = createCountryNameResponseList().toCountries()
        val expectedStates = listOf(CountryListViewModel.CountriesState.Loading, CountryListViewModel.CountriesState.Success(countries))
        When calling getAllCountriesUseCase.getAllCountries() doReturn countries

        countryListViewModel.onLoad()

        countriesState.`should equal`(expectedStates)
        Verify on getAllCountriesUseCase that getAllCountriesUseCase.getAllCountries() was called
        `Verify no further interactions` on getAllCountriesUseCase
    }

    @Test
    fun `Should show error when trying to get all countries`() = mainCoroutineScopeRule.runBlockingTest {
        val exception = RuntimeException()
        val expectedStates = listOf(CountryListViewModel.CountriesState.Loading, CountryListViewModel.CountriesState.Error(R.string.something_went_wrong))
        When calling getAllCountriesUseCase.getAllCountries() doThrow exception

        countryListViewModel.onLoad()

        countriesState.`should equal`(expectedStates)
        Verify on getAllCountriesUseCase that getAllCountriesUseCase.getAllCountries() was called
        `Verify no further interactions` on getAllCountriesUseCase
    }

    private fun observeViewModel(viewModel: CountryListViewModel) {
        viewModel.countriesState().observeForever { state -> countriesState.add(state) }
    }
}