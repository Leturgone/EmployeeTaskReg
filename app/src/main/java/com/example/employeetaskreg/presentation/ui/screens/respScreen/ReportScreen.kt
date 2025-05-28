package com.example.employeetaskreg.presentation.ui.screens.respScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.employeetaskreg.domain.model.CompanyWorkerInterface
import com.example.employeetaskreg.domain.model.Report
import com.example.employeetaskreg.domain.repository.EmpTaskRegState
import com.example.employeetaskreg.presentation.ui.screens.CustomToastMessage
import com.example.employeetaskreg.presentation.viewmodel.ProfileViewModel
import com.example.employeetaskreg.presentation.viewmodel.ReportViewModel

@Composable
fun ReportScreen(
    profileViewModel:ProfileViewModel,
    reportViewModel: ReportViewModel = hiltViewModel()){

    val profileState = profileViewModel.profileFlow.collectAsState()
    val respListState = reportViewModel.reportListFlow.collectAsState()

    var showToast by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    var role by remember { mutableStateOf("") }

    Box {
        CustomToastMessage(
            message = errorMessage,
            isVisible = showToast,
            onDismiss = { showToast = false },
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.responds),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                modifier = Modifier.padding(bottom = 16.dp, top = 16.dp)
            )
            when(respListState.value){
                is EmpTaskRegState.Failure -> LaunchedEffect(respListState.value) {
                    showToast = true
                    errorMessage = (respListState.value as EmpTaskRegState.Failure).exception.toString()
                }
                EmpTaskRegState.Loading -> CircularProgressIndicator()
                is EmpTaskRegState.Success -> {
                    val respList = (respListState.value as EmpTaskRegState.Success<List<Report>>).result
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(respList.size) {
                            val resp = respList[it]
                            Spacer(modifier = Modifier.height(30.dp))
                            ReportCard(resp, role = role)
                        }
                    }
                }
                EmpTaskRegState.Waiting -> null
            }


        }
        when(profileState.value){
            is EmpTaskRegState.Failure -> LaunchedEffect(profileState.value) {
                showToast = true
                errorMessage = (profileState.value as EmpTaskRegState.Failure).exception.toString()
            }
            EmpTaskRegState.Loading -> CircularProgressIndicator()
            is EmpTaskRegState.Success -> {
                when((profileState.value as EmpTaskRegState.Success<CompanyWorkerInterface>).result){
                    is CompanyWorker.Director ->{
                        role = "director"

                    }
                    is CompanyWorker.Employee ->{
                        role = "employee"
                    }
                }
                LaunchedEffect(Unit){
                    reportViewModel.getReportList()
                }

            }
            EmpTaskRegState.Waiting -> null
        }
    }
}