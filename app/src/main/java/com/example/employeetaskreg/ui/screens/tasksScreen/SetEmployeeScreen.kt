package com.example.employeetaskreg.ui.screens.tasksScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.employeetaskreg.R
import com.example.employeetaskreg.ui.screens.employeeScreen.EmployeeCard
import com.example.employeetaskreg.ui.screens.employeeScreen.SearchSec

@Composable
fun SetEmployeeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.set_emp),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            modifier = Modifier.padding(bottom = 16.dp, top = 16.dp)
        )

        SearchSec()

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(3) {
                Spacer(modifier = Modifier.height(30.dp))
                EmployeeCard(name = "Иванов И.И.",true){
                    navController.popBackStack()
                    navController.popBackStack()
                    navController.navigate("new_task/${"Иванов И.И."}")
                }

            }
        }
    }
}