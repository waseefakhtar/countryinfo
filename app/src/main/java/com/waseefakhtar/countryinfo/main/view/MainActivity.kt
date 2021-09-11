package com.waseefakhtar.countryinfo.main.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.waseefakhtar.countryinfo.R
import com.waseefakhtar.countryinfo.countrylist.view.CountryListActivity
import com.waseefakhtar.countryinfo.databinding.ActivityCountryListBinding
import com.waseefakhtar.countryinfo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

const val SELECTED_COUNTRY = "arg_selected_country"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var countryPickerView: TextView
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
        countryPickerView = findViewById(R.id.countryPickerView)
        countryPickerView.setOnClickListener { openCountryList() }
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