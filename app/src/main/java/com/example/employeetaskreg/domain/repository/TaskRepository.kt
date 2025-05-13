package com.example.employeetaskreg.domain.repository

import com.example.employeetaskreg.domain.model.Task

interface TaskRepository {
    suspend fun getTaskCount():Result<Int>

    suspend fun getTaskList():Result<List<Task>>

    suspend fun getEmployeeTaskCount(id:Int):EmpTaskRegState<Int>

}