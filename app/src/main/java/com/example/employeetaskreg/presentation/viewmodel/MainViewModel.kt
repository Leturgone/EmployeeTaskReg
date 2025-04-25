package com.example.employeetaskreg.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.employeetaskreg.domain.model.CompanyWorker
import com.example.employeetaskreg.domain.model.CompanyWorkerInterface
import com.example.employeetaskreg.domain.model.Task
import com.example.employeetaskreg.domain.repository.EmpTaskRegState
import com.example.employeetaskreg.domain.repository.EmployeeTaskRegRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
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

    private val _profileFlow = MutableStateFlow<EmpTaskRegState<CompanyWorkerInterface>>(EmpTaskRegState.Waiting)

    val profileFlow: StateFlow<EmpTaskRegState<CompanyWorkerInterface>> = _profileFlow

    private val _dirNameFlow = MutableStateFlow<EmpTaskRegState<CompanyWorker.Director>>(EmpTaskRegState.Waiting)

    val dirNameFlow: StateFlow<EmpTaskRegState<CompanyWorker.Director>> = _dirNameFlow

    private val _taskCountFlow = MutableStateFlow<EmpTaskRegState<Int>>(EmpTaskRegState.Waiting)

    val taskCountFlow: StateFlow<EmpTaskRegState<Int>> = _taskCountFlow

    private val _taskListFlow = MutableStateFlow<EmpTaskRegState<List<Task>>>(EmpTaskRegState.Waiting)

    val taskListFlow: StateFlow<EmpTaskRegState<List<Task>>> = _taskListFlow

    private val _userRole = MutableLiveData<String>("1")
    val userRole: LiveData<String> = _userRole

    init {
        getProfile()
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
        if(result is EmpTaskRegState.Success){
            val token = withContext(Dispatchers.IO){
                employeeTaskRegRepository.getTokenFromDataStorage()
            }
            _regFlow.value = EmpTaskRegState.Success(token)
            _loginFlow.value = EmpTaskRegState.Success(token)
        }
        _profileFlow.value = result
    }

    fun getDirById(id:Int) = viewModelScope.launch {
        _dirNameFlow.value = EmpTaskRegState.Loading
        val result = withContext(Dispatchers.IO){
            employeeTaskRegRepository.getDirectorById(id)
        }
        _dirNameFlow.value = result
    }
    fun getTaskCount() = viewModelScope.launch {
        _taskCountFlow.value = EmpTaskRegState.Loading
        val result = withContext(Dispatchers.IO){
            employeeTaskRegRepository.getTaskCount()
        }
        _taskCountFlow.value = result
    }

    fun getTaskList() = viewModelScope.launch {
        _taskListFlow.value = EmpTaskRegState.Loading
        val result = withContext(Dispatchers.IO){
            employeeTaskRegRepository.getTaskList()
        }
        _taskListFlow.value = result

    }


    fun logout() = viewModelScope.launch {
        _loginFlow.value = EmpTaskRegState.Waiting
        _regFlow.value = EmpTaskRegState.Waiting
        async(Dispatchers.IO){
            employeeTaskRegRepository.logout()
        }.await()

    }
}