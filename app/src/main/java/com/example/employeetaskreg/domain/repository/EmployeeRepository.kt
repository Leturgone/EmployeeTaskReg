package com.example.employeetaskreg.domain.repository

import com.example.employeetaskreg.domain.model.CompanyWorker

interface EmployeeRepository {
    suspend fun getEmployeesList():EmpTaskRegState<List<CompanyWorker.Employee>>
    suspend fun getEmployeeById(id: Int):EmpTaskRegState<CompanyWorker.Employee>
}