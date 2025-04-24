package com.example.employeetaskreg.presentation.ui.screens.logRegScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.employeetaskreg.R
import com.example.employeetaskreg.domain.repository.EmpTaskRegState
import com.example.employeetaskreg.presentation.ui.screens.CustomToastMessage
import com.example.employeetaskreg.presentation.viewmodel.MainViewModel

@Composable
fun LogScreen(navController: NavHostController, viewModel: MainViewModel = hiltViewModel()){
    var loginInputText  by remember { mutableStateOf("") }
    var passwordInputText  by remember { mutableStateOf("") }

    var showToast by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val loginState = viewModel.loginFlow.collectAsState()

    var passwordVisible by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxWidth()){
        CustomToastMessage(
            message = errorMessage,
            isVisible = showToast,
            onDismiss = { showToast = false },
        )
        Box(Modifier.fillMaxWidth(),contentAlignment = Alignment.Center) {
            Column( modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Spacer(modifier = Modifier.height(60.dp))
                    Text(
                        text = stringResource(id = R.string.sign_in),
                        fontSize = 26.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Spacer(modifier = Modifier.height(101.dp))
                    OutlinedTextField(
                        value = loginInputText,
                        modifier = Modifier.size(width = 255.dp,60.dp),
                        label = { Text(stringResource(id = R.string.login_input)) },
                        colors = TextFieldDefaults
                            .colors(
                                focusedTextColor = MaterialTheme.colorScheme.primary,
                                unfocusedTextColor = MaterialTheme.colorScheme.primary,
                                focusedLabelColor = MaterialTheme.colorScheme.primary,
                                unfocusedLabelColor = MaterialTheme.colorScheme.primary,
                                focusedContainerColor = MaterialTheme.colorScheme.background,
                                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                                unfocusedIndicatorColor = MaterialTheme.colorScheme.primary),
                        singleLine = true,
                        keyboardOptions =  KeyboardOptions(keyboardType = KeyboardType.Email),
                        onValueChange = {
                            loginInputText = it
                        })
                    Spacer(modifier = Modifier.height(41.dp))

                    OutlinedTextField(
                        value = passwordInputText,
                        modifier = Modifier.size(width = 255.dp,60.dp),
                        colors = TextFieldDefaults
                            .colors(
                                focusedTextColor = MaterialTheme.colorScheme.primary,
                                unfocusedTextColor = MaterialTheme.colorScheme.primary,
                                focusedLabelColor = MaterialTheme.colorScheme.primary,
                                unfocusedLabelColor = MaterialTheme.colorScheme.primary,
                                focusedContainerColor = MaterialTheme.colorScheme.background,
                                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                                unfocusedIndicatorColor = MaterialTheme.colorScheme.primary),
                        singleLine = true,
                        label = { Text(stringResource(id = R.string.password)) },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions =  KeyboardOptions(keyboardType = KeyboardType.Password),
                        onValueChange = {
                            passwordInputText = it
                        },
                        trailingIcon = {
                            val image = if (passwordVisible)
                                Icons.Filled.Visibility
                            else Icons.Filled.VisibilityOff

                            val description = if (passwordVisible) "Hide password" else "Show password"

                            IconButton(onClick = {passwordVisible = !passwordVisible}){
                                Icon(imageVector  = image, description,
                                    tint = MaterialTheme.colorScheme.primary)
                            }
                        })
                }
                Spacer(modifier = Modifier.height(142.dp))

                Button(
                    onClick = {
                        viewModel.login(loginInputText, passwordInputText) },

                    ) {
                    Text(text = stringResource(id = R.string.sign_in),)
                }

                when (loginState.value) {
                    is EmpTaskRegState.Failure -> LaunchedEffect(loginState.value) {
                        showToast = true
                        errorMessage = (loginState.value as EmpTaskRegState.Failure).exception.toString()
                    }

                    EmpTaskRegState.Loading -> CircularProgressIndicator()
                    is EmpTaskRegState.Success -> LaunchedEffect(Unit){
                        navController.popBackStack()
                        navController.popBackStack()
                        navController.navigate("tasks")
                    }

                    EmpTaskRegState.Waiting -> null
                }


                Spacer(modifier = Modifier.height(1.dp))
            }
        }
    }
}