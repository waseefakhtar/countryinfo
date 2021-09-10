package com.waseefakhtar.countryinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.waseefakhtar.countryinfo.countrylist.viewmodel.CountryListViewModel

class ViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CountryListViewModel::class.java)) {
            return CountryListViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}