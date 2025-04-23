package com.example.employeetaskreg.domain.repository

import com.example.employeetaskreg.domain.model.CompanyWorker

interface EmployeeTaskRegRepository {
    suspend fun login(login:String, password:String):EmpTaskRegState<String>

    suspend fun register(login:String, password:String, name: String, dirName:String):EmpTaskRegState<String>

    suspend fun getProfile():EmpTaskRegState<CompanyWorker>

    suspend fun getDirectorNameById(id: Int):EmpTaskRegState<String>
}