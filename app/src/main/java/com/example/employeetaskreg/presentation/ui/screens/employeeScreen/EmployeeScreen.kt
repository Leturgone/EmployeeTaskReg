package com.example.employeetaskreg.presentation.ui.screens.employeeScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.employeetaskreg.R
import com.example.employeetaskreg.domain.model.CompanyWorker
import com.example.employeetaskreg.domain.repository.EmpTaskRegState
import com.example.employeetaskreg.presentation.viewmodel.EmployeesViewModel
import com.example.employeetaskreg.presentation.viewmodel.SearchViewModel

@Composable
fun EmployeesScreen(viewModel: EmployeesViewModel = hiltViewModel(),searchViewModel: SearchViewModel = hiltViewModel()) {

    val employeeListState = viewModel.employeesListFlow.collectAsState()
    val searchText = searchViewModel.searchText.collectAsState()

    var errorMessage by remember { mutableStateOf("") }
    var employeeList by remember { mutableStateOf(emptyList<CompanyWorker.Employee>()) }


    LaunchedEffect(Unit){
        viewModel.getEmployeesList()
    }

    Box(){

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.employees),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                modifier = Modifier.padding(bottom = 16.dp, top = 16.dp)
            )

            SearchSec(searchViewModel)
            when(employeeListState.value){
                is EmpTaskRegState.Failure -> {
                    errorMessage =
                        (employeeListState.value as EmpTaskRegState.Failure).exception.toString()
                    Column {
                        Text(text = errorMessage)
                        Button(onClick = { viewModel.searchEmployeeByName(searchText.value) }, ) {
                            Text(text = stringResource(id = R.string.update_search))
                        }
                    }
                }
                EmpTaskRegState.Loading -> CircularProgressIndicator()
                is EmpTaskRegState.Success -> {
                    employeeList = (employeeListState.value as EmpTaskRegState.Success<List<CompanyWorker.Employee>>).result
                    if (employeeList.isEmpty()){
                        Text(
                            text = stringResource(id = R.string.employees_not_found),
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            fontSize = 25.sp,
                            modifier = Modifier.padding(bottom = 16.dp, top = 16.dp)
                        )
                    }
                }
                EmpTaskRegState.Waiting -> null
            }


            if (employeeList.isNotEmpty()){
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(employeeList.size) {
                        val employee = employeeList[it]
                        Spacer(modifier = Modifier.height(30.dp))
                        EmployeeCard(
                            employeeName = employee.name,
                            employeeId = employee.id,
                            viewModel = viewModel
                        ){
                            searchViewModel.addToSearchHistory(employee.name)
                        }
                    }
                }
            }

        }
    }

}


