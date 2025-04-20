package com.example.employeetaskreg.domain.repository

import com.example.employeetaskreg.domain.model.CompanyWorker

interface EmployeeTaskRegRepository {
    suspend fun login(login:String, password:String):Result<String>

    suspend fun register(login:String, password:String, name: String, dirName:String):Result<String>

    suspend fun getProfile():CompanyWorker
}