package com.example.employeetaskreg.ui.screens.profileScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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


@Composable
@Preview
fun ProfileScreen(){
    Column {
        Box(modifier = Modifier.fillMaxWidth()){
            Text(
                text = stringResource(id = R.string.profile),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 16.dp, top = 16.dp)
            )
            Icon(imageVector = Icons.Default.Settings, contentDescription = "OptButton",
                Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 32.dp, top = 16.dp))
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)){
            Text(
                text = "Иванов И.И",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Normal,
                fontSize = 25.sp,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 16.dp, top = 16.dp)
            )
            Box(
                Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 16.dp, top = 16.dp)){
                Surface(
                    shape = RoundedCornerShape(50),
                    color = Color.LightGray,
                    modifier = Modifier.size(74.dp),
                    content = {
                        Box(Modifier.fillMaxWidth(), Alignment.Center) {
                            Text(
                                text = "ИИ",
                                fontSize = 16.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        }

                    })
            }
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)){
            Text(
                text = stringResource(id = R.string.director),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Normal,
                fontSize = 25.sp,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 16.dp, top = 16.dp)
            )
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)){
            Text(
                text = "${stringResource(id = R.string.tasks_solved)} 10",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Normal,
                fontSize = 25.sp,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 16.dp, top = 16.dp)
            )
        }

    }

}
