package com.example.employeetaskreg.data.repsitory

import android.util.Log
import com.example.employeetaskreg.data.api.EmployeeTaskRegApi
import com.example.employeetaskreg.data.api.dto.AddReportRequest
import com.example.employeetaskreg.domain.model.Report
import com.example.employeetaskreg.domain.repository.ReportRepository
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(private val api: EmployeeTaskRegApi):ReportRepository {
    override suspend fun getReportList(authToken: String): Result<List<Report>> {
        return try {
            val response = api.getReports("Bearer $authToken").sortedBy { it.id }
            Result.success(response)
        }catch (e: HttpException){
            Log.e("getReportList",e.toString())
            Result.failure(e)
        }catch(e:Exception){
            Log.i("getReportList",e.toString())
            Result.failure(e)
        }
    }

    private fun convertMillisToDate(millis: Long): String {
        val formatter = DateTimeFormatter.ofPattern("yyy-MM-dd")
            .withZone(ZoneId.systemDefault())
        return formatter.format(Instant.ofEpochMilli(millis))
    }

    override suspend fun addReport(report: AddReportRequest, file: File?, authToken: String): Result<Unit> {
        return try {
            val reportDateFormat = convertMillisToDate(report.reportDate.toLong())
            val reportWithDateAndFile = report.copy(documentName = file?.name, reportDate = reportDateFormat)
            val reportJson = Gson().toJson(reportWithDateAndFile)
            val reportRequestBody = reportJson.toRequestBody("application/json".toMediaTypeOrNull())
            val requestFile = file?.asRequestBody("application/pdf".toMediaTypeOrNull())
            val filePart = file?.let {
                requestFile?.let {
                    MultipartBody.Part.createFormData("file",file.name,requestFile)
                }
            }
            val response = api.addReport("Bearer $authToken",reportRequestBody,filePart)
            Result.success(response)
        }catch (e:HttpException){
            Log.e("addReport",e.toString())
            Result.failure(e)
        }catch(e:Exception){
            Log.e("addReport",e.toString())
            Result.failure(e)
        }
    }

}