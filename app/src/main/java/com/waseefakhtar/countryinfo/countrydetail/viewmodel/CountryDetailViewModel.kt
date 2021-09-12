package com.waseefakhtar.countryinfo.countrydetail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waseefakhtar.countryinfo.R
import com.waseefakhtar.countryinfo.countrydetail.usecase.GetCountryDetailUseCase
import com.waseefakhtar.countryinfo.countrylist.usecase.GetAllCountriesUseCase
import com.waseefakhtar.countryinfo.countrylist.viewmodel.CountryListViewModel
import com.waseefakhtar.countryinfo.data.CountryDetail
import kotlinx.coroutines.launch
import javax.inject.Inject

class CountryDetailViewModel @Inject constructor(
    private val getCountryDetailUseCase: GetCountryDetailUseCase
) : ViewModel() {

    fun countryDetailState(): LiveData<CountryDetailState> = countryDetailState
    private val countryDetailState: MutableLiveData<CountryDetailState> = MutableLiveData()

    fun onLoadDetail(country: String) {
        countryDetailState.value = CountryDetailState.Loading
        viewModelScope.launch {
            try {
                countryDetailState.value = CountryDetailState.Success(getCountryDetailUseCase.getCountryDetail(country))
            } catch (throwable: Throwable) {
                countryDetailState.value = CountryDetailState.Error(R.string.something_went_wrong)
            }
        }
    }

    sealed class CountryDetailState {
        object Loading : CountryDetailState()
        data class Success(val countryDetail: CountryDetail) : CountryDetailState()
        data class Error(val message: Int) : CountryDetailState()
    }
}
