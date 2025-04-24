package com.example.employeetaskreg.presentation.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.employeetaskreg.presentation.ui.screens.employeeScreen.EmployeesScreen
import com.example.employeetaskreg.presentation.ui.screens.logRegScreen.LogScreen
import com.example.employeetaskreg.presentation.ui.screens.logRegScreen.RegScreen
import com.example.employeetaskreg.presentation.ui.screens.optScreen.OptScreen
import com.example.employeetaskreg.presentation.ui.screens.profileScreen.ProfileScreen
import com.example.employeetaskreg.presentation.ui.screens.respScreen.RespScreen
import com.example.employeetaskreg.presentation.ui.screens.tasksScreen.NewTaskScreen
import com.example.employeetaskreg.presentation.ui.screens.tasksScreen.SetEmployeeScreen
import com.example.employeetaskreg.presentation.ui.screens.tasksScreen.TaskScreen
import com.example.employeetaskreg.presentation.viewmodel.MainViewModel


@Composable
fun AppNavigation(innerPadding: PaddingValues, navController: NavHostController, viewModel: MainViewModel){
    val role = viewModel.userRole.observeAsState().value!!
    NavHost(
        navController = navController,
        startDestination = "reg",
        modifier = Modifier.padding(innerPadding)
    ) {
        composable("reg"){ RegScreen(navController) }
        composable("log"){ LogScreen(navController) }
        composable("tasks"){ TaskScreen(navController, role) }
        composable("new_task") { NewTaskScreen(navController) }
        composable("new_task/{employeeName}") {
            val empName = it.arguments?.getString("employeeName")
            NewTaskScreen(navController,employeeName = empName) }
        composable("set_employee") { SetEmployeeScreen(navController)  }
        composable("opt") { OptScreen(navController) }
        composable("emp_list"){ EmployeesScreen() }
        composable("resp"){ RespScreen(role) }
        composable("profile"){ ProfileScreen(navController,role) }
    }
}