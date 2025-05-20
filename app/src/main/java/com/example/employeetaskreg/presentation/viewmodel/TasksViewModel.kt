package com.example.employeetaskreg.presentation.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.employeetaskreg.data.api.dto.AddTaskRequest
import com.example.employeetaskreg.domain.model.Task
import com.example.employeetaskreg.domain.repository.AuthRepository
import com.example.employeetaskreg.domain.repository.EmpTaskRegState
import com.example.employeetaskreg.domain.repository.FileRepository
import com.example.employeetaskreg.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val fileRepository: FileRepository,
    private val application: Application,
    private val taskRepository: TaskRepository):ViewModel() {

    private val _taskListFlow = MutableStateFlow<EmpTaskRegState<List<Task>>>(EmpTaskRegState.Waiting)

    val taskListFlow: StateFlow<EmpTaskRegState<List<Task>>> = _taskListFlow

    private val _addTaskFlow = MutableStateFlow<EmpTaskRegState<Unit>>(EmpTaskRegState.Waiting)
    val addTaskFlow: StateFlow<EmpTaskRegState<Unit>> = _addTaskFlow

    private val _selectedFileUri = MutableStateFlow<Uri?>(null)

    fun setSelectedTaskFileUri(uri: Uri?) {
        _selectedFileUri.value = uri
    }


    fun getTaskList() = viewModelScope.launch {
        _taskListFlow.value = EmpTaskRegState.Loading
        val authResult = withContext(Dispatchers.IO){
            authRepository.getTokenFromDataStorage()
        }

        authResult.onSuccess {token ->
            val result = withContext(Dispatchers.IO){
                taskRepository.getTaskList(token)
            }
            result.onSuccess {
                _taskListFlow.value = EmpTaskRegState.Success(it)
            }.onFailure {
                _taskListFlow.value = when(it){
                    is HttpException -> EmpTaskRegState.Failure(Exception("${it.code()} - ${it.message()}"))
                    else -> EmpTaskRegState.Failure(Exception("Error during getting tasks: Check your connection"))
                }
            }

        }.onFailure {
            _taskListFlow.value = EmpTaskRegState.Failure(Exception("No token found. Please login first."))
        }
    }

    fun addTask(title:String, taskDesc:String, documentName:String?,
                startDate:String, endDate:String,
                employeeId:Int, directorId:Int) = viewModelScope.launch{
        _addTaskFlow.value = EmpTaskRegState.Loading

        val authResult = withContext(Dispatchers.IO){
            authRepository.getTokenFromDataStorage()
        }
        authResult.onSuccess {token ->
            val result = withContext(Dispatchers.IO){
                val file = _selectedFileUri.value?.let {
                    fileRepository.uriToFile(application.applicationContext,it)
                }

                val task = AddTaskRequest(title, taskDesc, documentName,startDate, endDate, employeeId, directorId)

                taskRepository.addTask(task,file,token)
            }
            result.onSuccess {
                _addTaskFlow.value = EmpTaskRegState.Success(it)
            }.onFailure {
                _addTaskFlow.value = when(it){
                    is HttpException -> EmpTaskRegState.Failure(Exception("${it.code()} - ${it.message()}"))
                    else -> EmpTaskRegState.Failure(Exception("Error during adding task: Check your connection"))
                }
            }

        }.onFailure {
            _addTaskFlow.value = EmpTaskRegState.Failure(Exception("No token found. Please login first."))
        }
    }

}