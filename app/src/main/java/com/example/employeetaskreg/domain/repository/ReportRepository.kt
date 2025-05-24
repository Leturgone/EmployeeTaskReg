package com.example.employeetaskreg.domain.repository

import com.example.employeetaskreg.data.api.dto.AddReportRequest
import com.example.employeetaskreg.domain.model.Report
import java.io.File

interface ReportRepository {
    suspend fun getReportList(authToken:String):Result<List<Report>>

    suspend fun addReport(report: AddReportRequest, file: File?, authToken:String):Result<Unit>

    suspend fun downloadReport(reportId:Int,authToken:String):Result<ByteArray>

    suspend fun markReport(reportId: Int, status:Boolean, authToken: String): Result<Unit>

}