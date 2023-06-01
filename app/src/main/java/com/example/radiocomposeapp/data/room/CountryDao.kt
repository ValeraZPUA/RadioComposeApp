package com.example.radiocomposeapp.data.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CountryDao {
    @Query("SELECT * FROM countries")
    fun getAllCountries(): Flow<List<Country>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountry(country: Country)

    @Delete
    suspend fun deleteCountry(country: Country)

    @Query("SELECT COUNT(*) FROM countries")
    fun getCountOfCountries(): Flow<Int>
}