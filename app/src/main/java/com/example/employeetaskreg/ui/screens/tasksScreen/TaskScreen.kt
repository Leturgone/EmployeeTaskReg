package com.example.employeetaskreg.ui.screens.tasksScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ShapeDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.employeetaskreg.R
import com.example.employeetaskreg.ui.screens.TaskCard
import com.example.employeetaskreg.ui.screens.employeeScreen.EmployeeCard
import com.example.employeetaskreg.ui.screens.employeeScreen.SearchSec

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TaskScreen(){
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(true) }
    Box(Modifier.clickable {
        showBottomSheet = true
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.tasks),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                modifier = Modifier.padding(bottom = 16.dp, top = 16.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(2) {
                    Spacer(modifier = Modifier.height(30.dp))
                    TaskCard(taskName = "Задача 333", employeeName = "Иванов И.И", initials ="ИИ" )

                }
            }
        }
        FloatingActionButton(
            shape = ShapeDefaults.Large,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 100.dp, end = 40.dp),
            onClick = { /*TODO*/ }
        ) {
            Icon(imageVector =
            Icons.Default.Add,
                contentDescription = "addButton",
                modifier = Modifier.size(20.dp))

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
                    .padding(16.dp),Arrangement.SpaceEvenly ,
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
                FileCard(fileFunc = stringResource(id = R.string.download_file))
                Spacer(modifier = Modifier.height(220.dp))

            }

        }
    }

}