package com.example.employeetaskreg.data.api.dto

data class RegistrationRequest(
    val login:String,
    val password:String,
    val name: String,
    val dirName:String
)
