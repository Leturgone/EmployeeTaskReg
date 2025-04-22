package com.example.employeetaskreg.domain.model

data class Employee(
    override val id:Int,
    override val name:String,
    val userId:Int,
    val directorId:Int?,
    val role:String = "employee"
):CompanyWorker
