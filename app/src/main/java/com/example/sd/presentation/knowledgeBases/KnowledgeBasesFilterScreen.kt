package com.example.sd.presentation.knowledgeBases

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.sd.R
import com.example.sd.presentation.components.SearchableDropdownField
import com.example.sd.presentation.contact.ContactViewModel
import com.example.sd.presentation.createBids.Status
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.util.Calendar


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun KnowledgeBasesFilterScreen(
    navController: NavController,
    viewModel: KnowledgeBasesViewModel,
    contactViewModel: ContactViewModel
) {

    val expandedState = remember { mutableStateOf<String?>(null) }
    val contactList = remember { mutableStateOf<List<String>>(emptyList()) }

    var job: Job? = null
    val lazyPagingItems =
        contactViewModel.searchContact1(viewModel.selectedCreate).collectAsLazyPagingItems()


    LaunchedEffect(viewModel.selectedCreate) {
        job?.cancel()
        job = launch {
            delay(1000)
            snapshotFlow { lazyPagingItems }
                .collect { itemCount ->
                    val list = (0 until itemCount.itemCount).mapNotNull { index ->
                        lazyPagingItems[index]?.let {
                            it.name.toString()
                        }
                    }.distinct()
                    contactList.value = list
                }
        }
    }
    val lazyPagingItems1 =
        contactViewModel.searchContact1(viewModel.selectedUpdate).collectAsLazyPagingItems()


    LaunchedEffect(viewModel.selectedUpdate) {
        job?.cancel()
        job = launch {
            delay(1000)
            snapshotFlow { lazyPagingItems1 }
                .collect { itemCount ->
                    val list = (0 until itemCount.itemCount).mapNotNull { index ->
                        lazyPagingItems1[index]?.let {
                            it.name.toString()
                        }
                    }.distinct()
                    contactList.value = list
                }
        }
    }


    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)


    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
        }
    ) {
        Scaffold(
            topBar = {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 1.dp)
                        .padding(end = 10.dp)
                        .background(Color.White)
                ) {
                    IconButton(onClick = {
                        navController.navigate("KnowledgeBasesScreen") {
                            popUpTo("KnowledgeBasesFilterScreen") { inclusive = true }
                        }
                    }) {
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
            },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        SimpleDateRangePickerField(
                            label = "Дата cоздания",
                            startDate = viewModel.createStartDate,
                            endDate = viewModel.createEndDate,
                            onStartDateChange = { newStartDate ->
                                viewModel.createStartDate = newStartDate
                            },
                            onEndDateChange = { newEndDate ->
                                viewModel.createEndDate = newEndDate
                            }
                        )

                        SimpleDateRangePickerField(
                            label = "Дата изменения",
                            startDate = viewModel.updateStarDate,
                            endDate = viewModel.updateEndDate,
                            onStartDateChange = { newStartDate ->
                                viewModel.updateStarDate = newStartDate
                            },
                            onEndDateChange = { newEndDate ->
                                viewModel.updateEndDate = newEndDate
                            }
                        )


                        SearchableDropdownField(
                            label = "Тип",
                            placeholder = "Выберите тип cтатьи",
                            options = viewModel.itemTypeKnowledge(),
                            expandedState = expandedState,
                            currentId = "Type",
                            initialValue = viewModel.selectedType,
                            onOptionSelected = { selectedName ->
                                viewModel.selectedType = selectedName
                            }
                        )
                        // Text(text = "searchQuery.value = ${viewModel.selectedCreate}")
                        SearchableDropdownField(
                            label = "Создал",
                            placeholder = "Выберите контакт",
                            options = contactList.value,
                            expandedState = expandedState,
                            currentId = "Create",
                            initialValue = viewModel.selectedCreate,
                            onOptionSelected = { selectedName ->
                                viewModel.selectedCreate = selectedName
                            }
                        )
                        SearchableDropdownField(
                            label = "Изменил",
                            placeholder = "Выберите контакт",
                            options = contactList.value,
                            expandedState = expandedState,
                            currentId = "Update",
                            initialValue = viewModel.selectedUpdate,
                            onOptionSelected = { selectedName ->
                                viewModel.selectedUpdate = selectedName
                            }
                        )

                        TagInputField(
                            label = "Тег",
                            placeholder = "Введите тег",
                            value = viewModel.selectedTeg,
                            onValueChange = { srm ->
                                viewModel.selectedTeg = srm
                            }
                        )

                    }



                    Button(
                        onClick = {
                            viewModel.showFilter()
                            navController.navigate("KnowledgeBasesScreen")

                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF004FC7),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .padding(bottom = 20.dp)
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Text("Показать результат")
                    }
                }
            }
        )
    }
}


@Composable
fun BottomSheetContent(field: String, onSelect: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Выберите $field", style = MaterialTheme.typography.h6)

        val options = listOf("Опция 1", "Опция 2", "Опция 3")
        options.forEach { option ->
            TextButton(onClick = { onSelect(option) }) {
                Text(option)
            }
        }
    }
}


@Composable
fun SimpleDateRangePickerField(
    label: String,
    startDate: String,
    endDate: String,
    onStartDateChange: (String) -> Unit,
    onEndDateChange: (String) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()



    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 1.dp)
    ) {
        Text(
            label,
            style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 21.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(600),
                color = Color(0xFF5D6A83),
                letterSpacing = 0.2.sp
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Поле "От"
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
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(width = 1.dp, Color(0xFFE2E8F0)),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = startDate,
                        fontSize = 16.sp,
                        color = if (startDate == "ДД.ММ.ГГ") Color(0xFFA0AEC0) else Color.Black,
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

            // Поле "До"
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
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(width = 1.dp, Color(0xFFE2E8F0)),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = endDate,
                        fontSize = 16.sp,
                        color = if (endDate == "ДД.ММ.ГГ") Color(0xFFA0AEC0) else Color.Black,
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
fun TagInputField(
    label: String,
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        // Заголовок над полем
        Text(
            text = label,
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF5D6A83),
                letterSpacing = 0.2.sp
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        // Поле ввода
        OutlinedTextField(
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            placeholder = {
                Text(
                    text = placeholder,
                    color = Color(0xFFAFBCCB),
                    fontSize = 16.sp
                )
            },
            textStyle = TextStyle(
                fontSize = 16.sp,
                color = Color.Black
            ),
            singleLine = true,
            shape = RoundedCornerShape(12.dp),

            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color.White,
                cursorColor = Color(0xFF004FC7),
                focusedBorderColor = Color(0xFFE2E8F0),
                unfocusedBorderColor = Color(0xFFE2E8F0)
            )
        )
    }
}

