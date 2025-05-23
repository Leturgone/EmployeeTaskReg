package com.example.employeetaskreg.domain.repository

interface SearchHistoryRepository {

    suspend fun updateSearchHistory(element:String): Result<Unit>

    suspend fun getSearchHistory(): Result<List<String>>

    suspend fun clearSearchHistory():Result<Unit>
}