package com.example.employeetaskreg.domain.model


data class AppUser(
    val id:Int,
    val login: String,
    val passwordHash: String,
    val role: String
)