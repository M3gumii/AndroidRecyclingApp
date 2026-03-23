package com.example.recyclingapp.dataClasses.ai.dataClasses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OpenFoodFactsProduct(
    @Json(name = "product_name") val productName: String?,
    @Json(name = "image_url") val imageUrl: String?,
    val packaging: String?
)
