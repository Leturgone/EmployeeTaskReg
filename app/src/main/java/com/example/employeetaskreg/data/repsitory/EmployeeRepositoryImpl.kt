package com.example.employeetaskreg.data.repsitory

import android.util.Log
import com.example.employeetaskreg.data.api.EmployeeTaskRegApi
import com.example.employeetaskreg.domain.model.CompanyWorker
import com.example.employeetaskreg.domain.repository.EmployeeRepository
import retrofit2.HttpException
import javax.inject.Inject

class EmployeeRepositoryImpl @Inject constructor(private val api: EmployeeTaskRegApi):EmployeeRepository {
    override suspend fun getEmployeesList(authToken:String): Result<List<CompanyWorker.Employee>> {
        return try {
            val response = api.getEmployees("Bearer $authToken").sortedBy { it.id }
            Result.success(response)
            //EmpTaskRegState.Success(response)
        }catch (e:HttpException){
            Log.e("getEmployeesList",e.toString())
            Result.failure(e)
        //EmpTaskRegState.Failure(Exception("${e.code()} - ${e.message()}"))
        }catch(e:Exception){
            Log.e("getEmployeesList",e.toString())
            Result.failure(e)
            //EmpTaskRegState.Failure(Exception("Error during getting employees: Check your connection"))
        }
    }

    override suspend fun getEmployeeById(id: Int,authToken: String): Result<CompanyWorker.Employee> {
        return try {
            val response = api.getEmployeeById("Bearer $authToken",id.toString())
            Result.success(response)
        }catch (e: HttpException){
            Log.e("getEmployeeById",e.toString())
            Result.failure(e)
            //EmpTaskRegState.Failure(Exception("${e.code()} - ${e.message()}"))
        }catch(e:Exception){
            Log.i("getEmployeeById",e.toString())
            Result.failure(e)
            //EmpTaskRegState.Failure(Exception("Error during getting employee: Check your connection"))
        }
    }
}