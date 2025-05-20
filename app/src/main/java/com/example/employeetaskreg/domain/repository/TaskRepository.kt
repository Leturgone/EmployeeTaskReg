package com.example.employeetaskreg.domain.repository

import com.example.employeetaskreg.data.api.dto.AddTaskRequest
import com.example.employeetaskreg.domain.model.Task
import java.io.File

interface TaskRepository {

    suspend fun getTaskCount(authToken:String):Result<Int>

    suspend fun getTaskList(authToken:String):Result<List<Task>>

    suspend fun getEmployeeTaskCount(id:Int,authToken:String):Result<Int>

    suspend fun getEmployeeCurrentTask(id:Int,authToken:String):Result<Task>

    suspend fun addTask(task:AddTaskRequest, file: File?, authToken:String):Result<Unit>

}