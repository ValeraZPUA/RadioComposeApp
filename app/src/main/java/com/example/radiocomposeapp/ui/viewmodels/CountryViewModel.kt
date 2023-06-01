package com.example.radiocomposeapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.radiocomposeapp.data.repository.CountryRepository
import com.example.radiocomposeapp.data.room.Country
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryViewModel @Inject constructor(
    private val repository: CountryRepository
) : ViewModel() {

    val countries: Flow<List<Country>> = repository.getCountries()

    fun selectCountry(country: Country) {
        country.isSelected = true
        viewModelScope.launch {
            repository.setSelectedCountry(country)
        }
    }

    fun unselectCountry(country: Country) {
        country.isSelected = false
        viewModelScope.launch {
            repository.removeSelectedCountry(country)
        }
    }
}