package com.example.recyclingapp.dataClasses.ai

import com.example.recyclingapp.dataClasses.ai.dataClasses.OpenFoodFactsResponse
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Defines the endpoints for retrofit to send our get requests!
 */
interface OpenFoodFactsApi {
    @GET("api/v2/product/{barcode}")
    suspend fun getProduct(
        @Path("barcode") barcode: String
    ): OpenFoodFactsResponse
}