package com.example.employeetaskreg.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.employeetaskreg.domain.model.Report
import com.example.employeetaskreg.domain.repository.AuthRepository
import com.example.employeetaskreg.domain.repository.EmpTaskRegState
import com.example.employeetaskreg.domain.repository.ReportRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val reportRepository: ReportRepository,private val authRepository: AuthRepository): ViewModel() {

    private val _reportListFlow = MutableStateFlow<EmpTaskRegState<List<Report>>>(EmpTaskRegState.Waiting)

    val reportListFlow: StateFlow<EmpTaskRegState<List<Report>>> = _reportListFlow

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

}