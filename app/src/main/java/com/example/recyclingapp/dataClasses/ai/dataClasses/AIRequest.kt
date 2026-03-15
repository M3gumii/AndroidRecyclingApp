package com.example.recyclingapp.dataClasses.ai.dataClasses
import com.squareup.moshi.JsonClass

/**
 * Represents a request sent to the AI chat completion endpoint.
 *
 * @property model The model identifier to use for generating the completion -> AI type!
 * @property messages The ordered list of chat messages forming the conversation
 * history. Each message includes a role ("system", "user", or "assistant") and
 * the text content sent so far. The model uses this list to understand context
 * and generate the next response.
 * @property response_format Specifies how the AI should format its output
 * (e.g., enforcing JSON structure).
 */
@JsonClass(generateAdapter = true)
data class AIRequest(
    val model: String = "gpt-4o-mini",
    val messages: List<AIMessage>,
    val response_format: Map<String, String> = mapOf("type" to "json_object")
)

