package com.example.employeetaskreg.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.employeetaskreg.domain.model.CompanyWorker
import com.example.employeetaskreg.domain.repository.AuthRepository
import com.example.employeetaskreg.domain.repository.EmpTaskRegState
import com.example.employeetaskreg.domain.repository.EmployeeRepository
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
class EmployeesViewModel @Inject constructor(
    private val employeeRepository: EmployeeRepository,
    private val taskRepository: TaskRepository,
    private val authRepository: AuthRepository): ViewModel() {

    private val _employeesListFlow = MutableStateFlow<EmpTaskRegState<List<CompanyWorker.Employee>>>(EmpTaskRegState.Waiting)

    val employeesListFlow : StateFlow<EmpTaskRegState<List<CompanyWorker.Employee>>> = _employeesListFlow

    private val _employeeTaskCountFlow = MutableStateFlow<EmpTaskRegState<Int>>(EmpTaskRegState.Waiting)

    val employeeTaskCountFlow: StateFlow<EmpTaskRegState<Int>> = _employeeTaskCountFlow

    private val _employeeFlow = MutableStateFlow<EmpTaskRegState<CompanyWorker.Employee>>(EmpTaskRegState.Waiting)

    val employeeFlow: StateFlow<EmpTaskRegState<CompanyWorker.Employee>> = _employeeFlow

    private val _employeesSearchResultFlow = MutableStateFlow<EmpTaskRegState<List<CompanyWorker.Employee>>>(EmpTaskRegState.Waiting)

    val employeesSearchResultFlow : StateFlow<EmpTaskRegState<List<CompanyWorker.Employee>>> = _employeesListFlow

    fun getEmployeeById(id:Int) = viewModelScope.launch {
        _employeeFlow.value = EmpTaskRegState.Loading

        val authResult = withContext(Dispatchers.IO){
            authRepository.getTokenFromDataStorage()
        }
        authResult.onSuccess {token ->
            val result = withContext(Dispatchers.IO){
                employeeRepository.getEmployeeById(id,token)
            }
            result.onSuccess {
                _employeeFlow.value = EmpTaskRegState.Success(it)
            }.onFailure {
                _employeeFlow.value = when(it){
                    is HttpException -> EmpTaskRegState.Failure(Exception("${it.code()} - ${it.message()}"))
                    else -> EmpTaskRegState.Failure(Exception("Error during getting employee: Check your connection"))
                }
            }
        }.onFailure {
            _employeeFlow.value = EmpTaskRegState.Failure(Exception("No token found. Please login first."))
        }
    }

    fun getEmployeesList() = viewModelScope.launch {
        _employeesListFlow.value = EmpTaskRegState.Loading

        val authResult = withContext(Dispatchers.IO){
            authRepository.getTokenFromDataStorage()
        }
        authResult.onSuccess {token ->
            val result = withContext(Dispatchers.IO){
                employeeRepository.getEmployeesList(token)
            }
            result.onSuccess {
                _employeesListFlow.value = EmpTaskRegState.Success(it)
            }.onFailure {
                _employeesListFlow.value = when(it){
                    is HttpException -> EmpTaskRegState.Failure(Exception("${it.code()} - ${it.message()}"))
                    else -> EmpTaskRegState.Failure(Exception("Error during getting employees: Check your connection"))
                }
            }

        }.onFailure {
            _employeeFlow.value = EmpTaskRegState.Failure(Exception("No token found. Please login first."))
        }
    }

    fun getEmployeeTaskCount(id:Int) = viewModelScope.launch {
        _employeeTaskCountFlow.value = EmpTaskRegState.Loading
        val authResult = withContext(Dispatchers.IO){
            authRepository.getTokenFromDataStorage()
        }
        authResult.onSuccess {token ->
            val result = withContext(Dispatchers.IO){
                taskRepository.getEmployeeTaskCount(id,token)
            }
            result.onSuccess {
                _employeeTaskCountFlow.value = EmpTaskRegState.Success(it)
            }.onFailure {
                _employeeTaskCountFlow.value = when(it){
                    is HttpException -> EmpTaskRegState.Failure(Exception("${it.code()} - ${it.message()}"))
                    else -> EmpTaskRegState.Failure(Exception("Error during getting task count: Check your connection"))
                }
            }
        }.onFailure {
            _employeeFlow.value = EmpTaskRegState.Failure(Exception("No token found. Please login first."))
        }
    }

    fun searchEmployeeByName(employeeName:String) = viewModelScope.launch {
        _employeesSearchResultFlow.value = EmpTaskRegState.Loading
        val authResult = withContext(Dispatchers.IO){
            authRepository.getTokenFromDataStorage()
        }
        authResult.onSuccess {token ->
            val result = withContext(Dispatchers.IO){
                employeeRepository.getEmployeeByName(employeeName,token)
            }
            result.onSuccess {
                _employeesSearchResultFlow.value = EmpTaskRegState.Success(it)
            }.onFailure {
                _employeesSearchResultFlow.value = when(it){
                    is HttpException -> EmpTaskRegState.Failure(Exception("${it.code()} - ${it.message()}"))
                    else -> EmpTaskRegState.Failure(Exception("Error during getting search result: Check your connection"))
                }
            }
        }.onFailure {
            _employeesSearchResultFlow.value = EmpTaskRegState.Failure(Exception("No token found. Please login first."))
        }
    }

}