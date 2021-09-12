package com.waseefakhtar.countryinfo.countrydetail.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.waseefakhtar.countryinfo.R
import com.waseefakhtar.countryinfo.databinding.ActivityCountryDetailBinding
import com.waseefakhtar.countryinfo.databinding.ActivityMainBinding

class CountryDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCountryDetailBinding
    private var country = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountryDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        intent?.let {
            country = it.getStringExtra(COUNTRY_ARG) ?: ""
            Log.i("CountryDetail", "is run: ${country}")
        } ?: run {
            Log.e("CountryDetailActivity", "Error: Starting CountryDetailActivity with no selected country.")
            onBackPressed()
        }

        initViews()
    }

    private fun initViews() {
        supportActionBar?.title = country
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.show()
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