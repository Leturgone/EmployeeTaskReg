package com.example.employeetaskreg.presentation.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.employeetaskreg.R
import com.example.employeetaskreg.presentation.ui.screens.employeeScreen.AvatarNameSec
import com.example.employeetaskreg.presentation.ui.screens.tasksScreen.FileCard
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskCard(taskName: String, employeeName: String, initials: String,role:String = "1") {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .clickable { showBottomSheet = true },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Box(){
            Box(modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopStart){
                Text(
                    text = taskName,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 16.dp, start = 16.dp),
                    fontSize = 16.sp
                )

            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .padding(bottom = 8.dp),
                contentAlignment = Alignment.BottomEnd){
                if(role == "1"){
                    AvatarNameSec(avatar = "ИИ", name = "Иванов И.И", modifier = Modifier.padding(start = 190.dp,end =15.dp))
                }
            }
        }
    }
    if (showBottomSheet) {
        ModalBottomSheet(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .height(700.dp)
                    .padding(16.dp), Arrangement.SpaceEvenly ,
                Alignment.Start) {
                Text(
                    text = "Задача 333",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .width(200.dp)
                )
                Text(
                    text = "Сделать что нибудь",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .width(200.dp)
                )
                Text(
                    text = "Описание",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .width(300.dp)
                )
                Text(
                    text = "Сделать что нибудь",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .width(200.dp)
                )
                Text(
                    text = "Срок: с 19.02.25 по 21.02.25",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .width(400.dp)
                )
                when(role){
                    "1"->{
                        FileCard(fileFunc = stringResource(id = R.string.download_file))

                        Text(
                            text = stringResource(id = R.string.worker),
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .width(200.dp)
                        )
                        AvatarNameSec(avatar = "ИИ", name = "Иванов И.И", modifier =Modifier)
                    }
                    "2"->{
                        FileCard(fileFunc = stringResource(id = R.string.upload_order))
                        Spacer(modifier = Modifier.height(20.dp))
                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                            ExtendedFloatingActionButton(
                                containerColor = MaterialTheme.colorScheme.primary,
                                text = { Text(text = stringResource(id = R.string.create_resp)) },
                                icon = { Icon(imageVector = Icons.Default.MailOutline,
                                    contentDescription = "createRespButton") },
                                onClick = {
                                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                                        if (!sheetState.isVisible) {
                                            showBottomSheet = false
                                        }
                                    }
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(220.dp))


            }

        }
    }
}
