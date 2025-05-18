package com.example.employeetaskreg.domain.repository

import com.example.employeetaskreg.data.api.dto.AddTaskRequest
import com.example.employeetaskreg.domain.model.Task

interface TaskRepository {

    suspend fun getTaskCount(authToken:String):Result<Int>

    suspend fun getTaskList(authToken:String):Result<List<Task>>

    suspend fun getEmployeeTaskCount(id:Int,authToken:String):Result<Int>

    suspend fun addTask(task:AddTaskRequest,filePath: String?,authToken:String):Result<Unit>

}