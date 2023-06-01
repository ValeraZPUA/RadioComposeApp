package com.example.radiocomposeapp.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.compose.runtime.Immutable

@Entity(tableName = "countries")
@Immutable
data class Country(
    @PrimaryKey var countryCode: String,
    var name: String,
    var stationCount: Int,
    var isSelected: Boolean
)