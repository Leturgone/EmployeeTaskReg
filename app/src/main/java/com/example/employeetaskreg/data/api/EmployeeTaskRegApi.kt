package com.example.employeetaskreg.data.api

import com.example.employeetaskreg.data.api.dto.LoginRequest
import com.example.employeetaskreg.data.api.dto.RegistrationRequest
import com.example.employeetaskreg.data.api.dto.TokenResponse
import com.example.employeetaskreg.domain.model.CompanyWorker
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface EmployeeTaskRegApi {

    @GET("/profile")
    suspend fun getProfile(
        @Header("Authorization") token: String
    ):CompanyWorker

    @POST("/users/register")
    suspend fun register(
        @Body request: RegistrationRequest
    ): TokenResponse

    @POST("/users/login")
    suspend fun login(
        @Body request: LoginRequest
    ): TokenResponse

    @GET("/profile/director/{directorId}")
    suspend fun getDirectorById(
        @Header("Authorization") token: String,
        @Path("directorId") directorId: String
    ):CompanyWorker.Director

    @GET("/profile/myTaskCount")
    suspend fun getTaskCount()

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




}