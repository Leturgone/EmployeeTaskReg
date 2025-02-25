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
        composable("reg"){ RespScreen()}
        composable("log"){ LogScreen()}
        composable("tasks"){ TaskScreen()}
        composable("new_task") { NewTaskScreen() }
        composable("set_employee") { SetEmployeeScreen()  }
        composable("options") { OptScreen()}
        composable("employees_list"){ EmployeesScreen()}
        composable("resp"){ RespScreen()}
        composable("profile"){ ProfileScreen()}
    }
}