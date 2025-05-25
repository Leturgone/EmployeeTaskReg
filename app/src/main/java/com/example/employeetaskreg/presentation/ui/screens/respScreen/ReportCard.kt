package com.example.employeetaskreg.presentation.ui.screens.respScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.employeetaskreg.R
import com.example.employeetaskreg.domain.model.Report
import com.example.employeetaskreg.domain.repository.EmpTaskRegState
import com.example.employeetaskreg.presentation.ui.screens.CustomToastMessage
import com.example.employeetaskreg.presentation.ui.screens.employeeScreen.AvatarNameSec
import com.example.employeetaskreg.presentation.ui.screens.tasksScreen.DownloadFileCard
import com.example.employeetaskreg.presentation.ui.theme.DarkGray
import com.example.employeetaskreg.presentation.ui.theme.GreenSoft
import com.example.employeetaskreg.presentation.ui.theme.RedSoft
import com.example.employeetaskreg.presentation.ui.theme.YellowSoft
import com.example.employeetaskreg.presentation.viewmodel.ReportViewModel
import java.io.File

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ReportCard(report:Report, role:String,
               reportViewModel: ReportViewModel = hiltViewModel()){


    val downloadFile = stringResource(id = R.string.download_order)
    var fileTitle by remember { mutableStateOf(downloadFile) }

    val downloadReport = reportViewModel.downloadReport.collectAsState()

    val markReport = reportViewModel.markReport.collectAsState()

    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    var showToast by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .clickable { showBottomSheet = true },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Box(){
            Box(modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopStart){
                Text(
                    text = "${stringResource(id = R.string.resp_for_task)} ${report.taskId}",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 16.dp, start = 16.dp),
                    fontSize = 16.sp
                )

            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .padding(bottom = 8.dp),
                contentAlignment = Alignment.BottomEnd){
                if (role == "1") {
                    report.employeeName?.let {
                        AvatarNameSec(
                            avatar = it.substringAfter(" ").replace(".",""),
                                    name = it,
                            modifier = Modifier.padding(start = 190.dp, end = 15.dp)
                        )
                    }

                }
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .padding(top = 8.dp)
                .padding(end = 16.dp),
                contentAlignment = Alignment.TopEnd){
                Icon(imageVector = Icons.Filled.Circle,
                    contentDescription ="StatusIcon",
                    tint = when(report.status){
                        "Ожидание" -> YellowSoft
                        "Принято" -> GreenSoft
                        else -> RedSoft
                    }
                )

            }
        }
    }
    if (showBottomSheet) {
        ModalBottomSheet(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            onDismissRequest = {
                reportViewModel.resetDownloadState()
                reportViewModel.resetMarkState()
                showBottomSheet = false
            },
            sheetState = sheetState
        ) {
            val reportFlow = reportViewModel.reportFlow.collectAsState()
            LaunchedEffect(Unit){
                reportViewModel.getReportById(report.id)
            }
            Box {
                CustomToastMessage(
                    message = errorMessage,
                    isVisible = showToast,
                    onDismiss = { showToast = false },
                )
                Column(
                    Modifier
                        .fillMaxWidth()
                        .height(700.dp)
                        .padding(16.dp),
                    Arrangement.SpaceEvenly , Alignment.Start) {
                    when (reportFlow.value) {
                        is EmpTaskRegState.Failure -> {
                            LaunchedEffect(reportFlow.value) {
                                showToast = true
                                errorMessage =
                                    (reportFlow.value as EmpTaskRegState.Failure).exception.toString()
                            }
                        }

                        EmpTaskRegState.Loading -> CircularProgressIndicator()
                        is EmpTaskRegState.Success -> {
                            val loadedReport =
                                (reportFlow.value as EmpTaskRegState.Success<Report>).result

                            Text(
                                text = "${stringResource(id = R.string.resp_for_task)} ${loadedReport.taskId}",
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                textAlign = TextAlign.Start,
                                modifier = Modifier
                                    .width(500.dp)
                            )
                            Text(
                                text = loadedReport.status,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Normal,
                                color = when (loadedReport.status) {
                                    "Ожидание" -> YellowSoft
                                    "Принято" -> GreenSoft
                                    else -> RedSoft
                                },
                                textAlign = TextAlign.Start,
                                modifier = Modifier
                                    .width(200.dp)
                            )
                            Text(
                                text = loadedReport.reportDate,
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                textAlign = TextAlign.Start,
                                modifier = Modifier
                                    .width(300.dp)
                            )
                            if (role == "director") {
                                loadedReport.employeeName?.let {
                                    AvatarNameSec(
                                        avatar = it.substringAfter(" ").replace(".", ""),
                                        name = it,
                                        modifier = Modifier
                                    )
                                }
                            }

                            DownloadFileCard(fileTitle) {
                                reportViewModel.downloadReport(loadedReport.id)
                            }

                            fileTitle = when (downloadReport.value) {
                                is EmpTaskRegState.Failure -> stringResource(id = R.string.error_while_loading)
                                EmpTaskRegState.Loading -> stringResource(id = R.string.loading)
                                is EmpTaskRegState.Success -> (downloadReport.value as EmpTaskRegState.Success<File>).result.absolutePath
                                EmpTaskRegState.Waiting -> downloadFile
                            }

                            when (role) {
                                "director" -> {
                                    Column {
                                        Row(Modifier.fillMaxWidth()) {
                                            Button(
                                                onClick = {
                                                    reportViewModel.markReport(
                                                        loadedReport.id,
                                                        true
                                                    )
                                                },
                                                colors = ButtonDefaults.buttonColors(GreenSoft),
                                                modifier = Modifier.weight(1f)
                                            ) {
                                                Text(
                                                    text = stringResource(id = R.string.get),
                                                    color = DarkGray
                                                )

                                            }
                                            Spacer(modifier = Modifier.width(50.dp))
                                            Button(
                                                onClick = {
                                                    reportViewModel.markReport(
                                                        loadedReport.id,
                                                        false
                                                    )
                                                },
                                                colors = ButtonDefaults.buttonColors(RedSoft),
                                                modifier = Modifier.weight(1f)
                                            ) {
                                                Text(
                                                    text = stringResource(id = R.string.back),
                                                    color = DarkGray
                                                )

                                            }
                                        }
                                        when (markReport.value) {
                                            is EmpTaskRegState.Failure -> LaunchedEffect(markReport.value) {
                                                showToast = true
                                                errorMessage =
                                                    (markReport.value as EmpTaskRegState.Failure).exception.toString()
                                            }

                                            EmpTaskRegState.Loading -> CircularProgressIndicator()
                                            is EmpTaskRegState.Success -> {
                                                LaunchedEffect(Unit) {
                                                    reportViewModel.getReportById(loadedReport.id)
                                                    reportViewModel.resetMarkState()
                                                    reportViewModel.resetDownloadState()
                                                }
                                            }
                                            EmpTaskRegState.Waiting -> null
                                        }

                                    }
                                }
                            }
                        }

                        EmpTaskRegState.Waiting -> null
                    }
                    Spacer(modifier = Modifier.height(220.dp))
                }

            }
        }
    }

}