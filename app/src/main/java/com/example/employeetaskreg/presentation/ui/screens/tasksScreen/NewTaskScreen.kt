package com.example.employeetaskreg.presentation.ui.screens.tasksScreen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material.icons.filled.Today
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.employeetaskreg.R
import com.example.employeetaskreg.presentation.ui.screens.employeeScreen.EmployeeCard
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


fun convertMillisToDate(millis: Long): String {
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yy")
        .withZone(ZoneId.systemDefault())
    return formatter.format(Instant.ofEpochMilli(millis))
}
fun LocalDate.localDateToMillis(): Long {
    val zoneId = ZoneId.systemDefault()
    val zonedDateTime: ZonedDateTime = this.atStartOfDay(zoneId)
    val instant = zonedDateTime.toInstant()
    return instant.toEpochMilli()
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTaskScreen(navController: NavHostController,employeeName:String? = null) {
    var taskTitle  by remember { mutableStateOf("") }
    var taskDesc  by remember { mutableStateOf("") }

    val datePickerState = rememberDatePickerState()

    var startDatePickerShow by remember { mutableStateOf(false) }
    var endDatePickerShow by remember { mutableStateOf(false) }

    var startDateMills by remember { mutableStateOf(LocalDate.now().localDateToMillis()) }

    var endDateMills by remember { mutableLongStateOf(LocalDate.now().localDateToMillis()) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.new_task),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            modifier = Modifier.padding(bottom = 32.dp, top = 16.dp)
        )
        Box(Modifier.fillMaxWidth(),contentAlignment = Alignment.Center) {

            if(endDatePickerShow or startDatePickerShow){
                DatePickerDialog(
                    onDismissRequest = {
                        endDatePickerShow = false
                        startDatePickerShow = false },
                    confirmButton = {
                        Button(
                            onClick = {
                                datePickerState.selectedDateMillis?.let {
                                    when{
                                        startDatePickerShow -> startDateMills = it
                                        endDatePickerShow -> endDateMills = it

                                    }
                                    if (startDateMills > endDateMills){
                                        endDateMills = startDateMills
                                    }
                                }
                                endDatePickerShow = false
                                startDatePickerShow = false
                                Log.d("SelectedDate", "${datePickerState.selectedDateMillis}")
                            }
                        ) {
                            Text("Confirm")
                        }
                    }) {
                    DatePicker(state = datePickerState)
                }
            }

            
            Column() {
                TextField(value =taskTitle,
                    onValueChange = {taskTitle = it},
                    modifier = Modifier.width(300.dp),
                    singleLine = true,
                    label = { Text(text = stringResource(id = R.string.task_title))}
                )
                Spacer(modifier = Modifier.height(20.dp))
                TextField(value =taskDesc,
                    onValueChange = {taskDesc = it},
                    modifier = Modifier.width(300.dp),
                    singleLine = true,
                    label = { Text(text = stringResource(id = R.string.task_desc))}
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = stringResource(id = R.string.set_limit),
                    fontSize = 16.sp, fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.height(8.dp))
                Row() {
                    Column() {
                        Text(text = "С ${convertMillisToDate(startDateMills)}",
                            fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        FloatingActionButton(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            shape = ShapeDefaults.Large,
                            onClick = {
                                startDatePickerShow = true
                            }
                        ) {
                            Icon(imageVector =
                            Icons.Default.Today,
                                contentDescription = "dateButton",
                                modifier = Modifier.size(30.dp))

                        }

                    }
                    Spacer(modifier = Modifier.width(90.dp))
                    Column() {
                        Text(text = "До ${convertMillisToDate(endDateMills)}",fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        FloatingActionButton(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            shape = ShapeDefaults.Large,
                            onClick = {
                                endDatePickerShow = true
                            }
                        ) {
                            Icon(imageVector =
                            Icons.Default.Today,
                                contentDescription = "dateButton",
                                modifier = Modifier.size(30.dp))

                        }

                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        FileCard(fileFunc = stringResource(id = R.string.upload_file))
        Spacer(modifier = Modifier.height(20.dp))
        if (employeeName ==null){
            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(73.dp)
                    .clickable {
                        navController.navigate("set_employee")
                    },
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Box(){
                    Box(modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.TopEnd){
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.NavigateNext,
                                contentDescription = "attachFileButton", Modifier
                            )
                        }
                    }
                    Box(modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.TopStart){
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(id = R.string.set_emp),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp
                            )
                        }
                    }

                }
            }
        }else{
            EmployeeCard(name = employeeName)
        }
        

        Box(Modifier.fillMaxWidth(),contentAlignment = Alignment.Center) {
            Spacer(modifier = Modifier.height(350.dp))
            Button(onClick = {
                navController.popBackStack()
                navController.navigate("tasks")
            },

                ) {
                Text(text = stringResource(id = R.string.create_task),)
            }
        }


    }
}