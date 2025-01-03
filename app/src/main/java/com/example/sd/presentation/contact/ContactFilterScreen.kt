package com.example.sd.presentation.contact

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
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
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.sd.R
import com.example.sd.presentation.accounts.AccountsViewModel
import com.example.sd.presentation.components.SearchableDropdownField
import com.example.sd.presentation.knowledgeBases.SimpleDateRangePickerField
import com.example.sd.presentation.knowledgeBases.TagInputField
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ContactFilterScreen(
    navController: NavController,
    contactViewModel: ContactViewModel,
    accountsViewModel: AccountsViewModel
) {

    val expandedState = remember { mutableStateOf<String?>(null) }
    val contactList = remember { mutableStateOf<List<String>>(emptyList()) }
    val accountList = remember { mutableStateOf<List<String>>(emptyList()) }

    var job: Job? = null
    val accountPagingItems =
        accountsViewModel.searchAccountsName(contactViewModel.selectedContragent).collectAsLazyPagingItems()


    LaunchedEffect(contactViewModel.selectedContragent) {
        job?.cancel()
        job = launch {
            delay(1000)
            snapshotFlow { accountPagingItems }
                .collect { itemCount ->
                    val list = (0 until itemCount.itemCount).mapNotNull { index ->
                        accountPagingItems[index]?.let {
                            it.name.toString()
                        }
                    }.distinct()
                    accountList.value = list
                }
        }
    }
    val lazyPagingItems =
        contactViewModel.searchContact1(contactViewModel.modifiedBy).collectAsLazyPagingItems()


    LaunchedEffect(contactViewModel.modifiedBy) {
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
        contactViewModel.searchContact1(contactViewModel.createdBy).collectAsLazyPagingItems()


    LaunchedEffect(contactViewModel.createdBy) {
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
    val lazyPagingItems2 =
        contactViewModel.searchContact1(contactViewModel.responsiblePerson).collectAsLazyPagingItems()


    LaunchedEffect(contactViewModel.responsiblePerson) {
        job?.cancel()
        job = launch {
            delay(1000)
            snapshotFlow { lazyPagingItems2 }
                .collect { itemCount ->
                    val list = (0 until itemCount.itemCount).mapNotNull { index ->
                        lazyPagingItems2[index]?.let {
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
                        navController.popBackStack()
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
                    TextButton(onClick = { contactViewModel.clearFilters() }) {
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
                            startDate = contactViewModel.creationDateStart,
                            endDate = contactViewModel.creationDateEnd,
                            onStartDateChange = { newStartDate ->
                                contactViewModel.creationDateStart = newStartDate
                            },
                            onEndDateChange = { newEndDate ->
                                contactViewModel.creationDateEnd = newEndDate
                            }
                        )

                        SimpleDateRangePickerField(
                            label = "Дата изменения",
                            startDate = contactViewModel.modificationDateStart,
                            endDate = contactViewModel.modificationDateEnd,
                            onStartDateChange = { newStartDate ->
                                contactViewModel.modificationDateStart = newStartDate
                            },
                            onEndDateChange = { newEndDate ->
                                contactViewModel.modificationDateEnd = newEndDate
                            }
                        )
                        SimpleDateRangePickerField(
                            label = "Дата рождения",
                            startDate = contactViewModel.birthDateStart,
                            endDate = contactViewModel.birthDateEnd,
                            onStartDateChange = { newStartDate ->
                                contactViewModel.birthDateStart = newStartDate
                            },
                            onEndDateChange = { newEndDate ->
                                contactViewModel.birthDateEnd = newEndDate
                            }
                        )


                        SearchableDropdownField(
                            label = "Тип",
                            placeholder = "Выберите тип cтатьи",
                            options = contactViewModel.itemTypeKnowledge(),
                            expandedState = expandedState,
                            currentId = "selectedType",
                            initialValue = contactViewModel.selectedType,
                            onOptionSelected = { selectedName ->
                                contactViewModel.selectedType = selectedName
                            }
                        )
                        // Text(text = "searchQuery.value = ${viewModel.selectedCreate}")
                        SearchableDropdownField(
                            label = "пол",
                            placeholder = "Выберите пол",
                            options = listOf("Мужской", "Женский"),
                            expandedState = expandedState,
                            currentId = "selectedGender",
                            initialValue = contactViewModel.selectedGender,
                            onOptionSelected = { selectedName ->
                                contactViewModel.selectedGender = selectedName
                            }
                        )
                        SearchableDropdownField(
                            label = "Контрагент",
                            placeholder = "Выберите контрагент",
                            options = accountList.value,
                            expandedState = expandedState,
                            currentId = "selectedContragent",
                            initialValue = contactViewModel.selectedContragent,
                            onOptionSelected = { selectedName ->
                                contactViewModel.selectedContragent = selectedName
                            }
                        )
                        TagInputField(
                            label = "Филиал",
                            placeholder = "Выберите филиал",
                            value = contactViewModel.selectedBranch,
                            onValueChange = { srm ->
                                contactViewModel.selectedBranch = srm
                            }
                        )

                        TagInputField(
                            label = "Отдел",
                            placeholder = "Выберите отдел",
                            value = contactViewModel.selectedDepartment,
                            onValueChange = { srm ->
                                contactViewModel.selectedDepartment = srm
                            }
                        )

                        SearchableDropdownField(
                            label = "Должность",
                            placeholder = "Выберите должность",
                            options = contactViewModel.itemCasta(),
                            expandedState = expandedState,
                            currentId = "selectedPosition",
                            initialValue = contactViewModel.selectedPosition,
                            onOptionSelected = { selectedName ->
                                contactViewModel.selectedPosition = selectedName
                            }
                        )

                        TagInputField(
                            label = "Мобильный телефон",
                            placeholder = "Введите номер мобильного телефона",
                            value = contactViewModel.mobilePhone,
                            onValueChange = { srm ->
                                contactViewModel.mobilePhone = srm
                            }
                        )

                        TagInputField(
                            label = "Домашний телефон",
                            placeholder = "Введите номер домашнего телефона",
                            value = contactViewModel.homePhone,
                            onValueChange = { srm ->
                                contactViewModel.homePhone = srm
                            }
                        )

                        TagInputField(
                            label = "Email",
                            placeholder = "Введите email контакта",
                            value = contactViewModel.email,
                            onValueChange = { srm ->
                                contactViewModel.email = srm
                            }
                        )
                        TagInputField(
                            label = "ID внешней системы",
                            placeholder = "Введите ID внешней система",
                            value = contactViewModel.externalSystemId,
                            onValueChange = { srm ->
                                contactViewModel.externalSystemId = srm
                            }
                        )

                        SearchableDropdownField(
                            label = "Создал",
                            placeholder = "Выберите контакт",
                            options = contactList.value,
                            expandedState = expandedState,
                            currentId = "createdBy",
                            initialValue = contactViewModel.createdBy,
                            onOptionSelected = { selectedName ->
                                contactViewModel.createdBy = selectedName
                            }
                        )

                        SearchableDropdownField(
                            label = "Изменил",
                            placeholder = "Выберите контакт",
                            options = contactList.value,
                            expandedState = expandedState,
                            currentId = "modifiedBy",
                            initialValue = contactViewModel.modifiedBy,
                            onOptionSelected = { selectedName ->
                                contactViewModel.modifiedBy = selectedName
                            }
                        )

                        SearchableDropdownField(
                            label = "Ответственный",
                            placeholder = "Выберите контакт",
                            options = contactList.value,
                            expandedState = expandedState,
                            currentId = "responsiblePerson",
                            initialValue = contactViewModel.responsiblePerson,
                            onOptionSelected = { selectedName ->
                                contactViewModel.responsiblePerson = selectedName
                            }
                        )

                    }



                    Button(
                        onClick = {
                            contactViewModel.showFilter()
                            navController.navigate("ContactScreen")

                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF004FC7),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .padding(bottom = 20.dp)
                            .fillMaxWidth().padding(horizontal =16.dp)
                            .height(48.dp)
                    ) {
                        Text("Показать результат")
                    }
                }
            }
        )
    }
}
