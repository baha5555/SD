package com.example.sd.presentation.createBids

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.sd.R
import com.example.sd.domain.service.serviceItems.Data
import com.example.sd.presentation.accounts.AccountsViewModel
import com.example.sd.presentation.components.SearchableDropdownField
import com.example.sd.presentation.components.StepProgressBar
import com.example.sd.utils.Values
import kotlinx.coroutines.Job

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun CreateBidsScreen2(
    navController: NavController,
    viewModel: CreateBidsViewModel,
    accountsViewModel: AccountsViewModel,
    ) {
    Log.i("UUID", "Values UUID2 ->${Values.UUID.value}")
    val expandedState = remember { mutableStateOf<String?>(null) }
    val supportLevels =
        viewModel.stateSupportLevels.value.response?.data?.map { Status(it.id, it.name) }
            ?: emptyList()
    val bidCategories =
        viewModel.stategetBidCategories.value.response?.data?.map  { Status(it.id, it.name) } ?: emptyList()
    val bidStatus = viewModel.stategetBidStatus.value.response?.data?.map { Status(it.id, it.name) } ?: emptyList()
    val bidPriorities =
        viewModel.stategetBidPriorities.value.response?.data?.map  { Status(it.id, it.name) } ?: emptyList()
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
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp), verticalArrangement = Arrangement.SpaceBetween
        ) {

            Column {

                Spacer(modifier = Modifier.height(20.dp))
                SearchableDropdownField(
                    label = "Категория",
                    placeholder = "Не указан",
                    options = bidCategories.map { it.name },
                    expandedState = expandedState,
                    currentId = "category",
                    initialValue = viewModel.categoryCreate.value,
                    onOptionSelected =  { selectedName ->
                        // Найдите соответствующий объект по имени
                        val selectedStatus = bidCategories.find { it.name == selectedName }
                        selectedStatus?.let {

                            viewModel.updateField("category", it.id, it.name)
                        Log.e("createBids", "category->${viewModel.categoryCreate.value}   name-> ${it.name}   id-> ${it.id}")
                    }}
                )


                if (Values.ROLES.value != "portal_users") {
                    SearchableDropdownField(
                        label = "Состояние",
                        placeholder = "Новое",
                        options = bidStatus.map { it.name },

                        expandedState = expandedState,
                        currentId = "status",
                        initialValue = viewModel.statusCreate.value,
                        onOptionSelected =  { selectedName ->
                            // Найдите соответствующий объект по имени
                            val selectedStatus = bidStatus.find { it.name == selectedName }
                            selectedStatus?.let {
                                // Обновите поле ID и имени в ViewModel
                                viewModel.updateField("status", it.id, it.name)
                                Log.e("createBids", "status->${viewModel.statusCreate.value}   name-> ${it.name}   id-> ${it.id}")
                            }
                        }
                    )

                    SearchableDropdownField(
                        label = "Приоритет",
                        placeholder = "Средний",
                        options = bidPriorities.map { it.name },

                        expandedState = expandedState,
                        currentId = "priority",
                        initialValue = viewModel.priorityCreate.value,
                        onOptionSelected = { selectedName ->
                            // Найдите соответствующий объект по имени
                            val selectedStatus = bidPriorities.find { it.name == selectedName }
                            selectedStatus?.let {
                                // Обновите поле ID и имени в ViewModel
                                viewModel.updateField("priority",  it.id, it.name)
                                Log.e(
                                    "createBids",
                                    "priority->${viewModel.priorityCreate.value}   name-> ${it.name}   id-> ${it.id}"
                                )
                            }
                        }
                    )
                    val lazyPagingItems = accountsViewModel.searchServiceItems().collectAsLazyPagingItems()

                    SearchableDropdownFieldWithPagination(
                        label = "Сервис",
                        placeholder = "Выберите сервис",
                        lazyPagingItems = lazyPagingItems,
                        expandedState = expandedState,
                        currentId = "serviceItem",
                        initialValue = viewModel.serviceItemCreate.value,
                        onOptionSelected = { selectedStatus ->
                            viewModel.updateField("serviceItem", selectedStatus.id, selectedStatus.name)
                            Log.i("serviceItem", "serviceItem = = = =${ selectedStatus.id + selectedStatus.name }")
                        }
                    )

                    SearchableDropdownField(
                        label = "Уровень поддержки",
                        placeholder = "1 линия",
                        options = supportLevels.map { it.name },

                        expandedState = expandedState,
                        currentId = "supportLevels",
                        initialValue = viewModel.levelCreate.value,
                        onOptionSelected = { selectedName ->
                            // Найдите соответствующий объект по имени
                            val selectedStatus = supportLevels.find { it.name == selectedName }
                            selectedStatus?.let {
                                // Обновите поле ID и имени в ViewModel
                                viewModel.updateField("level",  it.id, it.name)
                                Log.e(
                                    "createBids",
                                    "level->${viewModel.levelCreate.value}   name-> ${it.name}   id-> ${it.id}"
                                )
                            }
                        }
                    )

                }


            }


            Button(
                onClick = {
                    viewModel.goToNextStep()
                    viewModel.createBid {
                        navController.navigate("step6")
                        viewModel.resetStep()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
                    .height(57.dp)
                    .padding(bottom = 1.dp)
                    .imePadding(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF004FC7)),
                enabled = viewModel.categoryCreate.collectAsState().value!= "" && viewModel.statusCreate.collectAsState().value != "" && viewModel.priorityCreate.collectAsState().value != ""&& viewModel.serviceItemCreate.collectAsState().value!= "" && viewModel.levelCreate.collectAsState().value!=""

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

data class Status(
    val id: String,
    val name: String
)



@Composable
fun SearchableDropdownFieldWithPagination(
    label: String,
    placeholder: String,
    lazyPagingItems: LazyPagingItems<Data>,
    expandedState: MutableState<String?>,
    currentId: String,
    initialValue: String,
    onOptionSelected: (Status) -> Unit
) {
    var searchQuery by remember { mutableStateOf(initialValue) }

    LaunchedEffect(initialValue) {
        searchQuery = initialValue
    }

    val isExpanded = expandedState.value == currentId

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        // Метка
        Text(
            text = label,
            style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 21.sp,
                fontWeight = FontWeight(600),
                color = Color(0xFF5D6A83),
                letterSpacing = 0.2.sp,
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Поле ввода
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { query ->
                searchQuery = query
                expandedState.value = currentId
                lazyPagingItems.refresh() // Обновляем данные в зависимости от поискового запроса
            },
            placeholder = {
                Text(
                    text = placeholder,
                    fontSize = 16.sp,
                    color = Color(0xFFAFBCCB)
                )
            },
            singleLine = true,
            trailingIcon = {
                Icon(
                    modifier = Modifier.clickable {
                        expandedState.value = if (expandedState.value == currentId) null else currentId
                    },
                    painter = if (isExpanded) painterResource(id = R.drawable.icon_up) else painterResource(id = R.drawable.icon_down),
                    contentDescription = null,
                    tint = Color(0xFFA0AEC0)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expandedState.value = currentId },
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFE2E8F0),
                unfocusedBorderColor = Color(0xFFE2E8F0),
                textColor = Color.Black
            )
        )
Spacer(modifier = Modifier.height(10.dp))
        // Выпадающий список с пагинацией
        if (isExpanded) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 200.dp)
                    .background(Color.White)
                    .border(1.dp, Color(0xFFD9DCE1), RoundedCornerShape(8.dp))
            ) {
                items(lazyPagingItems) { item ->
                    item?.let {
                        Text(
                            text = it.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    searchQuery = it.name
                                    onOptionSelected(Status(it.id, it.name))
                                    expandedState.value = null
                                }
                                .padding(8.dp),
                            style = MaterialTheme.typography.body1
                        )
                    }
                }

                // Отображение состояния (загрузка, ошибка)
                lazyPagingItems.apply {
                    when {
                        loadState.append is LoadState.Loading || loadState.refresh is LoadState.Loading -> {
                            item {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                )
                            }
                        }
                        loadState.append is LoadState.Error -> {
                            item {
                                Text(
                                    text = "Ошибка загрузки данных",
                                    color = Color.Red,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
