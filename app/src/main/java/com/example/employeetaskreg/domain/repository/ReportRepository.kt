package com.example.employeetaskreg.domain.repository

import com.example.employeetaskreg.domain.model.Report

interface ReportRepository {
    suspend fun getReportList():EmpTaskRegState<List<Report>>
}