package com.example.employeetaskreg.domain.model

import java.time.LocalDate

data class Task(
    val id:Int,
    val title:String,
    val taskDesc:String,
    val documentName:String? = null,
    val startDate:LocalDate,
    val endDate:LocalDate,
    val employeeId:Int?,
    val directorId:Int?,
    val status:String
)
