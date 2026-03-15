package com.example.recyclingapp.dataClasses.ai

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Creates the Retrofit client used to talk to the AI API.
 *
 * Adds the API key to every request and returns an AIRetrofitApi
 * that can send messages to the AI.
 */
object AIContactor {
    private const val BASE_URL = "https://api.openai.com/"

    fun create(apiKey: String): AIRetrofitApi {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $apiKey")
                    .addHeader("Content-Type", "application/json")
                    .build()
                chain.proceed(request)  //Adds the API key to each request made...
            }
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
            .create(AIRetrofitApi::class.java)
    }

}