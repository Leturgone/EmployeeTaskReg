package com.example.employeetaskreg.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.employeetaskreg.domain.model.CompanyWorker
import com.example.employeetaskreg.domain.model.CompanyWorkerInterface
import com.example.employeetaskreg.domain.repository.AuthRepository
import com.example.employeetaskreg.domain.repository.DirectorRepository
import com.example.employeetaskreg.domain.repository.EmpTaskRegState
import com.example.employeetaskreg.domain.repository.ProfileRepository
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
class ProfileScreenViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val directorRepository: DirectorRepository,
    private val authRepository: AuthRepository, private val taskRepository: TaskRepository) : ViewModel() {

    private val _profileFlow = MutableStateFlow<EmpTaskRegState<CompanyWorkerInterface>>(EmpTaskRegState.Waiting)

    val profileFlow: StateFlow<EmpTaskRegState<CompanyWorkerInterface>> = _profileFlow

    private val _taskCountFlow = MutableStateFlow<EmpTaskRegState<Int>>(EmpTaskRegState.Waiting)

    val taskCountFlow: StateFlow<EmpTaskRegState<Int>> = _taskCountFlow

    private val _directorFlow = MutableStateFlow<EmpTaskRegState<CompanyWorker.Director>>(EmpTaskRegState.Waiting)

    val directorFlow: StateFlow<EmpTaskRegState<CompanyWorker.Director>> = _directorFlow

    fun getProfile() = viewModelScope.launch {
        _profileFlow.value = EmpTaskRegState.Loading
        val authResult = withContext(Dispatchers.IO){
            authRepository.getTokenFromDataStorage()
        }
        authResult.onSuccess {token ->
            val result = withContext(Dispatchers.IO) {
                profileRepository.getProfile(token)
            }
            result.onSuccess {
                _profileFlow.value = EmpTaskRegState.Success(it)
            }.onFailure {
                _profileFlow.value = when(it){
                    is HttpException -> EmpTaskRegState.Failure(Exception("${it.code()} - ${it.message()}"))
                    else -> EmpTaskRegState.Failure(Exception("Error during loading profile: Check your connection"))
                }
            }
        }.onFailure {
            _profileFlow.value = EmpTaskRegState.Failure(Exception("No token found. Please login first."))
        }
    }

    fun clearProfile() = viewModelScope.launch {
        _profileFlow.value = EmpTaskRegState.Waiting
    }
    fun getProfileTaskCount() = viewModelScope.launch {
        _taskCountFlow.value = EmpTaskRegState.Loading
        val authResult = withContext(Dispatchers.IO){
            authRepository.getTokenFromDataStorage()
        }
        authResult.onSuccess {token ->
            val result = withContext(Dispatchers.IO){
                taskRepository.getTaskCount(token)
            }
            result.onSuccess {
                _taskCountFlow.value  = EmpTaskRegState.Success(it)
            }.onFailure {
                _taskCountFlow.value = when(it){
                    is HttpException -> EmpTaskRegState.Failure(Exception("${it.code()} - ${it.message()}"))
                    else -> EmpTaskRegState.Failure(Exception("Error during getting task count: Check your connection"))
                }
            }

        }.onFailure {
            _taskCountFlow.value = EmpTaskRegState.Failure(Exception("No token found. Please login first."))
        }

    }

    fun getDirById(id:Int) = viewModelScope.launch {
        _directorFlow.value = EmpTaskRegState.Loading
        val authResult = withContext(Dispatchers.IO){
            authRepository.getTokenFromDataStorage()
        }
        authResult.onSuccess {token ->
            val result = withContext(Dispatchers.IO){
                directorRepository.getDirectorById(id,token)
            }
            result.onSuccess {
                _directorFlow.value = EmpTaskRegState.Success(it)
            }.onFailure {
                _directorFlow.value = when(it){
                    is HttpException -> EmpTaskRegState.Failure(Exception("${it.code()} - ${it.message()}"))
                    else -> EmpTaskRegState.Failure(Exception("Error during login: Check your connection"))
                }
            }
        }
    }

}