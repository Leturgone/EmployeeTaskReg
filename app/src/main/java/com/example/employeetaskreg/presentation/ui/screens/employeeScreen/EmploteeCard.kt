package com.example.employeetaskreg.presentation.ui.screens.employeeScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.employeetaskreg.domain.model.Task
import com.example.employeetaskreg.domain.repository.EmpTaskRegState
import com.example.employeetaskreg.presentation.ui.screens.TaskCard
import com.example.employeetaskreg.presentation.viewmodel.EmployeesViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeCard(employeeName:String, employeeId:Int, setListItem:Boolean = false, viewModel: EmployeesViewModel = hiltViewModel(),
                 clickFun: (() -> Unit)? = null) {

    val sheetState = rememberModalBottomSheetState()
    val taskCount = viewModel.employeeTaskCountFlow.collectAsState()
    val employee = viewModel.employeeFlow.collectAsState()

    val employeeCurrentTask = viewModel.employeeCurrentTaskFlow.collectAsState()

    var taskCountText by remember { mutableStateOf("Задач решено: ") }
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(73.dp)
            .let {
                when (setListItem) {
                    true -> it.clickable(onClick = clickFun!!)
                    false -> it.clickable { showBottomSheet = true }
                }
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        AvatarNameSec(avatar = employeeName.substringAfter(" ").replace(".",""),
            name = employeeName,Modifier.padding(16.dp))
    }

    if (showBottomSheet) {
        LaunchedEffect(Unit){
            viewModel.getEmployeeById(employeeId)
            viewModel.getEmployeeTaskCount(employeeId)
            viewModel.getEmployeeCurrentTask(employeeId)
        }
        ModalBottomSheet(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .height(700.dp)
                    .padding(16.dp),Arrangement.SpaceEvenly ,
                Alignment.Start) {

                when(employee.value){
                    is EmpTaskRegState.Failure -> {
                        Text(
                            text = "Не удалось загрузить данные о сотруднике: $employeeName",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .width(200.dp)
                        )
                    }
                    EmpTaskRegState.Loading -> CircularProgressIndicator()
                    is EmpTaskRegState.Success -> {
                        Text(
                            text = employeeName,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .width(200.dp)
                        )
                        taskCountText = when(taskCount.value){
                            is EmpTaskRegState.Failure -> "Не найдено количество решенных задач"
                            EmpTaskRegState.Loading -> "Загрузка..."
                            is EmpTaskRegState.Success -> "Задач решено: ${(taskCount.value as EmpTaskRegState.Success<Int>).result}"
                            EmpTaskRegState.Waiting -> "Задач решено: "
                        }
                        Text(
                            text = taskCountText,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .width(300.dp)
                        )
                        Text(
                            text = "Текущая задача",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .width(300.dp)
                        )
                        when(employeeCurrentTask.value){
                            is EmpTaskRegState.Failure -> {
                                Text(
                                    text = "Нет текущей задачи",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.Black,
                                    textAlign = TextAlign.Start,
                                    modifier = Modifier
                                        .width(300.dp)
                                )
                            }
                            EmpTaskRegState.Loading -> CircularProgressIndicator()
                            is EmpTaskRegState.Success -> {
                                TaskCard(
                                    task = (employeeCurrentTask.value as EmpTaskRegState.Success<Task>).result,
                                    role = "director",
                                )
                            }
                            EmpTaskRegState.Waiting -> null
                        }

                    }
                    EmpTaskRegState.Waiting -> null
                }


                Spacer(modifier = Modifier.height(220.dp))

            }

        }
    }

}

