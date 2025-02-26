package com.example.employeetaskreg.ui.screens.respScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.employeetaskreg.ui.screens.employeeScreen.AvatarNameSec
import com.example.employeetaskreg.ui.screens.tasksScreen.FileCard
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun RespCard(respName:String, employeeName:String, initials:String, role:String){
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
                    text = respName,
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
                if (role == "1") {
                    AvatarNameSec(
                        avatar = "ИИ",
                        name = "Иванов И.И",
                        modifier = Modifier.padding(start = 190.dp, end = 15.dp)
                    )
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
                    text = "Ответ по задаче 333",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .width(500.dp)
                )
                Text(
                    text = "20.02.25",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .width(300.dp)
                )
                if (role == "1"){
                    AvatarNameSec(avatar = "ИИ", name = "Иванов И.И", modifier = Modifier)
                }
                
                FileCard(fileFunc = stringResource(id = R.string.download_order))
                when(role){
                    "1"->{Row(Modifier.fillMaxWidth()) {
                        Button(onClick = {
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    showBottomSheet = false
                                }
                            }
                        },
                            colors = ButtonDefaults.buttonColors(Color.Green), modifier = Modifier.weight(1f)
                        ) {
                            Text(text = stringResource(id = R.string.get))

                        }
                        Spacer(modifier = Modifier.width(50.dp))
                        Button(onClick = {
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    showBottomSheet = false
                                }
                            }
                        },
                            colors = ButtonDefaults.buttonColors(Color.Red), modifier = Modifier.weight(1f)) {
                            Text(text = stringResource(id = R.string.back))

                        }
                    }}
                    "2"-> Text(
                        text = "Ожидание",
                        color = Color.Yellow,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp,
                        modifier = Modifier.padding(bottom = 150.dp)
                    )
                }
                
                Spacer(modifier = Modifier.height(220.dp))


            }

        }
    }

}