package com.example.employeetaskreg.data.repsitory

import android.util.Log
import com.example.employeetaskreg.domain.repository.SearchHistoryRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SearchHistoryRepositoryImpl @Inject constructor( private val dataStoreManager: DataStoreManager):SearchHistoryRepository {

    override suspend fun updateSearchHistory(element:String): Result<Unit> {
        return try {
            val oldHistory = dataStoreManager.searchHistoryFlow.first().toMutableList()
            oldHistory.add(element)
            val updatedHistory = oldHistory.distinct().takeLast(10).reversed()
            val result = dataStoreManager.storeSearchHistory(updatedHistory)
            Result.success(result)
        }catch (e:Exception){
            Log.e("updateSearchHistory",e.toString())
            Result.failure(e)
        }
    }

    override suspend fun getSearchHistory(): Result<List<String>> {
        return try {
            val result = dataStoreManager.searchHistoryFlow.first()
            Result.success(result)
        }catch (e:Exception){
            Log.e("getSearchHistory",e.toString())
            Result.failure(e)
        }
    }

    override suspend fun clearSearchHistory(): Result<Unit> {
        return try {
            val result = dataStoreManager.clearSearchHistory()
            Result.success(result)
        }catch (e:Exception){
            Log.e("clearSearchHistory",e.toString())
            Result.failure(e)
        }
    }

}