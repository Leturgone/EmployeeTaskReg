package com.example.employeetaskreg.presentation.ui.screens.employeeScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.employeetaskreg.R
import com.example.employeetaskreg.presentation.viewmodel.EmployeesViewModel
import com.example.employeetaskreg.presentation.viewmodel.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchSec(searchViewModel: SearchViewModel,
              viewModel: EmployeesViewModel = hiltViewModel()) {
    val textFieldState = rememberTextFieldState()

    var expanded by rememberSaveable { mutableStateOf(false) }


    SearchBar(
        inputField = { SearchBarDefaults.InputField(
            state = textFieldState,
            shape = RoundedCornerShape(20.dp),
            onSearch = {
                if (textFieldState.text.isNotEmpty()){
                    expanded = false
                    searchViewModel.setSearchTest(textFieldState.text.toString())
                    viewModel.searchEmployeeByName(textFieldState.text.toString())
                }
                       },
            expanded = expanded,
            onExpandedChange = {expanded = it},
            placeholder = { Text(text = stringResource(id = R.string.search_emp)) },
            leadingIcon = { Icon(
                Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.clickable {
                    if (textFieldState.text.isNotEmpty()){
                        expanded = false
                        searchViewModel.setSearchTest(textFieldState.text.toString())
                        viewModel.searchEmployeeByName(textFieldState.text.toString())
                    }
                }
            ) },
            trailingIcon = {
                if (textFieldState.text.isNotEmpty()) {
                    Text(
                        text = "Очистить",
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier
                            .clickable {
                                textFieldState.clearText()
                                expanded = false
                            }.padding(horizontal = 8.dp)
                    )
                } else {
                    Icon(
                        Icons.Filled.Close,
                        contentDescription = "Close",
                        tint = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.clickable {
                            expanded = false
                        }
                    )
                }
            }
        )},
        expanded = expanded,
        onExpandedChange = {expanded = it}, modifier = Modifier.padding(bottom = 16.dp)
    ) {

    }
}