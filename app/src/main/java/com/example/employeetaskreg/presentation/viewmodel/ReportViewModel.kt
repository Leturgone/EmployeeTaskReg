package com.example.employeetaskreg.presentation.viewmodel

import android.app.Application
import android.net.Uri
import android.os.Environment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.employeetaskreg.data.api.dto.AddReportRequest
import com.example.employeetaskreg.domain.model.Report
import com.example.employeetaskreg.domain.repository.AuthRepository
import com.example.employeetaskreg.domain.repository.EmpTaskRegState
import com.example.employeetaskreg.domain.repository.FileRepository
import com.example.employeetaskreg.domain.repository.ReportRepository
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
class ReportViewModel @Inject constructor(
    private val reportRepository: ReportRepository,
    private val fileRepository: FileRepository,
    private val application: Application,
    private val authRepository: AuthRepository): ViewModel() {

    private val _reportListFlow = MutableStateFlow<EmpTaskRegState<List<Report>>>(EmpTaskRegState.Waiting)

    val reportListFlow: StateFlow<EmpTaskRegState<List<Report>>> = _reportListFlow

    private val _addReportFlow = MutableStateFlow<EmpTaskRegState<Unit>>(EmpTaskRegState.Waiting)
    val addReportFlow: StateFlow<EmpTaskRegState<Unit>> = _addReportFlow

    private val _selectedFileUri = MutableStateFlow<Uri?>(null)

    private val _downloadReport = MutableStateFlow<EmpTaskRegState<File>>(EmpTaskRegState.Waiting)
    val downloadReport: StateFlow<EmpTaskRegState<File>> = _downloadReport

    private val _markReport = MutableStateFlow<EmpTaskRegState<Unit>>(EmpTaskRegState.Waiting)
    val markReport: StateFlow<EmpTaskRegState<Unit>> = _markReport

    private val _reportByTaskIdFlow = MutableStateFlow<EmpTaskRegState<Report>>(EmpTaskRegState.Waiting)
    val reportByTaskIdFlow:StateFlow<EmpTaskRegState<Report>> = _reportByTaskIdFlow

    fun setSelectedReportFileUri(uri: Uri?)  = viewModelScope.launch{
        _selectedFileUri.value = uri
    }

    fun resetDownloadState()  = viewModelScope.launch{
        _downloadReport.value = EmpTaskRegState.Waiting
    }

    fun resetMarkState() = viewModelScope.launch {
        _markReport.value = EmpTaskRegState.Waiting
    }

    fun getReportList() = viewModelScope.launch {
        _reportListFlow.value = EmpTaskRegState.Loading

        val authResult = withContext(Dispatchers.IO){
            authRepository.getTokenFromDataStorage()
        }

        authResult.onSuccess {token ->
            val result = withContext(Dispatchers.IO){
                reportRepository.getReportList(token)
            }
            result.onSuccess {
                _reportListFlow.value = EmpTaskRegState.Success(it)
            }.onFailure {
                _reportListFlow.value = when(it){
                    is HttpException -> EmpTaskRegState.Failure(Exception("${it.code()} - ${it.message()}"))
                    else -> EmpTaskRegState.Failure(Exception("Error during getting reports: Check your connection"))
                }
            }
        }.onFailure {
            _reportListFlow.value = EmpTaskRegState.Failure(Exception("No token found. Please login first."))
        }
    }

    fun addReport(reportDate:String, documentName:String?,
                  taskId:Int,employeeId:Int,directorId:Int) = viewModelScope.launch{
        _addReportFlow.value = EmpTaskRegState.Loading

        val authResult = withContext(Dispatchers.IO){
            authRepository.getTokenFromDataStorage()
        }
        authResult.onSuccess {token ->
            val result = withContext(Dispatchers.IO){
                var file:File? = null
                val resFile = _selectedFileUri.value?.let {
                    fileRepository.uriToFile(application.applicationContext,it)
                }

               resFile?.onSuccess { file = it}

                val report = AddReportRequest(
                    reportDate = reportDate,
                    documentName = documentName,
                    taskId = taskId,
                    employeeId = employeeId,
                    directorId = directorId
                )
                reportRepository.addReport(report,file,token)
            }
            result.onSuccess {
                _addReportFlow.value = EmpTaskRegState.Success(it)
            }.onFailure {
                _addReportFlow.value = when(it){
                    is HttpException -> EmpTaskRegState.Failure(Exception("${it.code()} - ${it.message()}"))
                    else -> EmpTaskRegState.Failure(Exception("Error during adding report: Check your connection"))
                }
            }

        }.onFailure {
            _addReportFlow.value = EmpTaskRegState.Failure(Exception("No token found. Please login first."))
        }
    }

    fun downloadReport(reportId:Int) = viewModelScope.launch {
        _downloadReport.value = EmpTaskRegState.Loading
        val authResult = withContext(Dispatchers.IO){
            authRepository.getTokenFromDataStorage()
        }
        authResult.onSuccess {token ->
            val result = withContext(Dispatchers.IO){
                reportRepository.downloadReport(reportId,token)
            }
            result.onSuccess {
                val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                val destinationFile = File(downloadsDir, "report_${reportId}.pdf")
                val fileResult = withContext(Dispatchers.IO) {
                    fileRepository.byteArrayToFile(
                        context = application.applicationContext,
                        byteArray = it,
                        destinationFile = destinationFile
                    )
                }
                fileResult.onSuccess {
                    _downloadReport.value = EmpTaskRegState.Success(it)
                }.onFailure {
                    _downloadReport.value = when(it){
                        is HttpException -> EmpTaskRegState.Failure(Exception("${it.code()} - ${it.message()}"))
                        else -> EmpTaskRegState.Failure(Exception("Error during saving report: Check your connection"))
                    }
                }
            }.onFailure {
                _downloadReport.value = when(it){
                    is HttpException -> EmpTaskRegState.Failure(Exception("${it.code()} - ${it.message()}"))
                    else -> EmpTaskRegState.Failure(Exception("Error during download report: Check your connection"))
                }
            }
        }.onFailure {
            _downloadReport.value = EmpTaskRegState.Failure(Exception("No token found. Please login first."))
        }
    }

    fun markReport(reportId: Int, status:Boolean) = viewModelScope.launch{
        _markReport .value = EmpTaskRegState.Loading

        val authResult = withContext(Dispatchers.IO){
            authRepository.getTokenFromDataStorage()
        }

        authResult.onSuccess {token ->
            val result = withContext(Dispatchers.IO){
                reportRepository.markReport(reportId,status,token)
            }
            result.onSuccess {
                _markReport.value = EmpTaskRegState.Success(it)
            }.onFailure {
                _markReport.value = when(it){
                    is HttpException -> EmpTaskRegState.Failure(Exception("${it.code()} - ${it.message()}"))
                    else -> EmpTaskRegState.Failure(Exception("Error during mark reports: Check your connection"))
                }
            }
        }.onFailure {
            _markReport.value = EmpTaskRegState.Failure(Exception("No token found. Please login first."))
        }
    }

    fun getReportByTaskId(taskId: Int) = viewModelScope.launch {
        _reportByTaskIdFlow.value = EmpTaskRegState.Loading

        val authResult = withContext(Dispatchers.IO){
            authRepository.getTokenFromDataStorage()
        }

        authResult.onSuccess {token ->
            val result = withContext(Dispatchers.IO){
                reportRepository.getReportByTaskId(taskId,token)
            }
            result.onSuccess {
                _reportByTaskIdFlow.value = EmpTaskRegState.Success(it)
            }.onFailure {
                _reportByTaskIdFlow.value = when(it){
                    is HttpException -> EmpTaskRegState.Failure(Exception("${it.code()} - ${it.message()}"))
                    else -> EmpTaskRegState.Failure(Exception("Error during getting report by taskId: Check your connection"))
                }
            }
        }.onFailure {
            _markReport.value = EmpTaskRegState.Failure(Exception("No token found. Please login first."))
        }
    }
}