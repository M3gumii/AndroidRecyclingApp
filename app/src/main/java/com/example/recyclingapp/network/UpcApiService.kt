package com.example.recyclingapp.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UpcApiService {

    @GET("prod/trial/lookup")
    fun lookupUpc(
        @Query("upc") upc: String
    ): Call<UpcResponse>
}