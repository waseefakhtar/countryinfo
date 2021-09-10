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
        supportActionBar?.hide()
        countryPickerView = findViewById(R.id.countryPickerView)
        countryPickerView.setOnClickListener { openCountryList() }
    }

    private fun registerForActivityResult() {
        activityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                //  you will get result here in result.data
            }
        }
    }

    private fun openCountryList() {
        activityResult.launch(Intent(this, CountryListActivity::class.java))
    }
}