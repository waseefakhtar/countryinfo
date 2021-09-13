package com.waseefakhtar.countryinfo.countrydetail.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.scope.MainCoroutineScopeRule
import com.waseefakhtar.countryinfo.*
import com.waseefakhtar.countryinfo.countrydetail.usecase.GetCountryDetailUseCase
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito
import java.lang.RuntimeException

class CountryDetailViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get: Rule
    val mainCoroutineScopeRule: MainCoroutineScopeRule = MainCoroutineScopeRule()

    private val countryDetailState = mutableListOf<CountryDetailViewModel.CountryDetailState>()

    private val getCountryDetailUseCase: GetCountryDetailUseCase = mock()
    private val countryDetailViewModel = CountryDetailViewModel(getCountryDetailUseCase)

    @Before
    fun setUp() {
        countryDetailState.clear()
        observeViewModel(countryDetailViewModel)
        Mockito.reset(getCountryDetailUseCase)
    }

    @Test
    fun `Should show all countries successfully`() = mainCoroutineScopeRule.runBlockingTest {
        val country = randomString()
        val countryDetail = createCountryDetailResponse().toCountryDetail()
        val expectedStates = listOf(CountryDetailViewModel.CountryDetailState.Loading, CountryDetailViewModel.CountryDetailState.Success(countryDetail))
        When calling getCountryDetailUseCase.getCountryDetail(country) doReturn countryDetail

        countryDetailViewModel.onLoadDetail(country)

        countryDetailState.`should equal`(expectedStates)
        Verify on getCountryDetailUseCase that getCountryDetailUseCase.getCountryDetail(country) was called
        `Verify no further interactions` on getCountryDetailUseCase
    }

    @Test
    fun `Should show error when trying to get all countries`() = mainCoroutineScopeRule.runBlockingTest {
        val country = randomString()
        val exception = RuntimeException()
        val expectedStates = listOf(CountryDetailViewModel.CountryDetailState.Loading, CountryDetailViewModel.CountryDetailState.Error(R.string.something_went_wrong))
        When calling getCountryDetailUseCase.getCountryDetail(country) doThrow exception

        countryDetailViewModel.onLoadDetail(country)

        countryDetailState.`should equal`(expectedStates)
        Verify on getCountryDetailUseCase that getCountryDetailUseCase.getCountryDetail(country) was called
        `Verify no further interactions` on getCountryDetailUseCase
    }

    private fun observeViewModel(viewModel: CountryDetailViewModel) {
        viewModel.countryDetailState().observeForever { state -> countryDetailState.add(state) }
    }
}