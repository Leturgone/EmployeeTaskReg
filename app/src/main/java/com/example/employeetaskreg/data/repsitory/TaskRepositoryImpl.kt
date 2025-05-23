package com.example.employeetaskreg.data.repsitory

import android.util.Log
import com.example.employeetaskreg.data.api.EmployeeTaskRegApi
import com.example.employeetaskreg.data.api.dto.AddTaskRequest
import com.example.employeetaskreg.domain.model.Task
import com.example.employeetaskreg.domain.repository.TaskRepository
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

    private fun convertMillisToDate(millis: Long): String {
        val formatter = DateTimeFormatter.ofPattern("yyy-MM-dd")
            .withZone(ZoneId.systemDefault())
        return formatter.format(Instant.ofEpochMilli(millis))
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

    override suspend fun getEmployeeCurrentTask(id: Int, authToken: String): Result<Task> {
        return try {
            val response = api.getEmployeeCurrentTaskById("Bearer $authToken",id.toString())
            Result.success(response)
        }catch (e:HttpException){
            Log.e("getEmployeeCurrentTask",e.toString())
            Result.failure(e)
        }catch(e:Exception){
            Log.e("getEmployeeCurrentTask",e.toString())
            Result.failure(e)
        }
    }

    override suspend fun addTask(task: AddTaskRequest, file: File?, authToken:String): Result<Unit> {
        return try{
            val startDateFormat = convertMillisToDate(task.startDate.toLong())
            val endDateFormat = convertMillisToDate(task.endDate.toLong())
            val taskWithDatesAndFile = task.copy(documentName = file?.name, startDate = startDateFormat, endDate = endDateFormat )
            val taskJson = Gson().toJson(taskWithDatesAndFile)
            val taskRequestBody = taskJson.toRequestBody("application/json".toMediaTypeOrNull())
            val requestFile = file?.asRequestBody("application/pdf".toMediaTypeOrNull())
            val filePart = file?.let {
                requestFile?.let {
                    MultipartBody.Part.createFormData("file",file.name,requestFile)
                }
            }
            val response = api.addTask("Bearer $authToken",taskRequestBody,filePart)
            Result.success(response)
        }catch (e:HttpException){
            Log.e("addTask",e.toString())
            Result.failure(e)
        }catch(e:Exception){
            Log.e("addTask",e.toString())
            Result.failure(e)
        }
    }

}