package com.example.employeetaskreg.data.repsitory

import android.util.Log
import com.example.employeetaskreg.data.api.EmployeeTaskRegApi
import com.example.employeetaskreg.domain.model.CompanyWorker
import com.example.employeetaskreg.domain.repository.DirectorRepository
import retrofit2.HttpException
import javax.inject.Inject

class DirectorRepositoryImpl@Inject constructor(private val api: EmployeeTaskRegApi):DirectorRepository {
    override suspend fun getDirectorById(
        id: Int,
        authToken: String
    ): Result<CompanyWorker.Director> {
        return try {
            val response = api.getDirectorById("Bearer $authToken",id.toString())
            Result.success(response)
        }catch (e: HttpException){
            Log.e("getDirectorById",e.toString())
            Result.failure(e)
        //EmpTaskRegState.Failure(Exception("${e.code()} - ${e.message()}"))
        }catch(e:Exception){
            Log.e("getDirectorById",e.toString())
            Result.failure(e)
        //EmpTaskRegState.Failure(Exception("Error during getting director: Check your connection"))
        }
    }

}