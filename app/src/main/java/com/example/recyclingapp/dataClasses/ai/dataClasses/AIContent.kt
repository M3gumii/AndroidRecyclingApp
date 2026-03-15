package com.example.recyclingapp.dataClasses.ai.dataClasses

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AIContent(
    val parts: List<AIPart>
)
