package com.example.recyclingapp.dataClasses

data class User(
    val username: String,
    val password: String,
    val email: String,
    val num_items_recycled: Int = 0
)