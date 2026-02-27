package com.example.recyclingapp.database

class Package (
    val barcode: String,
    val name: String,
    val recycling_pos: Boolean = false,
    val image_ling: String? = null
)