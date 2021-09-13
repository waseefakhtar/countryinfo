package com.waseefakhtar.countryinfo.countrydetail.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import com.waseefakhtar.countryinfo.R
import com.waseefakhtar.countryinfo.ViewModelFactory
import com.waseefakhtar.countryinfo.countrydetail.viewmodel.CountryDetailViewModel
import com.waseefakhtar.countryinfo.countrylist.viewmodel.CountryListViewModel
import com.waseefakhtar.countryinfo.data.CountryDetail
import com.waseefakhtar.countryinfo.databinding.ActivityCountryDetailBinding
import com.waseefakhtar.countryinfo.databinding.ActivityMainBinding
import com.waseefakhtar.countryinfo.util.Utils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CountryDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCountryDetailBinding
    private var country = ""

    private lateinit var viewModel: CountryDetailViewModel
    @Inject lateinit var vmFactory: ViewModelFactory<CountryDetailViewModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountryDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initCountry()
        initViewModel()
        initViews()
        onObserve()
    }

    private fun initCountry() {
        intent?.let {
            country = it.getStringExtra(COUNTRY_ARG) ?: ""
        } ?: run {
            Log.e("CountryDetailActivity", "Error: Starting CountryDetailActivity with no selected country.")
            onBackPressed()
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, vmFactory).get(CountryDetailViewModel::class.java)
    }

    private fun initViews() {
        supportActionBar?.title = country
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.show()
    }

    private fun onObserve() {
        viewModel.countryDetailState().observe(this, { countryDetailState -> refreshState(countryDetailState) })
        viewModel.onLoadDetail(country)
    }

    private fun refreshState(countryDetailState: CountryDetailViewModel.CountryDetailState) {
        when (countryDetailState) {
            is CountryDetailViewModel.CountryDetailState.Loading -> binding.progressBar.visibility = View.VISIBLE
            is CountryDetailViewModel.CountryDetailState.Success -> populateViews(countryDetailState.countryDetail)
            is CountryDetailViewModel.CountryDetailState.Error -> {
                Toast.makeText(this, countryDetailState.message, Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    private fun populateViews(countryDetail: CountryDetail) {
        with(binding) {
            progressBar.visibility = View.GONE

            capitalValue.text = countryDetail.capital
            populationValue.text = countryDetail.population
            callingCodesValue.text = countryDetail.callingCodes
            timezonesValue.text = countryDetail.timezones

            Utils.fetchSvg(this@CountryDetailActivity, countryDetail.flag, imageView)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object {

        const val COUNTRY_ARG = "arg_country"

        @JvmStatic
        fun createIntent(context: Context, country: String): Intent =
            Intent(context, CountryDetailActivity::class.java).apply {
                putExtra(COUNTRY_ARG, country)
            }
    }
}