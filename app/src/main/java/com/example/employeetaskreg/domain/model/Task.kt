package com.example.employeetaskreg.domain.model

import java.time.LocalDate

data class Task(
    val id:Int,
    val title:String,
    val taskDesc:String,
    val documentName:String? = null,
    val startDate:String,
    val endDate:String,
    val employeeId:Int?,
    val employeeName:String?,
    val directorId:Int?,
    val status:String
)
