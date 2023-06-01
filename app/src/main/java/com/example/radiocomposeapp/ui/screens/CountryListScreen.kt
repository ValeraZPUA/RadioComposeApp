package com.example.radiocomposeapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.radiocomposeapp.R
import com.example.radiocomposeapp.data.room.Country
import com.example.radiocomposeapp.ui.viewmodels.CountryViewModel

@Composable
fun CountryListItem(
    country: Country,
    onCountrySelected: (Country) -> Unit,
    onCountryUnselected: (Country) -> Unit
) {
    val isChecked = remember {
        mutableStateOf(country.isSelected)
    }

    Card(
       modifier = Modifier
           .fillMaxSize()
           .padding(top = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Checkbox(
                checked = isChecked.value,
                onCheckedChange = {
                    isChecked.value = it
                    if (it) {
                        onCountrySelected(country)
                    } else {
                        onCountryUnselected(country)
                    }
                }
            )
            Column {
                Text(
                   text = country.name,
                   style = MaterialTheme.typography.body1,
                   modifier = Modifier.padding(start = 8.dp)
                )
                Text(
                   text = stringResource(id = R.string.radio_station_count, country.stationCount),
                   style = MaterialTheme.typography.body2,
                   modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

@Composable
fun CountryList(
    countries: List<Country>,
    onCountrySelected: (Country) -> Unit,
    onCountryUnselected: (Country) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 8.dp)
    ) {
        itemsIndexed(
            countries
        ) {_, country ->
            CountryListItem(
                country = country,
                onCountrySelected = onCountrySelected,
                onCountryUnselected = onCountryUnselected
            )
        }
    }
}

@Composable
fun CountryListScreen(
) {
    val viewModel: CountryViewModel = viewModel()
    val countries by viewModel.countries.collectAsState(emptyList())

    Column(modifier = Modifier.fillMaxSize()) {
        CountryList(
            countries = countries,
            onCountrySelected = { viewModel.selectCountry(it) },
            onCountryUnselected = { viewModel.unselectCountry(it) }
        )
    }
}
