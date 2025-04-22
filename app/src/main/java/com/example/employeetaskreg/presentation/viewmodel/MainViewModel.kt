package com.example.employeetaskreg.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.employeetaskreg.domain.model.CompanyWorker
import com.example.employeetaskreg.domain.repository.EmpTaskRegState
import com.example.employeetaskreg.domain.repository.EmployeeTaskRegRepository
import com.example.employeetaskreg.presentation.ui.components.Employee
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val employeeTaskRegRepository: EmployeeTaskRegRepository): ViewModel() {

    private val _loginFlow = MutableStateFlow<EmpTaskRegState<String>>(EmpTaskRegState.Waiting)

    val loginFlow: StateFlow<EmpTaskRegState<String>> = _loginFlow

    private val _regFlow = MutableStateFlow<EmpTaskRegState<String>>(EmpTaskRegState.Waiting)

    val regFlow: StateFlow<EmpTaskRegState<String>> = _regFlow

    private val _profileFlow = MutableStateFlow<EmpTaskRegState<CompanyWorker>>(EmpTaskRegState.Waiting)

    val profileFlow: StateFlow<EmpTaskRegState<CompanyWorker>> = _profileFlow

    private val _userRole = MutableLiveData<String>("1")
    val userRole: LiveData<String> = _userRole

    private val _empList  = MutableLiveData<List<Employee>>()
    val empList:LiveData<List<Employee>> = _empList
    fun setRole(role: String) {
        _userRole.value = role
    }

    fun register(login: String, password: String, name: String, dirName: String) = viewModelScope.launch{
       _regFlow.value = EmpTaskRegState.Loading
        val result = withContext(Dispatchers.IO){
            employeeTaskRegRepository.register(login, password, name, dirName)
        }
        _regFlow.value = result
    }
    fun login(login: String, password: String) = viewModelScope.launch{
        _loginFlow.value = EmpTaskRegState.Loading
        val result = withContext(Dispatchers.IO){
            employeeTaskRegRepository.login(login, password)
        }
        _loginFlow.value = result
    }

    fun getProfile() = viewModelScope.launch {
        _profileFlow.value = EmpTaskRegState.Loading
        val result = withContext(Dispatchers.IO){
            employeeTaskRegRepository.getProfile()
        }
        _profileFlow.value = result
    }
}