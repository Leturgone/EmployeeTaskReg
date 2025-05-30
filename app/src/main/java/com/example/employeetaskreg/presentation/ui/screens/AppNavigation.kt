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
import com.example.employeetaskreg.presentation.ui.screens.respScreen.ReportScreen
import com.example.employeetaskreg.presentation.ui.screens.tasksScreen.NewTaskScreen
import com.example.employeetaskreg.presentation.ui.screens.tasksScreen.SetEmployeeScreen
import com.example.employeetaskreg.presentation.ui.screens.tasksScreen.TaskScreen
import com.example.employeetaskreg.presentation.viewmodel.AppThemeViewModel
import com.example.employeetaskreg.presentation.viewmodel.ProfileViewModel


@Composable
fun AppNavigation(innerPadding: PaddingValues, navController: NavHostController,
                  themeViewModel: AppThemeViewModel,
                  profileViewModel: ProfileViewModel){
    NavHost(
        navController = navController,
        startDestination = "reg",
        modifier = Modifier.padding(innerPadding)
    ) {
        composable("reg"){
            RegScreen(navController) }
        composable("log"){ LogScreen(navController) }
        composable("tasks"){
            TaskScreen(navController, profileViewModel = profileViewModel)
        }
        composable("new_task") { NewTaskScreen(navController) }
        composable("new_task/{employeeName}/{employeeId}/{directorId}") {
            val empName = it.arguments?.getString("employeeName")
            val empId = it.arguments?.getString("employeeId")?.toInt()
            val dirId = it.arguments?.getString("directorId")?.toInt()
            NewTaskScreen(navController,employeeName = empName,employeeId =empId, directorId = dirId) }
        composable("set_employee") { SetEmployeeScreen(navController)  }
        composable("opt") { OptScreen(navController, themeViewModel = themeViewModel, profileViewModel = profileViewModel) }
        composable("emp_list"){ EmployeesScreen() }
        composable("resp"){ ReportScreen(profileViewModel) }
        composable("profile"){ ProfileScreen(navController,profileViewModel) }
    }
}