package com.example.employeetaskreg.data.repsitory

import com.example.employeetaskreg.domain.repository.ThemeRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ThemeRepositoryImpl @Inject constructor(private val dataStoreManager: DataStoreManager):ThemeRepository {
    override suspend fun getIsDarkTheme(): Boolean {
        return dataStoreManager.isDarkMode.first()
    }

    override suspend fun saveTheme(isDarkMode: Boolean) {
        dataStoreManager.setDarkMode(isDarkMode)
    }

}