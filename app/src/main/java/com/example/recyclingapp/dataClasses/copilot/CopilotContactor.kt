package com.example.recyclingapp.dataClasses.copilot

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object CopilotContactor {
    private const val BASE_URL = "https://api.githubcopilot.com/"

    fun create(apiKey: String): CopilotRetrofitApi {
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
            .create(CopilotRetrofitApi::class.java)
    }

}