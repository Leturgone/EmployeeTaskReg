package com.example.employeetaskreg.presentation.ui.screens.logRegScreen

import androidx.compose.foundation.clickable
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
import com.example.employeetaskreg.presentation.viewmodel.AuthViewModel
import com.example.employeetaskreg.presentation.viewmodel.ProfileViewModel

@Composable
fun RegScreen(navController: NavHostController,
              profileViewModel:ProfileViewModel = hiltViewModel(),
              authViewModel: AuthViewModel = hiltViewModel()){
    var loginInputText  by remember { mutableStateOf("") }
    var fioInputText  by remember { mutableStateOf("") }
    var fioDirInputText  by remember { mutableStateOf("") }
    var passwordInputText  by remember { mutableStateOf("") }

    val regState = authViewModel.regFlow.collectAsState()
    val profileState = profileViewModel.profileFlow.collectAsState()

    var showToast by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }


    Box(modifier = Modifier.fillMaxWidth()){
        CustomToastMessage(
            message = errorMessage,
            isVisible = showToast,
            onDismiss = { showToast = false }
        )
        Box(Modifier.fillMaxWidth(),contentAlignment = Alignment.Center) {
                when(profileState.value){
                is EmpTaskRegState.Failure -> {
                    Column( modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Spacer(modifier = Modifier.height(60.dp))
                            Text(
                                text = stringResource(id = R.string.create_acc),
                                fontSize = 26.sp,
                                color = MaterialTheme.colorScheme.onBackground,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(16.dp),
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(30.dp))

                            OutlinedTextField(
                                value = fioInputText,
                                modifier = Modifier.size(width = 255.dp,60.dp),
                                label = { Text(stringResource(id = R.string.fio)) },
                                singleLine = true,
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
                                keyboardOptions =  KeyboardOptions(keyboardType = KeyboardType.Email),
                                onValueChange = {
                                    fioInputText = it
                                })
                            Spacer(modifier = Modifier.height(41.dp))
                            OutlinedTextField(
                                value = fioDirInputText,
                                modifier = Modifier.size(width = 255.dp,60.dp),
                                label = { Text(stringResource(id = R.string.director_fio)) },
                                singleLine = true,
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
                                keyboardOptions =  KeyboardOptions(keyboardType = KeyboardType.Email),
                                onValueChange = {
                                    fioDirInputText = it
                                })
                            Spacer(modifier = Modifier.height(41.dp))
                            OutlinedTextField(
                                value = loginInputText,
                                modifier = Modifier.size(width = 255.dp,60.dp),
                                label = { Text(stringResource(id = R.string.login_input)) },
                                singleLine = true,
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
                                keyboardOptions =  KeyboardOptions(keyboardType = KeyboardType.Email),
                                onValueChange = {
                                    loginInputText = it
                                })
                            Spacer(modifier = Modifier.height(41.dp))

                            OutlinedTextField(
                                value = passwordInputText,
                                modifier = Modifier.size(width = 255.dp,60.dp),
                                singleLine = true,
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
                                        Icon(imageVector  = image, description, tint = MaterialTheme.colorScheme.primary)
                                    }
                                })
                        }
                        Spacer(modifier = Modifier.height(40.dp))

                        Button(onClick = {
                            authViewModel.register(loginInputText,passwordInputText,fioInputText,fioDirInputText) },

                            ) {
                            Text(text = stringResource(id = R.string.create_acc),)
                        }

                        when(regState.value){
                            EmpTaskRegState.Loading -> CircularProgressIndicator()
                            is EmpTaskRegState.Success -> {
                                LaunchedEffect(Unit) {
                                    navController.popBackStack()
                                    navController.popBackStack()
                                    navController.navigate("profile")
                                }
                            }
                            is EmpTaskRegState.Failure -> {
                                LaunchedEffect(regState.value) {
                                    showToast = true
                                    errorMessage =
                                        (regState.value as EmpTaskRegState.Failure).exception.message.toString()
                                }
                            }
                            EmpTaskRegState.Waiting -> null
                        }


                        Spacer(modifier = Modifier.height(55.dp))
                        Text(text = stringResource(id = R.string.already_have),
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 16.sp,
                            modifier = Modifier.clickable {
                                navController.navigate("log")
                            })

                        Spacer(modifier = Modifier.height(1.dp))
                    }
                }
                EmpTaskRegState.Loading -> CircularProgressIndicator()
                is EmpTaskRegState.Success -> {
                    if (regState.value is EmpTaskRegState.Waiting) {
                        LaunchedEffect(Unit) {
                            navController.popBackStack()
                            navController.popBackStack()
                            navController.navigate("profile")
                        }
                    }
                }
                EmpTaskRegState.Waiting -> LaunchedEffect(Unit){
                    profileViewModel.getProfile()
                }
            }

        }
    }
}