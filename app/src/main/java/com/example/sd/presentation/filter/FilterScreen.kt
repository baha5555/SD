package com.example.sd.presentation.filter

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import com.example.sd.presentation.components.DateRangePickerInput
import com.example.sd.presentation.components.SearchableDropdownField
import com.example.sd.presentation.components.SearchableDropdownFieldWithPaginationContact
import com.example.sd.presentation.contact.ContactViewModel
import com.example.sd.presentation.createBids.SearchableDropdownFieldWithPagination
import com.example.sd.presentation.knowledgeBases.formatTo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FilterScreen(
    navController: NavController,
    viewModel: FilterViewModel,
    accountsViewModel: AccountsViewModel,
    searchViewModel: ContactViewModel
) {
    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val expandedState = remember { mutableStateOf<String?>(null) }

    var job: Job? = null


    var selectedDateTime by remember { mutableStateOf(Pair<Long?, Long?>(null, null)) }
    var selectedPlanDateTime by remember { mutableStateOf(Pair<Long?, Long?>(null, null)) }













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

        }
    ) {
        Scaffold(topBar = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 1.dp)
                    .padding(end = 10.dp,top = 10.dp)
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
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.height(1.dp))

                Column(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f)
                    .padding(0.dp)
                    .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(20.dp)) {
                    Column {
                        Text(
                            text = "Дата регистрации",
                            style = TextStyle(
                                fontSize = 14.sp,
                                lineHeight = 21.sp,
                                fontFamily = FontFamily(Font(R.font.inter)),
                                fontWeight = FontWeight(600),
                                color = Color(0xFF5D6A83),
                                letterSpacing = 0.2.sp,
                            ),
                            modifier = Modifier.padding(bottom = 0.dp)
                        )
                        DateRangePickerInput(
                            selectedDateRange = selectedDateTime,
                            onDateRangeSelected = { dateRange ->
                                selectedDateTime = dateRange
                            },
                            onApiCall = { first, second ->
                                val startDate = first.formatTo("yyyy-MM-dd")
                                val endDate = second.formatTo("yyyy-MM-dd")
                                viewModel.selectedDateTime = Pair(startDate, endDate)
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(0.dp))
                    Column {

                        Text(
                            text = "План разрешения",
                            style = TextStyle(
                                fontSize = 14.sp,
                                lineHeight = 21.sp,
                                fontFamily = FontFamily(Font(R.font.inter)),
                                fontWeight = FontWeight(600),
                                color = Color(0xFF5D6A83),
                                letterSpacing = 0.2.sp,
                            ),
                            modifier = Modifier.padding(bottom = 1.dp)
                        )

                        DateRangePickerInput(
                            selectedDateRange = selectedPlanDateTime,
                            onDateRangeSelected = { dateRange ->
                                selectedPlanDateTime = dateRange
                            },
                            onApiCall = { first, second ->
                                val startDate = first.formatTo("yyyy-MM-dd")
                                val endDate = second.formatTo("yyyy-MM-dd")
                                viewModel.selectedPlanDateTime = Pair(startDate, endDate)
                            }
                        )
                    }


                    SearchableDropdownField(
                        label = "Состояние",
                        placeholder = "Выберите состояние",
                        options = listOf(
                            "Новое",
                            "В работе",
                            "Ожидает назначения ответственного",
                            "Ожидает обработки",
                            "Отклонено по SLA",
                            "Отменено",
                            "Разрешено",
                            "Закрыто",
                            "Ожидает реакцию пользователя"
                        ),
                        expandedState = expandedState,
                        currentId = "selectedState",
                        initialValue = viewModel.selectedState,
                        onOptionSelected = { selectedName ->
                            viewModel.selectedState = selectedName
                        }
                    )

                    SearchableDropdownField(
                        label = "Категория",
                        placeholder = "Выберите категорию",
                        options = listOf(
                            "Запрос на изменение",
                            "Запрос на обслуживание",
                            "Инцидент",
                            "Не указан"
                        ),
                        expandedState = expandedState,
                        currentId = "selectedCategory",
                        initialValue = viewModel.selectedCategory,
                        onOptionSelected = { selectedName ->
                            viewModel.selectedCategory = selectedName
                        }
                    )

                    SearchableDropdownField(
                        label = "Линия",
                        placeholder = "Выберите категорию",
                        options = listOf("1 Линия", "2 Линия", "3 Линия"),
                        expandedState = expandedState,
                        currentId = "selectedLine",
                        initialValue = viewModel.selectedLine,
                        onOptionSelected = { selectedName ->
                            viewModel.selectedLine = selectedName
                        }
                    )

                    val lazyPagingItems =
                        accountsViewModel.searchServiceItems().collectAsLazyPagingItems()

                    SearchableDropdownFieldWithPagination(
                        label = "Сервис",
                        placeholder = "Выберите сервис",
                        lazyPagingItems = lazyPagingItems,
                        expandedState = expandedState,
                        currentId = "serviceItem",
                        initialValue = viewModel.selectedService,
                        onOptionSelected = { selectedStatus ->
                            viewModel.selectedService = selectedStatus.name
                            Log.i(
                                "serviceItem",
                                "serviceItem = = = =${selectedStatus.id + selectedStatus.name}"
                            )
                        }
                    )



                    SearchableDropdownFieldWithPaginationContact(
                        label = "Контакт",
                        placeholder = "Выберите контакт",
                        viewModel = searchViewModel,
                        expandedState = expandedState,
                        currentId = "selectedContact",
                        initialValue = viewModel.selectedContact,
                        onOptionSelected = { selectedName ->
                            viewModel.selectedContact = selectedName.name
                        }
                    )
                    SearchableDropdownFieldWithPaginationContact(
                        label = "Ответственный",
                        placeholder = "Выберите контакт",
                        viewModel = searchViewModel,
                        expandedState = expandedState,
                        currentId = "selectedResponsible",
                        initialValue = viewModel.selectedResponsible,
                        onOptionSelected = { selectedName ->
                            viewModel.selectedResponsible = selectedName.name
                        }
                    )


                }

            Spacer(modifier = Modifier.height(0.dp))


            Button(
                onClick = {
                    viewModel.showFilter()
                    navController.navigate("BidsScreen")
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF004FC7)),
                shape = RoundedCornerShape(8.dp),
                elevation = ButtonDefaults.elevation(0.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = "Показать результат",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
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


