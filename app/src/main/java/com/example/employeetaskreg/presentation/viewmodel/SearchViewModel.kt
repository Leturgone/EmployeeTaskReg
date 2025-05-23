package com.example.employeetaskreg.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.employeetaskreg.domain.repository.EmpTaskRegState
import com.example.employeetaskreg.domain.repository.SearchHistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchHistoryRepository: SearchHistoryRepository):ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText
    private val _searchHistoryListFlow = MutableStateFlow<EmpTaskRegState<List<String>>>(EmpTaskRegState.Waiting)

    val searchHistoryListFlow: StateFlow<EmpTaskRegState<List<String>>> = _searchHistoryListFlow

    fun setSearchTest(text:String){
        _searchText.value = text
    }

    fun  addToSearchHistory(element: String) = viewModelScope.launch {
        _searchHistoryListFlow.value = EmpTaskRegState.Loading
        val result = withContext(Dispatchers.IO){
            searchHistoryRepository.updateSearchHistory(element)
        }
        result.onSuccess {
            getSearchHistory()
        }.onFailure {
            _searchHistoryListFlow.value = EmpTaskRegState.Failure(Exception("Error during get search history"))
        }
    }

    fun getSearchHistory() = viewModelScope.launch {
        _searchHistoryListFlow.value = EmpTaskRegState.Loading
        val result = withContext(Dispatchers.IO){
            searchHistoryRepository.getSearchHistory()
        }
        result.onSuccess {
            _searchHistoryListFlow.value = EmpTaskRegState.Success(it)
        }.onFailure {
            _searchHistoryListFlow.value = EmpTaskRegState.Failure(Exception("Error during get search history"))
        }
    }

    fun clearSearchHistory() = viewModelScope.launch {
        _searchHistoryListFlow.value = EmpTaskRegState.Loading
        val result = withContext(Dispatchers.IO){
            searchHistoryRepository.clearSearchHistory()
        }
        result.onSuccess {
            getSearchHistory()
        }.onFailure {
            _searchHistoryListFlow.value = EmpTaskRegState.Failure(Exception("Error during clear history"))
        }
    }
}