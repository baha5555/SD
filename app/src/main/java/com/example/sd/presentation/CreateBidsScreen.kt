package com.example.sd.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
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
import androidx.navigation.NavController
import com.example.sd.R
import com.example.sd.presentation.authorization.AuthViewModel

@Composable
fun CreateBidsScreen(navController: NavController, viewModel: AuthViewModel) {
    val options = listOf("Option 1", "Option 2", "Option 3") // Пример списка для dropdown
    val name = viewModel.stateAboutMe.value.response?.fio
    val name2 = remember { mutableStateOf("$name") }
    val tema = remember { mutableStateOf("") }
    val priznaki = remember { mutableStateOf("") }

    val bidCategories   =viewModel.stategetBidCategories.value.response?.data?.map { it.name } ?: emptyList()

    Log.i("name2", "name2-> ${name2.value}")
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                verticalAlignment = CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
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
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp), verticalArrangement = Arrangement.SpaceBetween
        ) {

            Column {

                Text(
                    text = "Создание обращения",
                    style = TextStyle(
                        fontSize = 28.sp,
                        lineHeight = 36.sp,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(700),
                        color = Color(0xFF090A0A),
                    ), modifier = Modifier.padding(bottom = 14.dp)
                )

                Text(
                    text = "Чтобы создать заявку заполните все поля в данном окне",
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFFA0AEC0),
                    ), modifier = Modifier.padding(bottom = 30.dp)
                )

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(color = Color(0xFFE2E8F0))
                )
                Text(
                    text = "Тема",
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 21.sp,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFF5D6A83),
                        letterSpacing = 0.2.sp,
                    ), modifier = Modifier.padding(bottom = 8.dp, top = 30.dp)
                )

                OutlinedTextField(
                    value = tema.value,
                    onValueChange = { tema.value = it },
                    placeholder = {
                        androidx.compose.material3.Text(
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
                    text = "Признаки (симпомы)",
                    style = TextStyle.Default.copy(fontSize = 14.sp),
                    modifier = Modifier.padding(bottom = 8.dp, top = 30.dp)
                )
                // Описание проблемы
                OutlinedTextField(
                    value = priznaki.value,
                    onValueChange = { priznaki.value = it },
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


                // Выпадающий список для состояния
                DropdownField(
                    label = "Состояние",
                    placeholder = "Новое",
                    options = options,
                    onOptionSelected = { }
                )

                // Выпадающий список для категории
                DropdownField(
                    label = "Категория",
                    placeholder = "Не указан",
                    options = bidCategories,
                    onOptionSelected = { }
                )

                // Выпадающий список для приоритета
                DropdownField(
                    label = "Приоритет",
                    placeholder = "Средний",
                    options = options,
                    onOptionSelected = { }
                )
                DropdownField(
                    label = "Уровень поддержки",
                    placeholder = "1 линия",
                    options = options,
                    onOptionSelected = { }
                )
                DropdownField(
                    label = "Ответственный",
                    placeholder = name2.value,
                    options = options,
                    onOptionSelected = { }
                )

            }


            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF004FC7))
            ) {
                Text(
                    text = "Создать обращение",
                    color = Color.White,
                    style = MaterialTheme.typography.button
                )
            }
        }
    }

}

@Composable
fun DropdownField(
    label: String,
    placeholder: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit
) {
    var selectedOption by remember { mutableStateOf(placeholder) } // Значение по умолчанию
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {

        Text(
            text = label,
            style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 21.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(600),
                color = Color(0xFF5D6A83),
                letterSpacing = 0.2.sp,
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )


        OutlinedTextField(
            value = selectedOption,
            onValueChange = {
                selectedOption = it
                expanded = it.isNotEmpty()
            },
            placeholder = {
                androidx.compose.material.Text(
                    text = placeholder,
                    fontSize = 16.sp,
                    color = Color(0xFFAFBCCB)
                )
            },
            textStyle = TextStyle.Default.copy(fontSize = 14.sp),
            singleLine = true,
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.icon_down),
                    contentDescription = null, tint = Color(0xFFA0AEC0)
                )
            },
            modifier = Modifier
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


        if (expanded) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .background(Color.White)
                    .border(1.dp, Color(0xFFD9DCE1), RoundedCornerShape(8.dp))
                    .padding(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 200.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    options.forEach { option ->
                        Text(
                            text = option,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedOption = option
                                    onOptionSelected(option)
                                    expanded = false
                                }
                                .padding(8.dp),
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
            }
        }
    }
}