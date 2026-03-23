package com.example.recyclingapp.dataClasses.ai

import android.util.Log
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
    private const val BASE_URL = "https://generativelanguage.googleapis.com/v1beta/"

    fun create(apiKey: String): AIRetrofitApi {

        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val originalUrl = original.url

                // Add the API key as a query parameter
                val newUrl = originalUrl.newBuilder()
                    .addQueryParameter("key", apiKey)
                    .build()

                Log.d("AIContactor", "FINAL URL = $newUrl")


                val newRequest = original.newBuilder()
                    .url(newUrl)
                    .build()

                val buffer = okio.Buffer()
                newRequest.body?.writeTo(buffer)
                Log.d("AIContactor", "REQUEST BODY = ${buffer.readUtf8()}")

                chain.proceed(newRequest)
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