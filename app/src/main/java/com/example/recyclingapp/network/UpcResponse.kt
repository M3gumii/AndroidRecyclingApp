package com.example.recyclingapp.network

data class UpcResponse(
    val code: String,
    val total: Int,
    val offset: Int,
    val items: List<UpcItem>
)

data class UpcItem(
    val ean: String?,
    val title: String?,
    val description: String?,
    val upc: String?,
    val brand: String?,
    val model: String?,
    val color: String?,
    val size: String?,
    val dimension: String?,
    val weight: String?,
    val category: String?,
    val images: List<String>?
)