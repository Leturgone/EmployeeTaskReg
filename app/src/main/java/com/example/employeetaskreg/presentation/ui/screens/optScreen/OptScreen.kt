package com.example.employeetaskreg.presentation.ui.screens.optScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.employeetaskreg.R
import com.example.employeetaskreg.presentation.viewmodel.AppThemeViewModel
import com.example.employeetaskreg.presentation.viewmodel.AuthViewModel
import com.example.employeetaskreg.presentation.viewmodel.ProfileViewModel

@Composable
fun OptScreen(navController: NavHostController,
              profileViewModel:ProfileViewModel,
              themeViewModel: AppThemeViewModel,
              authViewModel: AuthViewModel = hiltViewModel()) {

    val isDarkTheme = themeViewModel.isDarkMode.collectAsState()

    Column {
        Box(modifier = Modifier.fillMaxWidth()){
            Text(
                text = stringResource(id = R.string.options),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 16.dp, top = 16.dp)
            )
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)){
            Text(
                text = stringResource(id = R.string.logout),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Normal,
                fontSize = 25.sp,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 16.dp, top = 16.dp)
                    .clickable {
                        profileViewModel.clearProfile()
                        authViewModel.logout()
                        navController.popBackStack()
                        navController.popBackStack()
                        navController.popBackStack()
                        navController.navigate("reg")
                    }
            )
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)){
            Text(
                text = stringResource(id = R.string.change_theme),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Normal,
                fontSize = 25.sp,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 16.dp, top = 16.dp)
            )
        }
        Box(modifier = Modifier
            .fillMaxWidth()){
            Row(
                Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 16.dp, top = 16.dp),
                verticalAlignment = Alignment.CenterVertically) {
                Switch(checked =isDarkTheme.value ,
                    onCheckedChange = {
                        themeViewModel.switchTheme()
                    }
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = when(isDarkTheme.value){
                        true -> "${stringResource(id = R.string.dark)} "
                        false -> "${stringResource(id = R.string.light)} "
                    },
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Normal,
                    fontSize = 25.sp,
                )
            }

        }

    }
}