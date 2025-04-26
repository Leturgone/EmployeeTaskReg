package com.example.employeetaskreg.domain.repository

import com.example.employeetaskreg.domain.model.CompanyWorker
import com.example.employeetaskreg.domain.model.Report
import com.example.employeetaskreg.domain.model.Task

interface EmployeeTaskRegRepository {

    suspend fun getTokenFromDataStorage():String
    suspend fun login(login:String, password:String):EmpTaskRegState<String>

    suspend fun register(login:String, password:String, name: String, dirName:String):EmpTaskRegState<String>

    suspend fun logout()

    suspend fun getProfile():EmpTaskRegState<CompanyWorker>

    suspend fun getDirectorById(id: Int):EmpTaskRegState<CompanyWorker.Director>

    suspend fun getEmployeeById(id: Int):EmpTaskRegState<CompanyWorker.Employee>

    suspend fun getTaskCount():EmpTaskRegState<Int>

    suspend fun getTaskList():EmpTaskRegState<List<Task>>

    suspend fun getReportList():EmpTaskRegState<List<Report>>

    suspend fun getEmployeesList():EmpTaskRegState<List<CompanyWorker.Employee>>
}