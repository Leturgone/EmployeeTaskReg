package com.example.employeetaskreg.domain.repository

import com.example.employeetaskreg.domain.model.CompanyWorker

interface EmployeeRepository {
    suspend fun getEmployeesList(authToken:String):Result<List<CompanyWorker.Employee>>

    suspend fun getEmployeeById(id: Int, authToken: String):Result<CompanyWorker.Employee>
}