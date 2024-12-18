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
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.sd.R
import com.example.sd.presentation.accounts.AccountsViewModel
import com.example.sd.presentation.contact.ContactViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FilterScreen(navController: NavController, viewModel: FilterViewModel) {
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    var selectedFilterLabel by remember { mutableStateOf("") }
    var filterOptions by remember { mutableStateOf(listOf<String>()) }
    var selectedValue by remember { mutableStateOf("") }

    val selectedFilters = viewModel.selectedFilters

    // Привязываем значения фильтров из ViewModel
    var selectedDateTime by remember { mutableStateOf(viewModel.selectedDateTime) }
    var selectedPlanDateTime by remember { mutableStateOf(viewModel.selectedPlanDateTime) }
    var selectedState by remember { mutableStateOf(viewModel.selectedState) }
    var selectedCategory by remember { mutableStateOf(viewModel.selectedCategory) }
    var selectedLine by remember { mutableStateOf(viewModel.selectedLine) }
    var selectedService by remember { mutableStateOf(viewModel.selectedService) }
    var selectedContact by remember { mutableStateOf(viewModel.selectedContact) }
    var selectedResponsible by remember { mutableStateOf(viewModel.selectedResponsible) }

    // Обновляем фильтры при изменении в ViewModel
    LaunchedEffect(selectedFilters) {
        // Синхронизируем состояние с ViewModel
        selectedDateTime = viewModel.selectedDateTime
        selectedPlanDateTime = viewModel.selectedPlanDateTime
        selectedState = viewModel.selectedState
        selectedCategory = viewModel.selectedCategory
        selectedLine = viewModel.selectedLine
        selectedService = viewModel.selectedService
        selectedContact = viewModel.selectedContact
        selectedResponsible = viewModel.selectedResponsible
    }
    SideEffect {

    }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            Row(
                horizontalArrangement =  Arrangement.Center,
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
                ){}
            }
            Column(modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())) {
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
                                    "Дата регистрации" -> viewModel.selectedDateTime = selectedValue
                                    "План разрешения" -> viewModel.selectedPlanDateTime =
                                        selectedValue

                                    "Состояние" -> viewModel.selectedState = selectedValue
                                    "Категория" -> viewModel.selectedCategory = selectedValue
                                    "Линия" -> viewModel.selectedLine = selectedValue
                                    "Сервис" -> viewModel.selectedService = selectedValue
                                    "Контакт" -> viewModel.selectedContact = selectedValue
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
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                    DateTimePickerField("Дата регистрации", viewModel.selectedDateTime) {
                        viewModel.selectedDateTime = it
                        viewModel.addFilter(it) // Добавляем выбранную дату в фильтры
                    }
                    DateTimePickerField("План разрешения", viewModel.selectedPlanDateTime) {
                        viewModel.selectedPlanDateTime = it
                        viewModel.addFilter(it) // Добавляем выбранную дату в фильтры
                    }
                    FilterDropdown("Состояние", viewModel.selectedState, {
                        viewModel.selectedState = it
                        viewModel.addFilter(it)
                    }, R.drawable.icon_down, bottomSheetState, scope, { label, options ->
                        selectedFilterLabel = label
                        filterOptions = options
                    })
                    FilterDropdown("Категория", viewModel.selectedCategory, {
                        viewModel.selectedCategory = it
                        viewModel.addFilter(it)
                    }, R.drawable.icon_down, bottomSheetState, scope, { label, options ->
                        selectedFilterLabel = label
                        filterOptions = options
                    })
                    FilterDropdown("Линия", viewModel.selectedLine, {
                        viewModel.selectedLine = it
                        viewModel.addFilter(it)
                    }, R.drawable.icon_down, bottomSheetState, scope, { label, options ->
                        selectedFilterLabel = label
                        filterOptions = options
                    })
                    FilterDropdown("Сервис", viewModel.selectedService, {
                        viewModel.selectedService = it
                        viewModel.addFilter(it)
                    }, R.drawable.icon_down, bottomSheetState, scope, { label, options ->
                        selectedFilterLabel = label
                        filterOptions = options
                    })
                    FilterDropdown("Контакт", viewModel.selectedContact, {
                        viewModel.selectedContact = it
                        viewModel.addFilter(it)
                    }, R.drawable.icon_down, bottomSheetState, scope, { label, options ->
                        selectedFilterLabel = label
                        filterOptions = options
                    })
                    FilterDropdown("Ответственный", viewModel.selectedResponsible, {
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

                        navController.navigate("BidsScreen")
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
    }

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FilterDropdown(
    label: String,
    selectedValue: String,
    onValueChange: (String) -> Unit,
    iconEnd: Int,
    bottomSheetState: ModalBottomSheetState,
    scope: CoroutineScope,
    openBottomSheet: (String, List<String>) -> Unit
) {
    val searchViewModel : ContactViewModel = hiltViewModel()
    val accountsViewModel : AccountsViewModel = hiltViewModel()
    val lazyPagingItemsForServiceItems = accountsViewModel.searchServiceItems().collectAsLazyPagingItems()
    val serviceItems = remember { mutableStateOf(listOf<String>()) }
    LaunchedEffect(lazyPagingItemsForServiceItems.itemCount) {
        snapshotFlow { lazyPagingItemsForServiceItems.itemCount }
            .collect { itemCount ->
                val names = (0 until itemCount).mapNotNull { index ->
                    lazyPagingItemsForServiceItems[index]?.name
                }
                serviceItems.value = names
            }
    }
    val lazyPagingItems = searchViewModel.searchContact().collectAsLazyPagingItems()
    val contact = remember { mutableStateOf(listOf<String>()) }
    LaunchedEffect(lazyPagingItems.itemCount) {
        snapshotFlow { lazyPagingItems.itemCount }
            .collect { itemCount ->
                val names = (0 until itemCount).mapNotNull { index ->
                    lazyPagingItems[index]?.name
                }
                contact.value = names
            }
    }
    fun getOptions(label: String): List<String> {
        return when (label) {
            "Линия" -> listOf("1 Линия", "2 Линия", "3 Линия")
            "Категория" -> listOf("Запрос на изменение", "Запрос на обслуживание", "Инцидент", "Не указан")
            "Состояние" -> listOf("Новое", "В работе", "Ожидает назначения ответственного", "Ожидает обработки", "Отклонено по SLA", "Отменено", "Разрешено", "Закрыто", "Ожидает реакцию пользователя")
            "Сервис" -> serviceItems.value
            "Контакт" -> contact.value
            "Ответственный" ->contact.value
            else -> listOf("Опция 1", "Опция 2", "Опция 3")
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
fun DateTimePickerField(
    label: String,
    selectedValue: String,
    onValueChange: (String) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePart = selectedValue.split(" ").getOrNull(0) ?: "ДД.ММ.ГГ"
    val timePart = selectedValue.split(" ").getOrNull(1) ?: "00:00"

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 10.dp)) {
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
                            // Обновляем только дату, комбинируя с уже существующим временем
                            onValueChange("$formattedDate $timePart")
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
                        text = datePart,
                        fontSize = 16.sp,
                        color = if (datePart == "ДД.ММ.ГГ") Color.Gray else Color.Black,
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

            // Поле выбора времени
            OutlinedButton(
                onClick = {
                    TimePickerDialog(
                        context,
                        { _, hourOfDay, minute ->
                            val formattedTime = "${
                                hourOfDay.toString().padStart(2, '0')
                            }:${minute.toString().padStart(2, '0')}"
                            // Обновляем только время, комбинируя с уже существующей датой
                            onValueChange("$datePart $formattedTime")
                        },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true
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
                        text = timePart,
                        fontSize = 16.sp,
                        color = if (timePart == "00:00") Color.Gray else Color.Black,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_time_track),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
        }
    }
}


