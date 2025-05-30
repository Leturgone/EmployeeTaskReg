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
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(private val api: EmployeeTaskRegApi):ReportRepository {

    override suspend fun getReportList(authToken: String): Result<List<Report>> {
        return try {
            val response = api.getReports("Bearer $authToken").sortedBy { it.id }.reversed()
            Result.success(response)
        }catch (e: HttpException){
            Log.e("getReportList",e.toString())
            Result.failure(e)
        }catch(e:Exception){
            Log.i("getReportList",e.toString())
            Result.failure(e)
        }
    }

    override suspend fun getReportById(reportId: Int, authToken: String): Result<Report> {
        return try {
            val result = api.getReportById("Bearer $authToken",reportId.toString())
            Result.success(result)
        }catch (e: HttpException){
            Log.e("getReportById",e.toString())
            Result.failure(e)
        }catch(e:Exception){
            Log.i("getReportById",e.toString())
            Result.failure(e)
        }
    }

    override suspend fun addReport(report: AddReportRequest, file: File?, authToken: String): Result<Unit> {
        return try {
            val reportWithFile = report.copy(documentName = file?.name)
            val reportJson = Gson().toJson(reportWithFile)
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

    override suspend fun downloadReport(reportId: Int, authToken: String): Result<ByteArray> {
        return try {
            val responseBody = api.downloadReportById("Bearer $authToken",reportId.toString())
            val byteArray = responseBody.bytes()
            Result.success(byteArray)
        }catch (e:HttpException){
            Log.e("downloadReport",e.toString())
            Result.failure(e)
        }catch(e:Exception){
            Log.e("downloadReport",e.toString())
            Result.failure(e)
        }
    }

    override suspend fun markReport(reportId: Int, status:Boolean, authToken: String): Result<Unit> {
        return try {
            val result = api.markReportById("Bearer $authToken",reportId.toString(),status)
            Result.success(result)
        }catch (e:HttpException){
            Log.e("markReport",e.toString())
            Result.failure(e)
        }catch(e:Exception){
            Log.e("markReport",e.toString())
            Result.failure(e)
        }
    }

    override suspend fun getReportByTaskId(taskId: Int, authToken: String): Result<Report> {
        return try {
            val result = api.getReportByTaskId("Bearer $authToken",taskId.toString())
            Result.success(result)
        }catch (e:HttpException){
            Log.e("getReportByTaskId",e.toString())
            Result.failure(e)
        }catch(e:Exception){
            Log.e("getReportByTaskId",e.toString())
            Result.failure(e)
        }
    }

    override suspend fun updateReport(reportId: Int, file: File?, authToken: String): Result<Unit> {
        return try {
            val requestFile = file?.asRequestBody("application/pdf".toMediaTypeOrNull())
            val filePart = file?.let {
                requestFile?.let {
                    MultipartBody.Part.createFormData("file",file.name,requestFile)
                }
            }
            val response = api.updateReport("Bearer $authToken",reportId.toString(),filePart)
            Result.success(response)
        }catch (e:HttpException){
            Log.e("updateReport",e.toString())
            Result.failure(e)
        }catch(e:Exception){
            Log.e("updateReport",e.toString())
            Result.failure(e)
        }
    }

    override suspend fun deleteReport(reportId: Int, authToken: String): Result<Unit> {
        return try {
            val response = api.deleteReportById("Bearer $authToken",reportId.toString())
            Result.success(response)
        }catch (e: HttpException){
            Log.e("deleteReport",e.toString())
            Result.failure(e)
        }catch(e:Exception){
            Log.i("deleteReport",e.toString())
            Result.failure(e)
        }
    }

}