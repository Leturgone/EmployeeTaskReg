package com.example.employeetaskreg.presentation.viewmodel

import android.app.Application
import android.net.Uri
import android.os.Environment
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
import java.io.File
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val fileRepository: FileRepository,
    private val application: Application,
    private val taskRepository: TaskRepository):ViewModel() {

    private val _taskFlow = MutableStateFlow<EmpTaskRegState<Task>>(EmpTaskRegState.Waiting)
    val taskFlow: StateFlow<EmpTaskRegState<Task>> = _taskFlow

    private val _taskListFlow = MutableStateFlow<EmpTaskRegState<List<Task>>>(EmpTaskRegState.Waiting)
    val taskListFlow: StateFlow<EmpTaskRegState<List<Task>>> = _taskListFlow

    private val _addTaskFlow = MutableStateFlow<EmpTaskRegState<Unit>>(EmpTaskRegState.Waiting)
    val addTaskFlow: StateFlow<EmpTaskRegState<Unit>> = _addTaskFlow

    private val _deleteTaskFlow = MutableStateFlow<EmpTaskRegState<Unit>>(EmpTaskRegState.Waiting)
    val deleteTaskFlow: StateFlow<EmpTaskRegState<Unit>> = _deleteTaskFlow

    private val _selectedFileUri = MutableStateFlow<Uri?>(null)

    private val _downloadTask = MutableStateFlow<EmpTaskRegState<File>>(EmpTaskRegState.Waiting)
    val downloadTask: StateFlow<EmpTaskRegState<File>> = _downloadTask


    fun setSelectedTaskFileUri(uri: Uri?)  = viewModelScope.launch{
        _selectedFileUri.value = uri
    }

    fun resetDownloadState()  = viewModelScope.launch{
        _downloadTask.value = EmpTaskRegState.Waiting
    }
    fun resetDeleteState()  = viewModelScope.launch{
        _deleteTaskFlow.value = EmpTaskRegState.Waiting
    }

    fun getTaskById(taskId: Int) = viewModelScope.launch {
        _taskFlow.value = EmpTaskRegState.Loading
        val authResult = withContext(Dispatchers.IO){
            authRepository.getTokenFromDataStorage()
        }
        authResult.onSuccess {token ->
            val result = withContext(Dispatchers.IO){
                taskRepository.getTaskById(taskId,token)
            }
            result.onSuccess {
                _taskFlow.value = EmpTaskRegState.Success(it)
            }.onFailure {
                _taskFlow.value = when(it){
                    is HttpException -> EmpTaskRegState.Failure(Exception("${it.code()} - ${it.message()}"))
                    else -> EmpTaskRegState.Failure(Exception("Error during getting task by id: Check your connection"))
                }
            }

        }.onFailure {
            _taskFlow.value = EmpTaskRegState.Failure(Exception("No token found. Please login first."))
        }
    }


    fun deleteTaskById(taskId: Int) = viewModelScope.launch {
        _deleteTaskFlow.value = EmpTaskRegState.Loading
        val authResult = withContext(Dispatchers.IO){
            authRepository.getTokenFromDataStorage()
        }
        authResult.onSuccess {token ->
            val result = withContext(Dispatchers.IO){
                taskRepository.deleteTask(taskId,token)
            }
            result.onSuccess {
                getTaskList()
                _deleteTaskFlow.value = EmpTaskRegState.Success(it)
                resetDeleteState()
            }.onFailure {
                _deleteTaskFlow.value = when(it){
                    is HttpException -> EmpTaskRegState.Failure(Exception("${it.code()} - ${it.message()}"))
                    else -> EmpTaskRegState.Failure(Exception("Error during deleting task by id: Check your connection"))
                }
            }

        }.onFailure {
            _deleteTaskFlow.value = EmpTaskRegState.Failure(Exception("No token found. Please login first."))
        }
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
                var file:File? = null

                val fileRes = _selectedFileUri.value?.let {
                    fileRepository.uriToFile(application.applicationContext,it)
                }
                fileRes?.onSuccess { file = it }

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


    fun downloadTask(taskId:Int) = viewModelScope.launch {
        _downloadTask.value = EmpTaskRegState.Loading
        val authResult = withContext(Dispatchers.IO){
            authRepository.getTokenFromDataStorage()
        }
        authResult.onSuccess {token ->
            val result = withContext(Dispatchers.IO){
                taskRepository.downloadTask(taskId,token)
            }
            result.onSuccess {
                val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                val destinationFile = File(downloadsDir, "task_${taskId}.pdf")
                val fileResult = withContext(Dispatchers.IO) {
                    fileRepository.byteArrayToFile(
                        context = application.applicationContext,
                        byteArray = it,
                        destinationFile = destinationFile
                    )
                }
                fileResult.onSuccess {
                    _downloadTask.value = EmpTaskRegState.Success(it)
                }.onFailure {
                    _downloadTask.value = when(it){
                        is HttpException -> EmpTaskRegState.Failure(Exception("${it.code()} - ${it.message()}"))
                        else -> EmpTaskRegState.Failure(Exception("Error during saving task file: Check your connection"))
                    }
                }
            }.onFailure {
                _downloadTask.value = when(it){
                    is HttpException -> EmpTaskRegState.Failure(Exception("${it.code()} - ${it.message()}"))
                    else -> EmpTaskRegState.Failure(Exception("Error during download task file: Check your connection"))
                }
            }
        }.onFailure {
            _downloadTask.value = EmpTaskRegState.Failure(Exception("No token found. Please login first."))
        }
    }



}