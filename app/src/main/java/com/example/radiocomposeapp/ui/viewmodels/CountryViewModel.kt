package com.example.radiocomposeapp.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.radiocomposeapp.data.repository.CountryRepository
import com.example.radiocomposeapp.data.room.Country
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryViewModel @Inject constructor(
    private val repository: CountryRepository
) : ViewModel() {

    val countries: Flow<List<Country>> = repository.getCountries()

    var countryUiState: CountryUiState by mutableStateOf(CountryUiState.Loading)
        private set

    var searchWidgetState by mutableStateOf(SearchWidgetState.CLOSED)
        private set

    var searchTextState by mutableStateOf("")
        private set

    init {
        getCountries()
    }

    fun updateSearchWidgetState(newValue: SearchWidgetState) {
        searchWidgetState = newValue
    }

    fun updateSearchTextState(newValue: String) {
        searchTextState = newValue
    }

    private fun getCountries() {
        viewModelScope.launch {
            countryUiState = CountryUiState.Loading

            countryUiState = try {
                val countries = repository.getCountries().first()
                CountryUiState.Success(countries)
            } catch (e: Exception) {
                CountryUiState.Error
            }
        }
    }

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

sealed class CountryUiState {
    data class Success(val countries: List<Country>) : CountryUiState()
    object Error: CountryUiState()
    object Loading : CountryUiState()
}

enum class SearchWidgetState {
    OPENED,
    CLOSED
}