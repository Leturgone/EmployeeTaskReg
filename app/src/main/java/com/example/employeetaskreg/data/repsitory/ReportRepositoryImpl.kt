package com.example.employeetaskreg.data.repsitory

import android.util.Log
import com.example.employeetaskreg.data.api.EmployeeTaskRegApi
import com.example.employeetaskreg.domain.model.Report
import com.example.employeetaskreg.domain.repository.ReportRepository
import retrofit2.HttpException
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(private val api: EmployeeTaskRegApi):ReportRepository {
    override suspend fun getReportList(authToken: String): Result<List<Report>> {
        return try {
            val response = api.getReports("Bearer $authToken").sortedBy { it.id }
            Result.success(response)
            //EmpTaskRegState.Success(response)
        }catch (e: HttpException){
            Log.e("getReportList",e.toString())
            Result.failure(e)
        //EmpTaskRegState.Failure(Exception("${e.code()} - ${e.message()}"))
        }catch(e:Exception){
            Log.i("getReportList",e.toString())
            Result.failure(e)
            //EmpTaskRegState.Failure(Exception("Error during getting reports: Check your connection"))
        }
    }

}