package com.waseefakhtar.countryinfo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {

    private lateinit var countryPickerView: TextView

    private lateinit var activityResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        registerForActivityResult()
        initViews()
    }

    private fun initViews() {
        Log.i("MainActivity", "openCountryList")
        countryPickerView = findViewById(R.id.countryPickerView)
        countryPickerView.setOnClickListener {
            Log.i("MainActivity", "1 openCountryList")
            openCountryList()
        }
    }

    private fun registerForActivityResult() {
        activityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                //  you will get result here in result.data
            }
        }
    }

    private fun openCountryList() {
        Log.i("MainActivity", "openCountryList")
        activityResult.launch(Intent(this, CountryListActivity::class.java))
    }
}