package com.example.employeetaskreg.domain.model

data class Report(
    val id:Int,
    val reportDate:String,
    val documentName:String?,
    val status:String,
    val taskId:Int,
    val employeeId:Int?,
    val employeeName:String?,
    val directorId:Int?,
)
