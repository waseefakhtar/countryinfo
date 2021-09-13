package com.waseefakhtar.countryinfo.countrylist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.waseefakhtar.countryinfo.R

class CountriesAdapter(
    private val layoutInflater: LayoutInflater,
    private val onCountryClick: (country: String) -> Unit
) : RecyclerView.Adapter<CountryViewHolder>() {
    private var countries = mutableListOf<String>()
    override fun getItemCount(): Int = countries.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) = holder.bind(countries[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder = CountryViewHolder(layoutInflater, parent, onCountryClick)

    fun add(countries: List<String>) {
        this.countries.addAll(countries)
        notifyDataSetChanged()
    }

    fun getPopupText(position: Int): String = countries[position].first().toString()
}

class CountryViewHolder(
    layoutInflater: LayoutInflater,
    parentView: ViewGroup,
    private val onCountryClick: (country: String) -> Unit
) : RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.item_country, parentView, false)) {
    private val textView: TextView = itemView.findViewById(R.id.textView)
    fun bind(country: String) {
        itemView.setOnClickListener { onCountryClick(country) }
        textView.text = country
    }
}