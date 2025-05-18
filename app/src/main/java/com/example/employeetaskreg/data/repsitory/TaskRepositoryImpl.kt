package com.example.employeetaskreg.data.repsitory

import android.util.Log
import com.example.employeetaskreg.data.api.EmployeeTaskRegApi
import com.example.employeetaskreg.domain.model.Task
import com.example.employeetaskreg.domain.repository.TaskRepository
import retrofit2.HttpException
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(private val api:EmployeeTaskRegApi):TaskRepository {
    override suspend fun getTaskCount(authToken: String): Result<Int> {
        return try {
            val response = api.getTaskCount("Bearer $authToken")
            Result.success(response)
        }catch (e: HttpException){
            Log.e("getTaskCount",e.toString())
            Result.failure(e)
        }catch(e:Exception){
            Log.e("getTaskCount",e.toString())
            Result.failure(e)
        }
    }

    override suspend fun getTaskList(authToken: String): Result<List<Task>> {
        return try {
            val response = api.getTasks("Bearer $authToken").sortedBy { it.id }
            Result.success(response)
        }catch (e:HttpException){
            Log.e("getTaskList",e.toString())
            Result.failure(e)
        }catch(e:Exception){
            Log.e("getTaskList",e.toString())
            Result.failure(e)
        }
    }

    override suspend fun getEmployeeTaskCount(id: Int, authToken: String): Result<Int> {
        return try {
            val response = api.getEmployeeTaskCountById("Bearer $authToken",id.toString())
            Result.success(response)
        }catch (e:HttpException){
            Log.e("getEmployeeTaskCount",e.toString())
            Result.failure(e)
        }catch(e:Exception){
            Log.e("getEmployeeTaskCount",e.toString())
            Result.failure(e)
        }
    }

}