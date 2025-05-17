package com.example.employeetaskreg.presentation.ui.screens.tasksScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.employeetaskreg.R
import com.example.employeetaskreg.domain.model.CompanyWorker
import com.example.employeetaskreg.domain.model.CompanyWorkerInterface
import com.example.employeetaskreg.domain.model.Task
import com.example.employeetaskreg.domain.repository.EmpTaskRegState
import com.example.employeetaskreg.presentation.ui.screens.CustomToastMessage
import com.example.employeetaskreg.presentation.ui.screens.TaskCard
import com.example.employeetaskreg.presentation.viewmodel.ProfileScreenViewModel
import com.example.employeetaskreg.presentation.viewmodel.TasksScreenViewModel

@Composable
fun TaskScreen(navController: NavHostController,
               profileViewModel:ProfileScreenViewModel,
               tasksViewModel: TasksScreenViewModel = hiltViewModel()) {

    val profileState = profileViewModel.profileFlow.collectAsState()
    val taskListState = tasksViewModel.taskListFlow.collectAsState()

    var showToast by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var taskList by remember { mutableStateOf(emptyList<Task>()) }
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
                text = stringResource(id = R.string.tasks),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                modifier = Modifier.padding(bottom = 16.dp, top = 16.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(taskList.size) {
                    val task = taskList[it]
                    Spacer(modifier = Modifier.height(30.dp))
                    TaskCard(task,role)

                }
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
                        FloatingActionButton(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            shape = ShapeDefaults.Large,
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(bottom = 50.dp, end = 40.dp),
                            onClick = {navController.navigate("new_task")}
                        ) {
                            Icon(imageVector =
                            Icons.Default.Add,
                                contentDescription = "addButton",
                                modifier = Modifier.size(20.dp))

                        }
                    }
                    is CompanyWorker.Employee ->{
                        role = "employee"
                    }
                }
                LaunchedEffect(Unit){
                    tasksViewModel.getTaskList()
                }

                when(taskListState.value){
                    is EmpTaskRegState.Failure -> LaunchedEffect(taskListState.value) {
                        showToast = true
                        errorMessage = (taskListState.value as EmpTaskRegState.Failure).exception.toString()
                    }
                    EmpTaskRegState.Loading -> CircularProgressIndicator()
                    is EmpTaskRegState.Success -> taskList = (taskListState.value as EmpTaskRegState.Success<List<Task>>).result
                    EmpTaskRegState.Waiting -> null
                }


            }
            EmpTaskRegState.Waiting -> null
        }


    }


}