package com.example.sd.presentation.authorization

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sd.R

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ChangePasswordScreen(navController: NavController,viewModel: AuthViewModel) {
    val password = remember { mutableStateOf("") }
    val password_confirmation = remember { mutableStateOf("") }
    val errorMessage = remember { mutableStateOf("") }


    val userId = viewModel.stateAboutMe.value.response?.id
    Toast.makeText(navController.context, userId.toString(), Toast.LENGTH_LONG).show()
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.1f)
                    .padding(horizontal = 0.dp),
                horizontalArrangement = Arrangement.spacedBy(75.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
            ) {


                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_left),
                        contentDescription = "Back"
                    )
                }

                Text(
                    text = "Изменить пароль",

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
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(19.dp)
            ) {
                Text(
                    text = "Новый пароль",
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 21.sp,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFF5D6A83),
                        letterSpacing = 0.2.sp,
                    )
                )
                OutlinedTextField(
                    value = password.value,
                    onValueChange = {
                        password.value = it
                    },
                    placeholder = {
                        Text(
                            text = "Введите новый пароль",
                            style = TextStyle(
                                fontSize = 14.sp,
                                lineHeight = 21.sp,
                                fontFamily = FontFamily(Font(R.font.inter)),
                                fontWeight = FontWeight(500),
                                color = Color(0xFFA0AEC0),
                                letterSpacing = 0.2.sp,
                            )
                        )
                    },

                    textStyle = TextStyle.Default.copy(fontSize = 18.sp),
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 20.dp)
                        .background(
                            MaterialTheme.colors.background,
                            shape = RoundedCornerShape(5.dp)
                        )
                        .fillMaxWidth(0.95f),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF004FC7),
                        unfocusedBorderColor = Color(0xFFAFBCCB),
                        errorBorderColor = Color(0xFFAFBCCB),
                        cursorColor = Color(0xFF004FC7),
                        textColor = Color.Black
                    )
                )





                Text(
                    text = "Новый пароль снова",
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 21.sp,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFF5D6A83),
                        letterSpacing = 0.2.sp,
                    )
                )
                OutlinedTextField(
                    value = password_confirmation.value,
                    onValueChange = {
                        password_confirmation.value = it
                    },
                    placeholder = {
                        Text(
                            text = "Введите новый пароль снова",
                            style = TextStyle(
                                fontSize = 14.sp,
                                lineHeight = 21.sp,
                                fontFamily = FontFamily(Font(R.font.inter)),
                                fontWeight = FontWeight(500),
                                color = Color(0xFFA0AEC0),
                                letterSpacing = 0.2.sp,
                            )
                        )
                    },

                    textStyle = TextStyle.Default.copy(fontSize = 18.sp),
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .background(
                            MaterialTheme.colors.background,
                            shape = RoundedCornerShape(5.dp)
                        )
                        .fillMaxWidth(0.95f),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF004FC7),
                        unfocusedBorderColor = Color(0xFFAFBCCB),
                        errorBorderColor = Color(0xFFAFBCCB),
                        cursorColor = Color(0xFF004FC7),
                        textColor = Color.Black
                    )
                )



                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            viewModel.changePassword(userId = userId.toString(), password = password.value, password_confirmation = password_confirmation.value){ success, error ->
                                if (success) {
                                    navController.navigate("SuccessChangePassword") {
                                        popUpTo("ChangePasswordScreen") { inclusive = true }
                                    }
                                } else {
                                    password.value = error ?: "Неверный логин или пароль"
                                }
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(bottom = 10.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF004FC7)),
                    ) {
                        Text(
                            text = "Подтвердить изменение",
                            style = TextStyle(
                                fontSize = 16.sp,
                                lineHeight = 24.sp,
                                fontFamily = FontFamily(Font(R.font.inter)),
                                fontWeight = FontWeight(600),
                                color = Color(0xFFFFFFFF),
                                textAlign = TextAlign.Center,
                                letterSpacing = 0.2.sp,
                            )
                        )
                    }
                    if (errorMessage.value.isNotEmpty()) {
                        Text(
                            text = errorMessage.value,
                            color = Color.Red,
                            fontSize = 14.sp
                        )
                    }
                }

            }
        }
    )
}