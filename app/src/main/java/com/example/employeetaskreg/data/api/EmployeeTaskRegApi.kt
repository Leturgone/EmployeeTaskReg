package com.example.employeetaskreg.data.api

import retrofit2.http.GET

interface EmployeeTaskRegApi {

    @GET("/profile/myEmployees")
    suspend fun getEmployees()
}