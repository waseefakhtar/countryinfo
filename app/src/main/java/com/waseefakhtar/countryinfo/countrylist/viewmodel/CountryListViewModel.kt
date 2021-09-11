package com.waseefakhtar.countryinfo.countrylist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waseefakhtar.countryinfo.countrylist.usecase.GetAllCountriesUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class CountryListViewModel @Inject constructor(
    private val getAllCountriesUseCase: GetAllCountriesUseCase
)  : ViewModel() {

    init {
        viewModelScope.launch {
            val countries = getAllCountriesUseCase.getAllCountries()
        }
    }
}