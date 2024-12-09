package com.example.sd.presentation.createBids

import android.annotation.SuppressLint
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.sd.R
import com.example.sd.presentation.accounts.AccountsViewModel
import com.example.sd.presentation.components.StepProgressBar
import com.example.sd.presentation.contact.ContactViewModel
import com.example.sd.presentation.filter.DateTimePickerField
import com.example.sd.utils.Values
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreateBidCreateBidsScreenСompletion(
    navController: NavController,
    viewModel: CreateBidsViewModel
) {
    BackHandler {
        navController.navigate("AnalysisScreen")
    }
    val number = viewModel.stateEntityNumber.value.response?.number.toString()
    val annotatedMessage = buildAnnotatedString {
        // Добавляем обычный текст
        append("Вы успешно создали обращение ")

        // Добавляем стилизованный текст SR-443321
        pushStyle(SpanStyle(color = Color(0xFF004FC7), fontWeight = FontWeight.Bold))
        append(number)
        pop()

        // Окончание текста
        append(", оно появится в общем списке обращений")
    }

    Scaffold(

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
                    bitmap = ImageBitmap.imageResource(R.drawable.icon_completion),
                    contentDescription = "lock"
                )
                Text(
                    text = "Обращение успешно создано",
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
                ClickableText(
                    text = annotatedMessage,
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF090A0A),
                        textAlign = TextAlign.Center
                    ),
                    onClick = { offset ->
                            navController.navigate("appealDetailScreen/SR-443321")

                    },
                    modifier = Modifier.width(260.dp)
                )
                Spacer(modifier = Modifier.height(22.dp))
                Button(
                    onClick = {
                        viewModel.resetFields()
                        navController.navigate("AnalysisScreen") {
                            popUpTo("step5") { inclusive = true }
                        }
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
                Text(text = viewModel.nameCreate.value)
                Text(text = viewModel.descriptionCreate.value)
                Text(text = viewModel.statusCreate.value)
                Text(text = viewModel.categoryCreate.value)
                Text(text = viewModel.priorityCreate.value)
                Text(text = viewModel.levelCreate.value)
                Text(text = viewModel.contactCreate.value)
                Text(text = viewModel.responsibleCreate.value)
                Text(text = viewModel.accountCreate.value)
                Text(text = viewModel.servicePactCreate.value)
                Text(text = viewModel.serviceItemCreate.value)
                Text(text = viewModel.originCreate.value)
                Text(text = viewModel.regDataCreate.value)
                Text(text = viewModel.respDataCreate.value)
                Text(text = viewModel.resolutionDataCreate.value)

            }

        }
    )
}

