package com.example.recyclingapp.dataClasses.ai.dataClasses

import com.squareup.moshi.JsonClass

/**
 * Represents a single message within a chat conversation sent to the AI model.
 * CONVO HISTORY!
 *
 * @property role The role of the message sender. Valid values are:
 *  - "system": sets behavior or high‑level instructions for the model
 *  - "user": input provided by the end user
 *  - "assistant": previous responses from the AI
 *
 * @property content The textual content of the message. The model uses this
 * information, along with prior messages, to generate the next response.
 */
@JsonClass(generateAdapter = true)
data class AIMessage(
    val role: String,
    val content: String
)