package com.example.sd.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sd.R

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RoleScreen(navController: NavController,title: String) {

    Scaffold(
    topBar = {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.1f)
                .padding(horizontal = 0.dp),
            horizontalArrangement = Arrangement.spacedBy(100.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
        ) {


            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_left),
                    contentDescription = "Back"
                )
            }

            Text(
                text = title,
                style = TextStyle(
                    fontSize = 18.sp,
                    lineHeight = 18.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF1A202C),
                    textAlign = TextAlign.Center,
                )
            )

        }
    },
    content = {
        Column(
            modifier = Modifier
                .fillMaxHeight(0.9f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier
                    .width(250.dp)
                    .height(200.dp),
                bitmap = ImageBitmap.imageResource(R.drawable.role_icon),
                contentDescription = "lock"
            )
            Text(
                text = "Нет доступа",
                style = TextStyle(
                    fontSize = 18.sp,
                    lineHeight = 24.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(700),
                    color = Color(0xFF090A0A),
                    textAlign = TextAlign.Center,
                )
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                modifier = Modifier.width(260.dp),
                text = "К сожалению у вас нет нужного доступа для просмотра данной страницы",
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF090A0A),
                    textAlign = TextAlign.Center,
                )
            )
            Spacer(modifier = Modifier.height(22.dp))
            Button(
                onClick = {
                    navController.navigate("AnalysisScreen")
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .height(60.dp)
                    .padding(bottom = 10.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF004FC7)),
            ) {
                Text(
                    text = "На главную",
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 16.sp,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFFFFFFFF),
                        textAlign = TextAlign.Center,
                    )
                )
            }

        }

    }
    )
}