package com.example.employeetaskreg.data.api.dto

data class AddReportRequest(
    val documentName:String? = "",
    val status:String = "Ожидание",
    val taskId:Int,
    val employeeId:Int,
    val directorId:Int,
)