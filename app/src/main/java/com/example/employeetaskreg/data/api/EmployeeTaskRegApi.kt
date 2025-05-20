package com.example.employeetaskreg.data.api

import com.example.employeetaskreg.data.api.dto.LoginRequest
import com.example.employeetaskreg.data.api.dto.RegistrationRequest
import com.example.employeetaskreg.data.api.dto.TokenResponse
import com.example.employeetaskreg.domain.model.CompanyWorker
import com.example.employeetaskreg.domain.model.Report
import com.example.employeetaskreg.domain.model.Task
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
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

    @GET("profile/myEmployees/employee/{employeeId}")
    suspend fun getEmployeeById(
        @Header("Authorization") token: String,
        @Path("employeeId") employeeId: String
    ):CompanyWorker.Employee


    @GET("/profile/myTaskCount")
    suspend fun getTaskCount(
        @Header("Authorization") token: String
    ):Int

    @GET("/profile/myTasks")
    suspend fun getTasks(
        @Header("Authorization") token: String
    ):List<Task>

    @GET("/profile/myReports")
    suspend fun getReports(
        @Header("Authorization") token: String
    ):List<Report>

    @GET("/profile/myEmployees")
    suspend fun getEmployees(
        @Header("Authorization") token: String
    ):List<CompanyWorker.Employee>

    @GET("profile/myEmployees/employee/{employeeId}/taskCount")
    suspend fun getEmployeeTaskCountById(
        @Header("Authorization") token: String,
        @Path("employeeId") employeeId: String
    ):Int

    @Multipart
    @POST("/profile/addTask")
    suspend fun addTask(
        @Header("Authorization") token: String,
        @Part("taskJson")task: RequestBody,
        @Part file: MultipartBody.Part?
    )

    @Multipart
    @POST("/profile/addReport")
    suspend fun addReport(
        @Header("Authorization") token: String,
        @Part("reportJson")report: RequestBody,
        @Part file: MultipartBody.Part?
    )



    @GET("profile/myEmployees/{empName}")
    suspend fun getEmployeeByName()


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