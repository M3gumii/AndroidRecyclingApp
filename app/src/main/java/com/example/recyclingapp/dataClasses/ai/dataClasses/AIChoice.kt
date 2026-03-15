package com.example.recyclingapp.dataClasses.ai.dataClasses

import com.squareup.moshi.JsonClass

/**
 * Represents one generated answer from the AI.
 *
 * @property index The position of this choice in the response.
 * @property message The assistant's message content for this choice.
 * @property finish_reason Why the model stopped generating (e.g., "stop").
 */
@JsonClass(generateAdapter = true)
data class AIChoice(
    val index: Int,
    val message: AIMessage,  //Actual AI Generated text
    val finish_reason: String?
)