package com.example.sd.presentation.filter

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.sd.R


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FilterScreen(navController: NavController, viewModel: FilterViewModel) {



    var selectedDateTime by remember { mutableStateOf("Выберите дату и время") }
    var selectedPlanDateTime by remember { mutableStateOf("Выберите дату и время") }
    var selectedState by remember { mutableStateOf("Выберите состояние") }
    var selectedCategory by remember { mutableStateOf("Выберите категорию") }
    var selectedLine by remember { mutableStateOf("Выберите линию") }
    var selectedService by remember { mutableStateOf("Выберите сервис") }
    var selectedContact by remember { mutableStateOf("Выберите контакт") }
    var selectedResponsible by remember { mutableStateOf("Выберите ответственного") }

    // Результаты, рассчитываются на основе фильтров
    val resultCount by remember {
        derivedStateOf {
            listOf(
                selectedDateTime, selectedPlanDateTime, selectedState, selectedCategory,
                selectedLine, selectedService, selectedContact, selectedResponsible
            ).count { it.isNotEmpty() && !it.startsWith("Выберите") }
        }
    }


    Scaffold(topBar = {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(horizontal = 1.dp)
                .padding(end = 10.dp)
                .background(Color.White)
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(painter = painterResource(id = R.drawable.icon_left), contentDescription = "")
            }
            Text(
                text = "Фильтр",
                style = TextStyle(
                    fontSize = 18.sp,
                    lineHeight = 18.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF2C2D2E),
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier.padding(start = 40.dp)
            )
            TextButton(onClick = { viewModel.clearFilters() }) {
                Text(
                    text = "Очистить",
                    style = TextStyle(
                        fontSize = 18.sp,
                        lineHeight = 18.sp,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(500),
                        color = Color(0xFF004FC7),
                        textAlign = TextAlign.Right,
                    )
                )
            }
        }
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Заголовок и кнопка очистки


            Spacer(modifier = Modifier.height(8.dp))

            Column(verticalArrangement = Arrangement.spacedBy(25.dp)) {
                DateTimePickerField("Дата регистрации", viewModel.selectedDateTime) {
                    viewModel.selectedDateTime = it
                    viewModel.addFilter(it) // Добавляем выбранную дату в фильтры
                }
                DateTimePickerField("План разрешения", viewModel.selectedPlanDateTime) {
                    viewModel.selectedPlanDateTime = it
                    viewModel.addFilter(it) // Добавляем выбранную дату в фильтры
                }
                FilterDropdown("Состояние", viewModel.selectedState, onValueChange = {
                    viewModel.selectedState = it
                    viewModel.addFilter(it) // Добавляем состояние в фильтры
                }, iconEnd = R.drawable.icon_down)
                FilterDropdown("Категория", viewModel.selectedCategory, onValueChange = {
                    viewModel.selectedCategory = it
                    viewModel.addFilter(it) // Добавляем категорию в фильтры
                }, iconEnd = R.drawable.icon_down)
                FilterDropdown("Линия", viewModel.selectedLine, onValueChange = {
                    viewModel.selectedLine = it
                    viewModel.addFilter(it) // Добавляем линию в фильтры
                }, iconEnd = R.drawable.icon_down)
                FilterDropdown("Сервис", viewModel.selectedService, onValueChange = {
                    viewModel.selectedService = it
                    viewModel.addFilter(it) // Добавляем сервис в фильтры
                }, iconEnd = R.drawable.icon_down)
                FilterDropdown("Контакт", viewModel.selectedContact, onValueChange = {
                    viewModel.selectedContact = it
                    viewModel.addFilter(it) // Добавляем контакт в фильтры
                }, iconEnd = R.drawable.icon_down)
                FilterDropdown("Ответственный", viewModel.selectedResponsible, onValueChange = {
                    viewModel.selectedResponsible = it
                    viewModel.addFilter(it) // Добавляем ответственного в фильтры
                }, iconEnd = R.drawable.icon_down)
            }


            Spacer(modifier = Modifier.height(25.dp))


                Button(
                    onClick = {
                        // Добавляем выбранные фильтры в ViewModel
                        viewModel.addFilter(selectedDateTime)
                        viewModel.addFilter(selectedPlanDateTime)
                        viewModel.addFilter(selectedState)
                        viewModel.addFilter(selectedCategory)
                        viewModel.addFilter(selectedLine)
                        viewModel.addFilter(selectedService)
                        viewModel.addFilter(selectedContact)
                        viewModel.addFilter(selectedResponsible)

                        navController.navigate("BidsScreen")

                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF004FC7)),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text(
                        text = "Показать ($resultCount результата)",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }


        }
    }

}

@Composable
fun DateTimePickerField(label: String, selectedValue: String, onValueChange: (String) -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            label, style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 21.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(600),
                color = Color(0xFF5D6A83),
                letterSpacing = 0.2.sp,
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedButton(
            onClick = {
                DatePickerDialog(
                    context,
                    { _, year, month, dayOfMonth ->
                        calendar.set(year, month, dayOfMonth)
                        TimePickerDialog(
                            context,
                            { _, hourOfDay, minute ->
                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                                calendar.set(Calendar.MINUTE, minute)
                                val formattedDateTime = "${
                                    dayOfMonth.toString().padStart(2, '0')
                                }.${
                                    (month + 1).toString().padStart(2, '0')
                                }.$year ${hourOfDay.toString().padStart(2, '0')}:${
                                    minute.toString().padStart(2, '0')
                                }"
                                onValueChange(formattedDateTime)
                            },
                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE),
                            true
                        ).show()
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(vertical = 4.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = selectedValue,
                    fontSize = 16.sp,
                    color = if (selectedValue.startsWith("Выберите")) Color.Gray else Color.Black,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.weight(1f)

                )
                Icon(
                    painter = painterResource(id = R.drawable.icon_calendar),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(25.dp)
                        .padding(start = 1.dp)
                )

            }

        }
    }
}


@Composable
fun FilterDropdown(
    label: String,
    selectedValue: String,
    onValueChange: (String) -> Unit,
    iconEnd: Int
) {
    var showDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            label, style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 21.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(600),
                color = Color(0xFF5D6A83),
                letterSpacing = 0.2.sp,
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedButton(
            onClick = { showDialog = true },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(vertical = 4.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (label == "Контакт" || label == "Ответственный") {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_profile_2),
                        contentDescription = null,
                        tint = Color(0xFFA0AEC0),
                        modifier = Modifier
                            .size(30.dp)
                            .padding(end = 10.dp)
                    )
                }
                Text(
                    text = selectedValue,
                    fontSize = 16.sp,
                    color = if (selectedValue.startsWith("Выберите")) Color.Gray else Color.Black,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    painter = painterResource(id = iconEnd),
                    contentDescription = null,
                    tint = Color(0xFFA0AEC0),
                    modifier = Modifier
                        .size(25.dp)
                        .padding(start = 4.dp)
                )
            }
        }

        if (showDialog) {
            Dialog(onDismissRequest = { showDialog = false }) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Выберите значение", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        listOf("Опция 1", "Опция 2", "Опция 3").forEach { option ->
                            Text(
                                text = option,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onValueChange(option)
                                        showDialog = false
                                    }
                                    .padding(vertical = 8.dp),
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}