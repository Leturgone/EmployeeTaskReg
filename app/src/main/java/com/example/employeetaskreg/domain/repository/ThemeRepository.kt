package com.example.employeetaskreg.domain.repository

interface ThemeRepository {

    suspend fun getIsDarkTheme():Boolean
    suspend fun saveTheme(isDarkMode:Boolean)
}