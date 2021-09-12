package com.waseefakhtar.countryinfo.main.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.waseefakhtar.countryinfo.R
import com.waseefakhtar.countryinfo.countrydetail.view.CountryDetailActivity
import com.waseefakhtar.countryinfo.countrylist.view.CountryListActivity
import com.waseefakhtar.countryinfo.databinding.ActivityMainBinding

const val SELECTED_COUNTRY = "arg_selected_country"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var activityResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        registerForActivityResult()
        initViews()
    }

    private fun initViews() {
        supportActionBar?.hide()
        binding.countryPickerView.setOnClickListener { openCountryList() }
        binding.mainCta.setOnClickListener {
            val countryPicked = binding.countryPickerView.text as String
            when (countryPicked == getString(R.string.pick_country)) {
                true -> Toast.makeText(this, R.string.select_country, Toast.LENGTH_LONG).show()
                false -> startActivity(CountryDetailActivity.createIntent(this, countryPicked))
            }
        }
    }

    private fun registerForActivityResult() {
        activityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let {
                    val clickedCountry = it.getStringExtra(SELECTED_COUNTRY)
                    binding.countryPickerView.text = clickedCountry
                }
            }
        }
    }

    private fun openCountryList() {
        activityResult.launch(Intent(this, CountryListActivity::class.java))
    }
}