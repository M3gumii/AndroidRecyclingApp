package com.example.recyclingapp.dataClasses.copilot

import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Tells retrofit how to process the API response...
 */
interface CopilotRetrofitApi {
    @POST("v1/chat/completions")
    suspend fun askCopilot(
        @Body request: CopilotRequest
    ): CopilotResponse
}