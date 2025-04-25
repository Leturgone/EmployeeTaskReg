package com.example.employeetaskreg.presentation.ui.screens.employeeScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.employeetaskreg.presentation.ui.screens.TaskCard


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeCard(name: String, setListItem:Boolean = false,clickFun: (() -> Unit)? = null) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier =  Modifier
            .fillMaxWidth()
            .height(73.dp).let {
                when(setListItem){
                    true-> it.clickable(onClick = clickFun!!)
                    false-> it.clickable { showBottomSheet = true }
                }
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.White  // Set container color
        )
    ) {
        AvatarNameSec(avatar = "ИИ", name = name,Modifier.padding(16.dp))
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
                    .padding(16.dp),Arrangement.SpaceEvenly ,
                Alignment.Start) {
                Text(
                    text = "Иванов И.И",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .width(200.dp)
                )
                Text(
                    text = "Задач решено: 10",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .width(300.dp)
                )
                Text(
                    text = "Текущая задача",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .width(300.dp)
                )
                //TaskCard(taskName = "Задача 333", employeeName = "Иванов И.И", initials = "ИИ")
                Spacer(modifier = Modifier.height(220.dp))

            }

        }
    }

}

