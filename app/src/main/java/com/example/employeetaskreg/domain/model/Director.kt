package com.example.employeetaskreg.domain.model

data class Director(
    override val id:Int,
    override val name:String,
    val userId:Int,
    val role:String = "director"
):CompanyWorker
