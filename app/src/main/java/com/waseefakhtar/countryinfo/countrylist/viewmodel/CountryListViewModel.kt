package com.waseefakhtar.countryinfo.countrylist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waseefakhtar.countryinfo.R
import com.waseefakhtar.countryinfo.countrylist.usecase.GetAllCountriesUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class CountryListViewModel @Inject constructor(
    private val getAllCountriesUseCase: GetAllCountriesUseCase
)  : ViewModel() {

    fun countriesState(): LiveData<CountriesState> = countriesState
    private val countriesState: MutableLiveData<CountriesState> = MutableLiveData()

    fun onLoad() {
        countriesState.value = CountriesState.Loading
        viewModelScope.launch {
            try {
                countriesState.value = CountriesState.Success(getAllCountriesUseCase.getAllCountries())
            } catch (throwable: Throwable) {
                countriesState.value = CountriesState.Error(R.string.something_went_wrong)
            }
        }
    }

    sealed class CountriesState {
        object Loading : CountriesState()
        data class Success(val countries: List<String>) : CountriesState()
        data class Error(val message: Int) : CountriesState()
    }
}