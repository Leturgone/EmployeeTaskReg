package com.example.employeetaskreg.data.api

import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface EmployeeTaskRegApi {

    @GET("/profile")
    suspend fun getProfile()
    @POST("/profile/addTask")
    suspend fun addTask()

    @POST("/profile/addReport")
    suspend fun addReport()

    @GET("/profile/myEmployees")
    suspend fun getEmployees()

    @GET("profile/myEmployees/{empName}")
    suspend fun getEmployeeByName()

    @GET("profile/myEmployees/employee/{employeeId}")
    suspend fun getEmployeeById()

    @GET("/profile/myTasks")
    suspend fun getTasks()

    @GET("/profile/myReports")
    suspend fun getReports()

    @GET("/profile/myTaskCount")
    suspend fun getTaskCount()

    @GET("/getReport/{reportId}")
    suspend fun getReportById()

    @GET("/getReport/{reportId}/download")
    suspend fun downloadReportById()

    @PATCH("/markReport/{reportId}/{status}")
    suspend fun markReportById()

    @GET("/getTask/{taskId}")
    suspend fun getTaskById()

    @GET("/getTask/{taskId}/download")
    suspend fun downloadTaskById()

    @POST("/users/register")
    suspend fun register()

    @POST("/users/login")
    suspend fun login()



}