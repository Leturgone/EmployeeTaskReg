package com.example.employeetaskreg.domain.model

data class Employee(
    val id:Int,
    val name:String,
    val userId:Int,
    val directorId:Int?,
    val role:String = "employee"
):CompanyWorker
