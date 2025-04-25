package com.example.employeetaskreg.presentation.ui.screens.profileScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.navigation.NavHostController
import com.example.employeetaskreg.R
import com.example.employeetaskreg.domain.model.CompanyWorker
import com.example.employeetaskreg.domain.model.CompanyWorkerInterface
import com.example.employeetaskreg.domain.repository.EmpTaskRegState
import com.example.employeetaskreg.presentation.ui.screens.CustomToastMessage
import com.example.employeetaskreg.presentation.viewmodel.MainViewModel


@Composable
fun ProfileScreen(navController: NavHostController,role:String, viewModel: MainViewModel = hiltViewModel()) {

    val profileState = viewModel.profileFlow.collectAsState()
    val dirNameState = viewModel.dirNameFlow.collectAsState()
    val taskCountState = viewModel.taskCountFlow.collectAsState()

    var dirName by remember { mutableStateOf("") }
    var taskCount by remember { mutableIntStateOf(0) }
    var showToast by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(Unit){
        viewModel.getProfile()
        viewModel.getTaskCount()
    }
    CustomToastMessage(
        message = errorMessage,
        isVisible = showToast,
        onDismiss = { showToast = false }
    )
    when(profileState.value){
        is EmpTaskRegState.Failure -> {
            showToast = true
            errorMessage = (profileState.value as EmpTaskRegState.Failure).exception.message.toString()
        }
        EmpTaskRegState.Loading -> CircularProgressIndicator()
        is EmpTaskRegState.Success -> {
            val profile = (profileState.value as EmpTaskRegState.Success<CompanyWorkerInterface>).result
            Column {
                Box(modifier = Modifier.fillMaxWidth()){
                    Text(
                        text = stringResource(id = R.string.profile),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(start = 16.dp, top = 16.dp)
                    )
                    Icon(imageVector = Icons.Default.Settings, contentDescription = "OptButton",
                        Modifier
                            .align(Alignment.BottomEnd)
                            .padding(end = 32.dp, top = 16.dp)
                            .clickable {
                                navController.navigate("opt")
                            })
                }
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)){
                    Text(
                        text = profile.name,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Normal,
                        fontSize = 25.sp,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(start = 16.dp, top = 16.dp)
                    )
                    Box(
                        Modifier
                            .align(Alignment.BottomEnd)
                            .padding(end = 16.dp, top = 16.dp)){
                        Surface(
                            shape = RoundedCornerShape(50),
                            color = Color.LightGray,
                            modifier = Modifier.size(74.dp),
                            content = {
                                Box(Modifier.fillMaxWidth(), Alignment.Center) {
                                    Text(
                                        text = profile.name.substringAfter(" ").replace(".",""),
                                        fontSize = 16.sp,
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center
                                    )
                                }

                            })
                    }
                }
                when(profile){
                    is CompanyWorker.Director->{
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)){
                            Text(
                                text = stringResource(id = R.string.director),
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Normal,
                                fontSize = 25.sp,
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(start = 16.dp, top = 16.dp)
                            )
                        }
                    }
                    is CompanyWorker.Employee->{
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)){
                            Text(
                                text = stringResource(id = R.string.employee),
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Normal,
                                fontSize = 25.sp,
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(start = 16.dp, top = 16.dp)
                            )
                        }
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)){

                            LaunchedEffect(Unit){
                                profile.directorId?.let { viewModel.getDirById(it) }
                            }

                            dirName = when(dirNameState.value){
                                is EmpTaskRegState.Failure -> ""
                                EmpTaskRegState.Loading -> "..."
                                is EmpTaskRegState.Success -> (dirNameState.value as EmpTaskRegState.Success<CompanyWorker.Director>).result.name
                                EmpTaskRegState.Waiting -> ""
                            }

                            Text(
                                text = "${stringResource(id = R.string.director)}: $dirName ",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Normal,
                                fontSize = 25.sp,
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(start = 16.dp, top = 16.dp)
                            )
                        }
                    }
                }

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)){
                    taskCount = when(taskCountState.value){
                        is EmpTaskRegState.Failure -> 0
                        EmpTaskRegState.Loading -> 0
                        is EmpTaskRegState.Success -> (taskCountState.value as EmpTaskRegState.Success<Int>).result
                        EmpTaskRegState.Waiting -> 0
                    }
                    Text(
                        text = "${stringResource(id = R.string.tasks_solved)}: $taskCount",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Normal,
                        fontSize = 25.sp,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(start = 16.dp, top = 16.dp)
                    )
                }

            }
        }
        EmpTaskRegState.Waiting -> null
    }


}
