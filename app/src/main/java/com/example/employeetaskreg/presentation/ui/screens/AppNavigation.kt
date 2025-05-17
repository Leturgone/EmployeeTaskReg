package com.example.employeetaskreg.presentation.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
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
import com.example.employeetaskreg.presentation.viewmodel.ProfileScreenViewModel


@Composable
fun AppNavigation(innerPadding: PaddingValues, navController: NavHostController,profileScreenViewModel: ProfileScreenViewModel){
    NavHost(
        navController = navController,
        startDestination = "reg",
        modifier = Modifier.padding(innerPadding)
    ) {
        composable("reg"){
            RegScreen(navController) }
        composable("log"){ LogScreen(navController) }
        composable("tasks"){
            TaskScreen(navController, profileViewModel = profileScreenViewModel)
        }
        composable("new_task") { NewTaskScreen(navController) }
        composable("new_task/{employeeName}/{employeeId}") {
            val empName = it.arguments?.getString("employeeName")
            val empId = it.arguments?.getString("employeeId")?.toInt()
            NewTaskScreen(navController,employeeName = empName,employeeId =empId) }
        composable("set_employee") { SetEmployeeScreen(navController)  }
        composable("opt") { OptScreen(navController, profileViewModel = profileScreenViewModel) }
        composable("emp_list"){ EmployeesScreen() }
        composable("resp"){ RespScreen(profileScreenViewModel) }
        composable("profile"){ ProfileScreen(navController,profileScreenViewModel) }
    }
}