package com.example.radiocomposeapp.data.repository

import com.example.radiocomposeapp.data.network.CountryApiService
import com.example.radiocomposeapp.data.network.CountryResponse
import com.example.radiocomposeapp.data.room.Country
import com.example.radiocomposeapp.data.room.CountryDao
import kotlinx.coroutines.flow.*
import java.util.*
import javax.inject.Inject

class CountryRepository @Inject constructor(
    private val countryApi: CountryApiService,
    private val countryDao: CountryDao
) {

    fun getCountries(): Flow<List<Country>> = flow {
        val countriesFromApi = countryApi.getCountries().toCountryList()
        val selectedCountryCodes = countryDao.getAllCountries().first().map { it.countryCode }

        countriesFromApi.forEach { it.isSelected = it.countryCode in selectedCountryCodes }

        emit(countriesFromApi.sortedByDescending { it.isSelected })
    }

    suspend fun setSelectedCountry(country: Country) {
        countryDao.insertCountry(country)
    }

    suspend fun removeSelectedCountry(country: Country) {
        countryDao.deleteCountry(country)
    }

    fun getCountOfSelectedCountries(): Flow<Int> {
        return countryDao.getCountOfCountries()
    }

    private fun List<CountryResponse>.toCountryList(): List<Country> {
        return this.map { response ->
            Country(
                name = response.countryCode?.let { Locale("", it).displayCountry } ?: "",
                countryCode = response.countryCode ?: "",
                stationCount = response.stationCount ?: 0,
                isSelected = false
            )
        }
    }
}
