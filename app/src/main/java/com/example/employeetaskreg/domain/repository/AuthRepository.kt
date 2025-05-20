package com.example.employeetaskreg.domain.repository

interface AuthRepository {
    suspend fun getTokenFromDataStorage():Result<String>

    suspend fun login(login:String, password:String):Result<String>

    suspend fun register(login:String, password:String, name: String, dirName:String):Result<String>

    suspend fun logout():Result<Unit>
}