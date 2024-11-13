package com.example.sd.presentation.authorization

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.sd.R
import com.example.sd.presentation.dashboard.DashboardViewModel
import kotlinx.coroutines.delay


@Composable
fun AuthScreen(navController: NavController) {
    val viewModel: AuthViewModel = hiltViewModel()
    val dashboardViewModel: DashboardViewModel = hiltViewModel()
    val name = remember { mutableStateOf("sadmin") }
    val password = remember { mutableStateOf("12345678") }
    val nameError = remember { mutableStateOf(false) }
    val passwordError = remember { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }





    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.6f)
            .background(Color.White)
            .padding(top = 20.dp)
            .padding(horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.logo_eskh),
            contentDescription = "",
            tint = Color(0xFFAFBCCB),
            modifier = Modifier
                .padding(top = 10.dp)
                .size(45.dp)
        )
        Text(
            modifier = Modifier.padding(top = 24.dp),
            text = "Вход в аккаунт",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            fontFamily = FontFamily.SansSerif
        )
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = "Введите логин и пароль для входа",
            fontSize = 14.sp,
            color = Color(0xFFAFBCCB),
        )

        OutlinedTextField(
            value = name.value,
            onValueChange = {
                name.value = it
                nameError.value = false
            },
            placeholder = {
                Text(text = "Логин", fontSize = 18.sp, color = Color(0xFFAFBCCB))
            },
            isError = nameError.value,
            textStyle = TextStyle.Default.copy(fontSize = 18.sp),
            modifier = Modifier
                .padding(top = 32.dp)
                .background(
                    MaterialTheme.colors.background,
                    shape = RoundedCornerShape(5.dp)
                )
                .fillMaxWidth(0.95f),
            shape = RoundedCornerShape(5.dp),
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = if (nameError.value) Color.Red else Color(0xFF004FC7),
                unfocusedBorderColor = if (nameError.value) Color.Red else Color(0xFFAFBCCB),
                errorBorderColor = if (nameError.value) Color.Red else Color(0xFFAFBCCB),
                cursorColor = Color(0xFF004FC7),
                textColor = Color.Black
            )
        )


        OutlinedTextField(
            value = password.value,
            onValueChange = {
                password.value = it
                passwordError.value = false
            },
            placeholder = {
                Text(text = "Пароль", fontSize = 18.sp, color = Color(0xFFAFBCCB))
            },
            isError = passwordError.value,
            textStyle = TextStyle.Default.copy(fontSize = 18.sp),
            modifier = Modifier
                .padding(top = 16.dp)
                .background(
                    MaterialTheme.colors.background,
                    shape = RoundedCornerShape(5.dp)
                )
                .fillMaxWidth(0.95f),
            shape = RoundedCornerShape(5.dp),
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image =
                    if (passwordVisible) R.drawable.icon_visibility else R.drawable.icon_visibility_off
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(painter = painterResource(id = image), contentDescription = null)
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = if (passwordError.value) Color.Red else Color(0xFF004FC7),
                unfocusedBorderColor = if (nameError.value) Color.Red else Color(0xFFAFBCCB),
                errorBorderColor = if (nameError.value) Color.Red else Color(0xFFAFBCCB),
                cursorColor = Color(0xFF004FC7),
                textColor = Color.Black
            )
        )
        Column(
            Modifier
                .fillMaxWidth()
                .padding(start = 5.dp, top = 12.dp)
        ) {
            RememberMeCheckbox()
        }

        Button(
            onClick = {
                nameError.value = false
                passwordError.value = false
                viewModel.authorization(name.value, password.value) { success, error ->
                    if (success) {
                        navController.navigate("load") {
                            popUpTo("login") { inclusive = true }

                        }
                    } else {
                        errorMessage.value = error ?: "Неверный логин или пароль"
                        nameError.value = true
                        passwordError.value = true
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .padding(top = 40.dp)
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF004FC7),
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            if (!viewModel.stateAuth.value.isLoading) {
                Text(
                    text = "Войти",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            if (errorMessage.value.isNotEmpty()) {
                Text(text = errorMessage.value, color = Color.Red, fontSize = 14.sp)
            }
        }
    }
}


@Composable
fun CustomCheckbox(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val icon = if (isChecked) {
        painterResource(id = R.drawable.icon_check) // Замените на вашу иконку
    } else {
        painterResource(id = R.drawable.icon_un_check) // Замените на вашу иконку
    }

    Box(
        modifier = Modifier
            .size(30.dp) // Размер чекбокса
            .clickable { onCheckedChange(!isChecked) } // Обработчик клика
            .padding(4.dp) // Отступ внутри чекбокса
    ) {
        Image(
            painter = icon,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun RememberMeCheckbox() {
    var isChecked by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 19.dp)
    ) {
        CustomCheckbox(isChecked = isChecked, onCheckedChange = { isChecked = it })
        Text(
            text = "Запомнить",
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(start = 8.dp) // Отступ между чекбоксом и текстом
        )
    }
}

