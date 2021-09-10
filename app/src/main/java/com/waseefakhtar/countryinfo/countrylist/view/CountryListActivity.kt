package com.waseefakhtar.countryinfo.countrylist.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.waseefakhtar.countryinfo.R

class CountryListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_list)
        initViews()
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