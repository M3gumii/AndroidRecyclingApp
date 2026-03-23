package com.example.recyclingapp.dataClasses.ai

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Creates the Retrofit instance and API service - never init retrofit inside fragments!
 * CONNECTS TO THE ACTUAL FOOD FACTS WEBSITE!
 */
object OpenFoodFactsContactor {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://world.openfoodfacts.org/")    //connect to website
        .addConverterFactory(MoshiConverterFactory.create())    //Use moshi for data class conversion from JSON.
        .build()

    val api: OpenFoodFactsApi = retrofit.create(OpenFoodFactsApi::class.java)
}