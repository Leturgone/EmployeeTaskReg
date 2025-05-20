package com.example.employeetaskreg.data.api.dto

data class AddTaskRequest (
    val title:String,
    val taskDesc:String,
    val documentName:String? = "",
    val startDate:String,
    val endDate:String,
    val employeeId:Int,
    val directorId:Int,
)