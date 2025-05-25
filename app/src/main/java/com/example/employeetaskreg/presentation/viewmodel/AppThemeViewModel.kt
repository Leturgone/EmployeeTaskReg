package com.example.employeetaskreg.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.employeetaskreg.domain.repository.ThemeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppThemeViewModel @Inject constructor(private val themeRepository: ThemeRepository): ViewModel() {
    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode:StateFlow<Boolean> = _isDarkMode

    init {
        Log.d("AppTheme","Created")
        viewModelScope.launch {
            _isDarkMode.value = themeRepository.getIsDarkTheme()
        }
    }

    fun switchTheme() = viewModelScope.launch {
        val currentTheme = _isDarkMode.value
        _isDarkMode.value = !currentTheme
        themeRepository.saveTheme(!currentTheme)
    }
}