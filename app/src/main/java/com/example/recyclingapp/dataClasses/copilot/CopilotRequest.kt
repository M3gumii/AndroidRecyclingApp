package com.example.recyclingapp.dataClasses.copilot

data class CopilotRequest(
    val model: String = "gpt-4o-mini",
    val messages: List<CopilotMessage>,
    val response_format: Map<String, String> = mapOf("type" to "json_object")
)

