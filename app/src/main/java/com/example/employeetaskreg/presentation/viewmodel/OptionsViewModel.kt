package com.example.employeetaskreg.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.employeetaskreg.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OptionsViewModel @Inject constructor(private val authRepository: AuthRepository): ViewModel() {


}