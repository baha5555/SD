package com.example.sd.presentation.contact

import android.annotation.SuppressLint
import android.app.DatePickerDialog
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sd.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


/*@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ContactFilterScreen(navController: NavController, viewModel: ContactViewModel) {
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    var selectedFilterLabel by remember { mutableStateOf("") }
    var filterOptions by remember { mutableStateOf(listOf<String>()) }
    var selectedValue by remember { mutableStateOf("") }

    val selectedFilters = viewModel.selectedFilters

    // Привязываем значения фильтров из ViewModel
    var selectedFIO by remember { mutableStateOf(viewModel.selectedFIO) }
    var createDateStart by remember { mutableStateOf(viewModel.createDateStart) }
    var createDateEnd by remember { mutableStateOf(viewModel.createDateEnd) }
    var updateDateStart by remember { mutableStateOf(viewModel.updateDateStart) }
    var updateDateEnd by remember { mutableStateOf(viewModel.updateDateEnd) }
    var selectedBirthday by remember { mutableStateOf(viewModel.selectedBirthday) }
    var selectedType by remember { mutableStateOf(viewModel.selectedType) }
    var selectedContractor by remember { mutableStateOf(viewModel.selectedContractor) }
    var selectedBranch by remember { mutableStateOf(viewModel.selectedBranch) }
    var selectedDepartment by remember { mutableStateOf(viewModel.selectedDepartment) }
    var selectedPosition by remember { mutableStateOf(viewModel.selectedPosition) }
    var selectedMobilePhone by remember { mutableStateOf(viewModel.selectedMobilePhone) }
    var selectedHomePhone by remember { mutableStateOf(viewModel.selectedHomePhone) }
    var selectedEmail by remember { mutableStateOf(viewModel.selectedEmail) }
    var selectedExternalSystemID by remember { mutableStateOf(viewModel.selectedExternalSystemID) }
    var selectedCreator by remember { mutableStateOf(viewModel.selectedCreator) }
    var selectedModifier by remember { mutableStateOf(viewModel.selectedModifier) }
    var selectedResponsible by remember { mutableStateOf(viewModel.selectedResponsible) }


    // Обновляем фильтры при изменении в ViewModel
    LaunchedEffect(selectedFilters) {
        // Синхронизируем состояние с ViewModel
        selectedFIO = viewModel.selectedFIO
        createDateStart = viewModel.createDateStart
        createDateEnd = viewModel.createDateEnd
        updateDateStart = viewModel.updateDateStart
        updateDateEnd = viewModel.updateDateEnd
        selectedBirthday = viewModel.selectedBirthday
        selectedType = viewModel.selectedType
        selectedContractor = viewModel.selectedContractor
        selectedBranch = viewModel.selectedBranch
        selectedDepartment = viewModel.selectedDepartment
        selectedPosition = viewModel.selectedPosition
        selectedMobilePhone = viewModel.selectedMobilePhone
        selectedHomePhone = viewModel.selectedHomePhone
        selectedEmail = viewModel.selectedEmail
        selectedExternalSystemID = viewModel.selectedExternalSystemID
        selectedCreator = viewModel.selectedCreator
        selectedModifier = viewModel.selectedModifier
        selectedResponsible = viewModel.selectedResponsible
    }


    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                    .height(12.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .height(5.dp)
                        .width(50.dp)
                        .background(color = Color(0xFFE2E8F0), shape = RoundedCornerShape(16.dp))
                        .padding(top = 8.dp, bottom = 11.dp)
                ) {}
            }
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                filterOptions.forEach { option ->

                    Text(
                        text = option,
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 20.sp,
                            fontFamily = FontFamily(Font(R.font.inter)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF2C2D2E),
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedValue = option
                                when (selectedFilterLabel) {
                                    "ФИО" -> viewModel.selectedFIO = selectedValue
                                    "Дата создания" -> viewModel.createDateEnd = selectedValue
                                    "Дата изменения" -> viewModel.updateDateEnd = selectedValue
                                    "Дата рождения" -> viewModel.selectedBirthday = selectedValue
                                    "Тип" -> viewModel.selectedType = selectedValue
                                    "Контрагент" -> viewModel.selectedContractor = selectedValue
                                    "Филиал" -> viewModel.selectedBranch = selectedValue
                                    "Отдел" -> viewModel.selectedDepartment = selectedValue
                                    "Должность" -> viewModel.selectedPosition = selectedValue
                                    "Мобильный телефон" -> viewModel.selectedMobilePhone =
                                        selectedValue

                                    "Домашний телефон" -> viewModel.selectedHomePhone =
                                        selectedValue

                                    "Email" -> viewModel.selectedEmail = selectedValue
                                    "ID внешней системы" -> viewModel.selectedExternalSystemID =
                                        selectedValue

                                    "Создал" -> viewModel.selectedCreator = selectedValue
                                    "Изменил" -> viewModel.selectedModifier = selectedValue
                                    "Ответственный" -> viewModel.selectedResponsible = selectedValue
                                }
                                viewModel.addFilter(selectedValue) // Добавляем выбранный фильтр
                                scope.launch { bottomSheetState.hide() }
                            }
                            .padding(vertical = 8.dp),
                        fontSize = 16.sp,
                        color = Color.Black
                    )

                }
            }
        }
    ) {
        Scaffold(topBar = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 1.dp)
                    .padding(end = 10.dp)
                    .background(Color.White)
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_left),
                        contentDescription = ""
                    )
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
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                    DateRangePickerField(
                        label = "Дата создания",
                        selectedStartDate = viewModel.createDateStart,
                        selectedEndDate = viewModel.createDateEnd,
                        onStartDateChange = { viewModel.createDateStart = it },
                        onEndDateChange = { viewModel.createDateEnd = it }
                    )
                    DateRangePickerField(
                        label = "Дата изменения",
                        selectedStartDate = viewModel.updateDateStart,
                        selectedEndDate = viewModel.updateDateEnd,
                        onStartDateChange = { viewModel.updateDateStart = it },
                        onEndDateChange = { viewModel.updateDateEnd = it }
                    )

                    DatePickerField(
                        label = "Дата рождения",
                        selectedDate = viewModel.selectedBirthday,
                        onDateChange = { viewModel.selectedBirthday = it }
                    )
                    FilterDropdown1("Тип", viewModel.selectedType, {
                        viewModel.selectedType = it
                        viewModel.addFilter(it)
                    }, R.drawable.icon_down, bottomSheetState, scope, { label, options ->
                        selectedFilterLabel = label
                        filterOptions = options
                    })
                    FilterDropdown1("Контрагент", viewModel.selectedContractor, {
                        viewModel.selectedContractor = it
                        viewModel.addFilter(it)
                    }, R.drawable.icon_down, bottomSheetState, scope, { label, options ->
                        selectedFilterLabel = label
                        filterOptions = options
                    })
                    FilterDropdown1("Филиал", viewModel.selectedBranch, {
                        viewModel.selectedBranch = it
                        viewModel.addFilter(it)
                    }, R.drawable.icon_down, bottomSheetState, scope, { label, options ->
                        selectedFilterLabel = label
                        filterOptions = options
                    })
                    FilterDropdown1("Отдел", viewModel.selectedDepartment, {
                        viewModel.selectedDepartment = it
                        viewModel.addFilter(it)
                    }, R.drawable.icon_down, bottomSheetState, scope, { label, options ->
                        selectedFilterLabel = label
                        filterOptions = options
                    })
                    FilterDropdown1("Должность", viewModel.selectedPosition, {
                        viewModel.selectedPosition = it
                        viewModel.addFilter(it)
                    }, R.drawable.icon_down, bottomSheetState, scope, { label, options ->
                        selectedFilterLabel = label
                        filterOptions = options
                    })
                    FilterDropdown1("Мобильный телефон", viewModel.selectedMobilePhone, {
                        viewModel.selectedMobilePhone = it
                        viewModel.addFilter(it)
                    }, R.drawable.icon_down, bottomSheetState, scope, { label, options ->
                        selectedFilterLabel = label
                        filterOptions = options
                    })
                    FilterDropdown1("Домашний телефон", viewModel.selectedHomePhone, {
                        viewModel.selectedHomePhone = it
                        viewModel.addFilter(it)
                    }, R.drawable.icon_down, bottomSheetState, scope, { label, options ->
                        selectedFilterLabel = label
                        filterOptions = options
                    })
                    FilterDropdown1("Email", viewModel.selectedEmail, {
                        viewModel.selectedEmail = it
                        viewModel.addFilter(it)
                    }, R.drawable.icon_down, bottomSheetState, scope, { label, options ->
                        selectedFilterLabel = label
                        filterOptions = options
                    })
                    FilterDropdown1("ID внешней системы", viewModel.selectedExternalSystemID, {
                        viewModel.selectedExternalSystemID = it
                        viewModel.addFilter(it)
                    }, R.drawable.icon_down, bottomSheetState, scope, { label, options ->
                        selectedFilterLabel = label
                        filterOptions = options
                    })
                    FilterDropdown1("Создал", viewModel.selectedCreator, {
                        viewModel.selectedCreator = it
                        viewModel.addFilter(it)
                    }, R.drawable.icon_down, bottomSheetState, scope, { label, options ->
                        selectedFilterLabel = label
                        filterOptions = options
                    })
                    FilterDropdown1("Изменил", viewModel.selectedModifier, {
                        viewModel.selectedModifier = it
                        viewModel.addFilter(it)
                    }, R.drawable.icon_down, bottomSheetState, scope, { label, options ->
                        selectedFilterLabel = label
                        filterOptions = options
                    })
                    FilterDropdown1("Ответственный", viewModel.selectedResponsible, {
                        viewModel.selectedResponsible = it
                        viewModel.addFilter(it)
                    }, R.drawable.icon_down, bottomSheetState, scope, { label, options ->
                        selectedFilterLabel = label
                        filterOptions = options
                    })
                }


                Spacer(modifier = Modifier.height(25.dp))

                val resultCount = selectedFilters.size // Количество фильтров для отображения

                Button(
                    onClick = {

                        navController.navigate("ContactScreen")
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF004FC7)),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text(
                        text = "Показать (${resultCount} результата)",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}*/

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FilterDropdown1(
    label: String,
    selectedValue: String,
    onValueChange: (String) -> Unit,
    iconEnd: Int,
    bottomSheetState: ModalBottomSheetState,
    scope: CoroutineScope,
    openBottomSheet: (String, List<String>) -> Unit
) {
val viewModel: ContactViewModel = hiltViewModel()
    val bidOrigins = viewModel.stateGetContactType.value.response?.data?.map  { it.name } ?: emptyList()

    fun getOptions(label: String): List<String> {
        return when (label) {
            "Тип" -> bidOrigins
            "Пол" -> listOf("Мужской", "Женский")
            "Контрагент" -> listOf("1 Линия", "2 Линия", "3 Линия")
            "Филиал" -> listOf("1 Линия", "2 Линия", "3 Линия")
            "Отдел" -> listOf("1 Линия", "2 Линия", "3 Линия")
            "Должность" -> listOf("1 Линия", "2 Линия", "3 Линия")
            "Создал" -> listOf("1 Линия", "2 Линия", "3 Линия")
            "Изменил" -> listOf("1 Линия", "2 Линия", "3 Линия")
            "Ответственный" -> listOf("1 Линия", "2 Линия", "3 Линия")

            else -> listOf("")
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            label,
            style = TextStyle(
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
                openBottomSheet(label, getOptions(label))
                scope.launch { bottomSheetState.show() }
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
    }
}

@Composable
fun DateRangePickerField(
    label: String,
    selectedStartDate: String,
    selectedEndDate: String,
    onStartDateChange: (String) -> Unit,
    onEndDateChange: (String) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val startDate = selectedStartDate.split(" ").getOrNull(0) ?: "ДД-ММ-ГГ"
    val endDate = selectedEndDate.split(" ").getOrNull(0) ?: "ДД-ММ-ГГ"

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
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
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Поле выбора начальной даты "От"
            OutlinedButton(
                onClick = {
                    DatePickerDialog(
                        context,
                        { _, year, month, dayOfMonth ->
                            val formattedDate = "${
                                dayOfMonth.toString().padStart(2, '0')
                            }.${
                                (month + 1).toString().padStart(2, '0')
                            }.$year"
                            // Обновляем только дату "От"
                            onStartDateChange(formattedDate)
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    ).show()
                },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = startDate,
                        fontSize = 16.sp,
                        color = if (startDate == "ДД-ММ-ГГ") Color.Gray else Color.Black,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.icon_calendar),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(25.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Поле выбора конечной даты "До"
            OutlinedButton(
                onClick = {
                    DatePickerDialog(
                        context,
                        { _, year, month, dayOfMonth ->
                            val formattedDate = "${
                                dayOfMonth.toString().padStart(2, '0')
                            }.${
                                (month + 1).toString().padStart(2, '0')
                            }.$year"
                            // Обновляем только дату "До"
                            onEndDateChange(formattedDate)
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    ).show()
                },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = endDate,
                        fontSize = 16.sp,
                        color = if (endDate == "ДД-ММ-ГГ") Color.Gray else Color.Black,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.icon_calendar),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun DatePickerField(
    label: String,
    selectedDate: String,
    onDateChange: (String) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePart = selectedDate.ifEmpty { "ДД-ММ-ГГ" } // Показываем заглушку, если дата не выбрана

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
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
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Поле выбора даты
            OutlinedButton(
                onClick = {
                    DatePickerDialog(
                        context,
                        { _, year, month, dayOfMonth ->
                            val formattedDate = "${
                                dayOfMonth.toString().padStart(2, '0')
                            }.${
                                (month + 1).toString().padStart(2, '0')
                            }.$year"
                            onDateChange(formattedDate) // Обновляем только дату
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    ).show()
                },
                modifier = Modifier
                    .fillMaxWidth() // Используем всю ширину
                    .height(56.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = datePart,
                        fontSize = 16.sp,
                        color = if (datePart == "ДД-ММ-ГГ") Color.Gray else Color.Black,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.icon_calendar),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
        }
    }
}