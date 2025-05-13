package com.example.employeetaskreg.data.repsitory

import android.util.Log
import com.example.employeetaskreg.data.api.EmployeeTaskRegApi
import com.example.employeetaskreg.domain.model.CompanyWorker
import com.example.employeetaskreg.domain.repository.ProfileRepository
import retrofit2.HttpException
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(private val api: EmployeeTaskRegApi):ProfileRepository {
    override suspend fun getProfile(authToken: String): Result<CompanyWorker> {
        return try{
            val response = api.getProfile("Bearer $authToken")
            Result.success(response)
        //EmpTaskRegState.Success(response)
        }catch (e: HttpException){
            Log.e("getProfile",e.toString())
            Result.failure(e)
        //EmpTaskRegState.Failure(Exception("${e.code()} - ${e.message()}"))
        }catch(e:Exception){
            Log.e("getProfile",e.toString())
            Result.failure(e)
            //EmpTaskRegState.Failure(Exception("Error during login: Check your connection"))
        }
    }
}