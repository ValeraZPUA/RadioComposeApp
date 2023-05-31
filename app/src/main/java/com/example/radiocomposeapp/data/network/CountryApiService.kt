package com.example.radiocomposeapp.data.network

import retrofit2.http.GET
import retrofit2.http.Query

interface CountryApiService {

    @GET("countries")
    suspend fun getCountries(@Query("hidebroken") hideBroken: Boolean = true): List<CountryResponse>

}