package com.example.employeetaskreg.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.employeetaskreg.ui.screens.employeeScreen.EmployeesScreen
import com.example.employeetaskreg.ui.screens.logRegScreen.LogScreen
import com.example.employeetaskreg.ui.screens.logRegScreen.RegScreen
import com.example.employeetaskreg.ui.screens.optScreen.OptScreen
import com.example.employeetaskreg.ui.screens.profileScreen.ProfileScreen
import com.example.employeetaskreg.ui.screens.respScreen.RespScreen
import com.example.employeetaskreg.ui.screens.tasksScreen.NewTaskScreen
import com.example.employeetaskreg.ui.screens.tasksScreen.SetEmployeeScreen
import com.example.employeetaskreg.ui.screens.tasksScreen.TaskScreen


@Composable
fun AppNavigation(innerPadding: PaddingValues, navController: NavHostController){

    NavHost(
        navController = navController,
        startDestination = "reg",
        modifier = Modifier.padding(innerPadding)
    ) {
        composable("reg"){ RegScreen(navController)}
        composable("log"){ LogScreen(navController)}
        composable("tasks"){ TaskScreen(navController)}
        composable("new_task") { NewTaskScreen(navController) }
        composable("new_task/{employeeName}") {
            val empName = it.arguments?.getString("employeeName")
            NewTaskScreen(navController,employeeName = empName) }
        composable("set_employee") { SetEmployeeScreen(navController)  }
        composable("options") { OptScreen(navController)}
        composable("emp_list"){ EmployeesScreen(navController)}
        composable("resp"){ RespScreen(navController)}
        composable("profile"){ ProfileScreen(navController)}
    }
}