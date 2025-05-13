package com.example.employeetaskreg.domain.repository

interface AuthRepository {
    suspend fun getTokenFromDataStorage():String
    suspend fun login(login:String, password:String):EmpTaskRegState<String>

    suspend fun register(login:String, password:String, name: String, dirName:String):EmpTaskRegState<String>

    suspend fun logout()
}