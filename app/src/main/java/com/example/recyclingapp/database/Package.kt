package com.example.recyclingapp.database

class Package (
    val barcode: String,
    val name: String,
    val recycling_pos: Boolean = false,
    val image_link: String? = null,
    val description: String? = null
)