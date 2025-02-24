package com.example.employeetaskreg.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TaskCard(taskName: String, employeeName: String, initials: String) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .clickable {

            },
        colors = CardDefaults.cardColors(
            containerColor = Color.White  // Set container color
        )
    ) {
        Column {
            Text(
                text = taskName,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 16.dp, start = 16.dp),
                fontSize = 16.sp
            )
            Row(
                modifier = Modifier.padding(start = 210.dp,end =20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(){
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = Color.LightGray, // Adjust color as needed
                        modifier = Modifier.size(40.dp),
                        content = {
                            Box(Modifier.fillMaxWidth(), Alignment.Center) {
                                Text(
                                    text = "ИИ",
                                    fontSize = 16.sp,
                                    color = Color.Black,
                                    textAlign = TextAlign.Center
                                )
                            }

                        })
                }

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = employeeName,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }
        }

    }
}
@Preview(showBackground = true)
@Composable
fun TaskCardPreview() {
    TaskCard("Задача 333", "Иванов И.И.", "ИИ")
}