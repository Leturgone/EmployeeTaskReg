package com.example.employeetaskreg.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.employeetaskreg.domain.repository.AuthRepository
import com.example.employeetaskreg.domain.repository.EmpTaskRegState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class AuthScreenViewModel @Inject constructor(private val authRepository: AuthRepository):ViewModel(){

    private val _loginFlow = MutableStateFlow<EmpTaskRegState<String>>(EmpTaskRegState.Waiting)

    val loginFlow: StateFlow<EmpTaskRegState<String>> = _loginFlow

    private val _regFlow = MutableStateFlow<EmpTaskRegState<String>>(EmpTaskRegState.Waiting)

    val regFlow: StateFlow<EmpTaskRegState<String>> = _regFlow


    fun login(login: String, password: String) = viewModelScope.launch{
        _loginFlow.value = EmpTaskRegState.Loading
        val result = withContext(Dispatchers.IO){
            authRepository.login(login, password)
        }
        result.onSuccess {
            _loginFlow.value = EmpTaskRegState.Success(it)
        }.onFailure {
            _loginFlow.value = when(it){
                is HttpException ->EmpTaskRegState.Failure(Exception("${it.code()} - ${it.message()}"))
                else -> EmpTaskRegState.Failure(Exception("Error during login: Check your connection"))
            }
        }

    }
    fun register(login: String, password: String, name: String, dirName: String) = viewModelScope.launch{
        _regFlow.value = EmpTaskRegState.Loading
        val result = withContext(Dispatchers.IO){
            authRepository.register(login, password, name, dirName)
        }
        result.onSuccess {
            _regFlow.value = EmpTaskRegState.Success(it)
        }.onFailure {
            _regFlow.value = when(it){
                is HttpException ->EmpTaskRegState.Failure(Exception("${it.code()} - ${it.message()}"))
                else -> EmpTaskRegState.Failure(Exception("Error during register: Check your connection"))
            }
        }
    }


    fun logout() = viewModelScope.launch {
        val oldValue = _loginFlow.value
        _loginFlow.value = EmpTaskRegState.Loading
        _regFlow.value = EmpTaskRegState.Loading
        val result = async(Dispatchers.IO){
            authRepository.logout()
        }.await()
        result.onSuccess {
            _loginFlow.value = EmpTaskRegState.Waiting
            _regFlow.value = EmpTaskRegState.Waiting
        }.onFailure {
            _loginFlow.value = oldValue
            _regFlow.value = oldValue
        }
    }
}