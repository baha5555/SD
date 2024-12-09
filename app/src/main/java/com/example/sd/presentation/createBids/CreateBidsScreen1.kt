package com.example.sd.presentation.createBids

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sd.R
import com.example.sd.presentation.components.StepProgressBar
import com.example.sd.presentation.contact.ContactViewModel
import com.example.sd.utils.Values.UUID
import kotlinx.coroutines.delay

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun CreateBidsScreen1(
    navController: NavController,
    viewModel: CreateBidsViewModel,
) {
    val context = LocalContext.current

    val text = "App\\Models\\Bids\\Bid"
    UUID.value = viewModel.stateGetUUID.value.response.toString()
    val entityNumber = viewModel.stateEntityNumber.value.response?.number.toString()

    Log.i("UUID", "Values UUID ->${UUID.value}")
    Log.i("UUID", "Values textEntityNumber ->${text}")
    Log.i("UUID", "Values EntityNumber ->${entityNumber}")
    Toast.makeText(context, "Values UUID -> ${UUID.value}", Toast.LENGTH_SHORT).show()
    Scaffold(
        topBar = {
            Column {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalAlignment = CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                            viewModel.goToPreviousStep()
                            viewModel.resetFields()
                        },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_left),
                            contentDescription = "Back", modifier = Modifier
                        )
                    }

                    Text(
                        text = "Новое обращение",
                        style = TextStyle(
                            fontSize = 18.sp,
                            lineHeight = 18.sp,
                            fontFamily = FontFamily(Font(R.font.inter)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF1A202C),
                            textAlign = TextAlign.Center,
                        )
                    )
                    Box(
                        modifier = Modifier
                            .size(25.dp)

                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp),
                    verticalAlignment = CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    StepProgressBar(viewModel = viewModel)
                }
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp), verticalArrangement = Arrangement.SpaceBetween
        ) {

            Column {
                Text(
                    text = "Тема",
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 21.sp,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFF5D6A83),
                        letterSpacing = 0.2.sp,
                    ), modifier = Modifier.padding(bottom = 8.dp, top = 35.dp)
                )

                OutlinedTextField(
                    value = viewModel.nameCreate.collectAsState().value,
                    onValueChange = {
                        viewModel.updateField("name", "", it)
                    },
                    placeholder = {
                        Text(
                            text = "Тема обращения",
                            fontSize = 14.sp,
                            color = Color(0xFFAFBCCB)
                        )
                    },
                    singleLine = true,
                    textStyle = TextStyle.Default.copy(fontSize = 14.sp),
                    modifier = Modifier

                        .background(
                            MaterialTheme.colors.background,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),

                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFFE2E8F0),
                        unfocusedBorderColor = Color(0xFFE2E8F0),
                        errorBorderColor = Color(0xFFE2E8F0),
                        cursorColor = Color(0xFFE2E8F0),
                        textColor = Color.Black
                    )
                )

                Text(
                    text = "Признаки (Симптомы)",
                    style = TextStyle.Default.copy(fontSize = 14.sp),
                    modifier = Modifier.padding(bottom = 8.dp, top = 30.dp)
                )

                OutlinedTextField(
                    value = viewModel.descriptionCreate.collectAsState().value,
                    onValueChange = {
                        viewModel.updateField(
                            "description", name = it, id = ""
                        )
                    },
                    placeholder = {
                        androidx.compose.material3.Text(
                            text = "Дайте описание признакам обращения",
                            fontSize = 14.sp,
                            color = Color(0xFFAFBCCB),
                        )
                    },

                    textStyle = TextStyle.Default.copy(fontSize = 14.sp),
                    modifier = Modifier
                        .heightIn(min = 120.dp)
                        .background(
                            MaterialTheme.colors.background,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .fillMaxWidth(1f)
                        .height(200.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFFE2E8F0),
                        unfocusedBorderColor = Color(0xFFE2E8F0),
                        errorBorderColor = Color(0xFFE2E8F0),
                        cursorColor = Color(0xFFE2E8F0),
                        textColor = Color.Black
                    )
                )
            }
            Button(
                onClick = {
                    viewModel.goToNextStep()
                    viewModel.createBid {

                    }
                    navController.navigate("step2")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 40.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF004FC7)),
                enabled = viewModel.nameCreate.value != "" && viewModel.descriptionCreate.value != ""
            ) {
                Text(
                    text = "Продолжить",
                    color = Color.White,
                    style = MaterialTheme.typography.button
                )
            }
        }
    }

}

