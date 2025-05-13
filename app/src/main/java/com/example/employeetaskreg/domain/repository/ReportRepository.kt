package com.example.employeetaskreg.domain.repository

import com.example.employeetaskreg.domain.model.Report

interface ReportRepository {
    suspend fun getReportList(authToken:String):Result<List<Report>>
}