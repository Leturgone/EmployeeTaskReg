package com.example.employeetaskreg.presentation.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
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
import com.example.employeetaskreg.domain.model.Task
import com.example.employeetaskreg.domain.repository.EmpTaskRegState
import com.example.employeetaskreg.presentation.ui.screens.employeeScreen.AvatarNameSec
import com.example.employeetaskreg.presentation.ui.screens.tasksScreen.DownloadFileCard
import com.example.employeetaskreg.presentation.ui.screens.tasksScreen.FileCard
import com.example.employeetaskreg.presentation.ui.screens.tasksScreen.localDateToMillis
import com.example.employeetaskreg.presentation.ui.theme.GreenSoft
import com.example.employeetaskreg.presentation.ui.theme.RedSoft
import com.example.employeetaskreg.presentation.ui.theme.YellowSoft
import com.example.employeetaskreg.presentation.viewmodel.ReportViewModel
import com.example.employeetaskreg.presentation.viewmodel.TasksViewModel
import java.io.File
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskCard(task: Task,
             role:String,
             taskViewModel: TasksViewModel = hiltViewModel(),
             reportViewModel: ReportViewModel = hiltViewModel()) {

    val sheetState = rememberModalBottomSheetState()

    val loadFile = stringResource(id = R.string.upload_order)
    var fileTitle by remember { mutableStateOf(loadFile) }

    var showBottomSheet by remember { mutableStateOf(false) }

    val addReportState = reportViewModel.addReportFlow.collectAsState()

    val downloadFile = stringResource(id = R.string.download_file)
    var downloadFileTitle by remember { mutableStateOf(downloadFile) }

    val downloadTask = taskViewModel.downloadTask.collectAsState()
    val reportByTask = reportViewModel.reportByTaskIdFlow.collectAsState()

    var showToast by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(Unit){
        reportViewModel.getReportByTaskId(task.id)
    }

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
                    text = "${stringResource(id = R.string.task)} ${task.id}",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 16.dp, start = 16.dp),
                    fontSize = 16.sp
                )

            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .padding(end = 16.dp)
                .padding(bottom = 8.dp),
                contentAlignment = Alignment.BottomEnd){
                if(role == "director"){
                    task.employeeName?.let {
                        AvatarNameSec(avatar = it.substringAfter(" ").replace(".",""),
                            name = it, modifier =Modifier)
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
                    tint = when(task.status){
                        "В процессе" -> YellowSoft
                        "Завершена" -> GreenSoft
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
                taskViewModel.resetDownloadState()
                reportViewModel.resetAddReportState()
                showBottomSheet = false
            },
            sheetState = sheetState
        ) {
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
                        .padding(16.dp), Arrangement.SpaceEvenly,
                    Alignment.Start
                ) {
                    when (addReportState.value) {
                        is EmpTaskRegState.Failure -> {
                            LaunchedEffect(addReportState.value) {
                                showToast = true
                                errorMessage = (addReportState.value as EmpTaskRegState.Failure).exception.toString()
                            }
                        }
                        EmpTaskRegState.Loading -> CircularProgressIndicator()
                        is EmpTaskRegState.Success -> {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "okIcon",
                                modifier = Modifier.size(35.dp),
                                tint = GreenSoft
                            )
                        }

                        EmpTaskRegState.Waiting -> null
                    }
                    Text(
                        text = "${stringResource(id = R.string.task)} ${task.id}",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .width(200.dp)
                    )
                    Text(
                        text = task.title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .width(200.dp)
                    )
                    Text(
                        text = task.status,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        color = when(task.status){
                            "В процессе" -> YellowSoft
                            "Завершена" -> GreenSoft
                            else -> RedSoft
                                                 },
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .width(200.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.desc),
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .width(300.dp)
                    )
                    Text(
                        text = task.taskDesc,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .width(200.dp)
                    )
                    Text(
                        text = "${stringResource(id = R.string.limit)} ${task.startDate} ${stringResource(
                            id = R.string.to)} ${task.endDate}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .width(400.dp)
                    )
                    task.documentName?.let {
                        DownloadFileCard(fileFunc = downloadFileTitle){
                            taskViewModel.downloadTask(task.id)
                        }
                    }

                    downloadFileTitle = when(downloadTask.value){
                        is EmpTaskRegState.Failure -> stringResource(id = R.string.error_while_loading)
                        EmpTaskRegState.Loading -> stringResource(id = R.string.loading)
                        is EmpTaskRegState.Success -> (downloadTask.value as EmpTaskRegState.Success<File>).result.absolutePath
                        EmpTaskRegState.Waiting -> downloadFile
                    }
                    when (role) {
                        "director" -> {
                            Text(
                                text = stringResource(id = R.string.worker),
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                textAlign = TextAlign.Start,
                                modifier = Modifier
                                    .width(200.dp)
                            )
                            task.employeeName?.let {
                                AvatarNameSec(
                                    avatar = it.substringAfter(" ").replace(".", ""),
                                    name = it, modifier = Modifier
                                )
                            }

                        }

                        "employee" -> {
                            FileCard(fileFunc = fileTitle) {
                                it?.let { fileTitle = it.lastPathSegment ?: loadFile }
                                reportViewModel.setSelectedReportFileUri(it)
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                when(reportByTask.value){
                                    is EmpTaskRegState.Failure -> {
                                        ExtendedFloatingActionButton(
                                            containerColor = MaterialTheme.colorScheme.primary,
                                            text = { Text(text = stringResource(id = R.string.create_resp)) },
                                            icon = {
                                                Icon(
                                                    imageVector = Icons.Default.MailOutline,
                                                    contentDescription = "createRespButton"
                                                )
                                            },
                                            onClick = {
                                                if (task.directorId != null && task.employeeId != null) {
                                                    reportViewModel.addReport(
                                                        reportDate = LocalDate.now().localDateToMillis()
                                                            .toString(),
                                                        documentName = null,
                                                        taskId = task.id,
                                                        directorId = task.directorId,
                                                        employeeId = task.employeeId
                                                    )
                                                }
                                            }
                                        )
                                    }
                                    EmpTaskRegState.Loading -> CircularProgressIndicator()
                                    is EmpTaskRegState.Success -> {
                                        ExtendedFloatingActionButton(
                                            containerColor = MaterialTheme.colorScheme.primary,
                                            text = { Text(text = stringResource(id = R.string.update_report)) },
                                            icon = {
                                                Icon(
                                                    imageVector = Icons.Default.Update,
                                                    contentDescription = "updateRespButton"
                                                )
                                            },
                                            onClick = {
                                                reportViewModel.updateReport(
                                                    (reportByTask.value as EmpTaskRegState.Success<Report>).result.id
                                                )
                                            }
                                        )
                                    }
                                    EmpTaskRegState.Waiting -> null
                                }

                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(220.dp))
                }
            }

        }
    }
}
