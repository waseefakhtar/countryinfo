package com.waseefakhtar.countryinfo.countrylist.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.waseefakhtar.countryinfo.R
import com.waseefakhtar.countryinfo.ViewModelFactory
import com.waseefakhtar.countryinfo.countrylist.viewmodel.CountryListViewModel
import com.waseefakhtar.countryinfo.databinding.ActivityCountryListBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CountryListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCountryListBinding
    @Inject lateinit var vmFactory: ViewModelFactory<CountryListViewModel>
    private lateinit var viewModel: CountryListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        setContentView(R.layout.activity_country_list)
        initViewModel()
        initViews()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, vmFactory).get(CountryListViewModel::class.java)
    }

    private fun initBinding() {
        binding = ActivityCountryListBinding.inflate(layoutInflater)
    }

    private fun initViews() {
        supportActionBar?.setTitle(R.string.pick_country)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.show()

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}