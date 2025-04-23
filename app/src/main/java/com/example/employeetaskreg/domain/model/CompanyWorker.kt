package com.example.employeetaskreg.domain.model

sealed class CompanyWorker:CompanyWorkerInterface {
    data class Director(
        override val id:Int,
        override val name:String,
        val userId:Int,
        val role:String = "director"
    ):CompanyWorker()

    data class Employee(
        override val id:Int,
        override val name:String,
        val userId:Int,
        val directorId:Int?,
        val role:String = "employee"
    ):CompanyWorker()
}