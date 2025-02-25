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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material.icons.filled.Today
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.employeetaskreg.R

@Composable
@Preview
fun NewTaskScreen(){
    var taskTitle  by remember { mutableStateOf("") }
    var taskDesc  by remember { mutableStateOf("") }

    var startDate  by remember { mutableStateOf("") }
    var endDate  by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.new_task),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            modifier = Modifier.padding(bottom = 32.dp, top = 16.dp)
        )
        Box(Modifier.fillMaxWidth(),contentAlignment = Alignment.Center) {
            Column() {
                TextField(value =taskTitle,
                    onValueChange = {taskTitle = it},
                    modifier = Modifier.width(300.dp),
                    label = { Text(text = stringResource(id = R.string.task_title))}
                )
                Spacer(modifier = Modifier.height(20.dp))
                TextField(value =taskDesc,
                    onValueChange = {taskDesc = it},
                    modifier = Modifier.width(300.dp),
                    label = { Text(text = stringResource(id = R.string.task_desc))}
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = stringResource(id = R.string.set_limit),
                    fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Row() {
                    Column() {
                        Text(text = "С $startDate",
                            fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        FloatingActionButton(
                            shape = ShapeDefaults.Large,
                            onClick = { /*TODO*/ }
                        ) {
                            Icon(imageVector =
                            Icons.Default.Today,
                                contentDescription = "dateButton",
                                modifier = Modifier.size(30.dp))

                        }

                    }
                    Spacer(modifier = Modifier.width(90.dp))
                    Column() {
                        Text(text = "До $endDate",fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        FloatingActionButton(
                            shape = ShapeDefaults.Large,
                            onClick = { /*TODO*/ }
                        ) {
                            Icon(imageVector =
                            Icons.Default.Today,
                                contentDescription = "dateButton",
                                modifier = Modifier.size(30.dp))

                        }

                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        FileCard(fileFunc = "Загрузить файл")
        Spacer(modifier = Modifier.height(20.dp))
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(73.dp)
                .clickable {

                },
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Box(){
                Box(modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.TopEnd){
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.NavigateNext,
                            contentDescription = "attachFileButton", Modifier
                        )
                    }
                }
                Box(modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.TopStart){
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.set_emp),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp
                        )
                    }
                }

            }
        }

        Box(Modifier.fillMaxWidth(),contentAlignment = Alignment.Center) {
            Spacer(modifier = Modifier.height(350.dp))
            Button(onClick = {
            },

                ) {
                Text(text = stringResource(id = R.string.new_task),)
            }
        }


    }
}