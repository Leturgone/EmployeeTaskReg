package com.example.employeetaskreg.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.employeetaskreg.presentation.ui.components.Employee

class MainViewModel: ViewModel() {
     private val _userRole = MutableLiveData<String>("1")
    val userRole: LiveData<String> = _userRole

    private val _empList  = MutableLiveData<List<Employee>>()
    val empList:LiveData<List<Employee>> = _empList
    fun setRole(role: String) {
        _userRole.value = role
    }
}