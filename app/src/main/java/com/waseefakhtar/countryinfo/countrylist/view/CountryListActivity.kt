package com.waseefakhtar.countryinfo.countrylist.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.waseefakhtar.countryinfo.R
import com.waseefakhtar.countryinfo.ViewModelFactory
import com.waseefakhtar.countryinfo.countrylist.adapter.CountriesAdapter
import com.waseefakhtar.countryinfo.countrylist.viewmodel.CountryListViewModel
import com.waseefakhtar.countryinfo.databinding.ActivityCountryListBinding
import com.waseefakhtar.countryinfo.main.view.SELECTED_COUNTRY
import dagger.hilt.android.AndroidEntryPoint
import me.zhanghai.android.fastscroll.FastScrollerBuilder
import me.zhanghai.android.fastscroll.PopupTextProvider
import javax.inject.Inject

@AndroidEntryPoint
class CountryListActivity : AppCompatActivity() {

    private val adapter: CountriesAdapter by lazy { CountriesAdapter(layoutInflater, ::onCountryClick) }

    private lateinit var binding: ActivityCountryListBinding
    private lateinit var viewModel: CountryListViewModel
    @Inject lateinit var vmFactory: ViewModelFactory<CountryListViewModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountryListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initViewModel()
        initViews()
        onObserve()
    }

    private fun onCountryClick(country: String) {
        val intent = Intent()
        intent.putExtra(SELECTED_COUNTRY, country)
        setResult(RESULT_OK, intent)
        onBackPressed()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, vmFactory).get(CountryListViewModel::class.java)
    }

    private fun initViews() {
        supportActionBar?.setTitle(R.string.pick_country)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.show()

        binding.recyclerView.adapter = adapter
        FastScrollerBuilder(binding.recyclerView)
            .setPopupTextProvider { adapter.getPopupText(it) }
            .build()
    }

    private fun onObserve() {
        viewModel.countriesState().observe(this, { countriesState -> refreshState(countriesState) })
        viewModel.onLoad()
    }

    private fun refreshState(countriesState: CountryListViewModel.CountriesState) {
        when (countriesState) {
            is CountryListViewModel.CountriesState.Loading -> binding.progressBar.visibility = View.VISIBLE
            is CountryListViewModel.CountriesState.Success -> {
                binding.progressBar.visibility = View.GONE
                adapter.add(countriesState.countries)
            }
            is CountryListViewModel.CountriesState.Error -> {
                Toast.makeText(this, countriesState.message, Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}