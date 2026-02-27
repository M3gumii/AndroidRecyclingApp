package com.example.recyclingapp.database

data class User(
    val username: String,
    val password: String,
    val email: String,
    val num_items_recycled: Int = 0
)