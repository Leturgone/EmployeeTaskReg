package com.example.employeetaskreg.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
     private val _userRole = MutableLiveData<String>("1")
    val userRole: LiveData<String> = _userRole
    fun setRole(role: String) {
        _userRole.value = role
    }
}