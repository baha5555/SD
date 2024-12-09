package com.example.sd.presentation.createBids

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
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
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.sd.R
import com.example.sd.presentation.accounts.AccountsViewModel
import com.example.sd.presentation.components.SearchableDropdownField
import com.example.sd.presentation.components.StepProgressBar
import com.example.sd.presentation.contact.ContactViewModel

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun CreateBidsScreen4(
    navController: NavController,
    searchViewModel: ContactViewModel,
    viewModel: CreateBidsViewModel,
    accountsViewModel: AccountsViewModel
) {
    val expandedState = remember { mutableStateOf<String?>(null) }
    val bidOrigins = viewModel.stateBidsOrigins.value.response?.data?.map  { Status(it.id, it.name) } ?: emptyList()

    val lazyPagingItems = searchViewModel.searchContact().collectAsLazyPagingItems()
    val contact = remember { mutableStateOf(listOf<Status>()) }
    LaunchedEffect(lazyPagingItems) {
        snapshotFlow { lazyPagingItems.itemCount }
            .collect { itemCount ->
                val names = (0 until itemCount).mapNotNull { index ->
                    lazyPagingItems[index]?.let {
                        Status(it.id.toString(), it.name.toString())
                    }
                }
                contact.value = names
            }
    }
    val lazyPagingItemsForAccounts = accountsViewModel.searchAccounts().collectAsLazyPagingItems()
    val accounts = remember { mutableStateOf(listOf<Status>()) }
    LaunchedEffect(lazyPagingItemsForAccounts) {
        snapshotFlow { lazyPagingItemsForAccounts.itemCount }
            .collect { itemCount ->
                val names = (0 until itemCount).mapNotNull { index ->
                    lazyPagingItemsForAccounts[index]?.let {
                        Status(it.id, it.name)
                    }
                }
                accounts.value = names
            }
    }
    val lazyPagingItemsForServicePacts = accountsViewModel.searchServicePacts().collectAsLazyPagingItems()
    val servicePacts = remember { mutableStateOf(listOf<Status>()) }
    LaunchedEffect(lazyPagingItemsForServicePacts) {
        snapshotFlow { lazyPagingItemsForServicePacts.itemCount }
            .collect { itemCount ->
                val names = (0 until itemCount).mapNotNull { index ->
                    lazyPagingItemsForServicePacts[index]?.let {
                        Status(it.id, it.name)
                    }
                }
                servicePacts.value = names
            }
    }
    val lazyPagingItemsForServiceItems = accountsViewModel.searchServiceItems().collectAsLazyPagingItems()
    val serviceItems = remember { mutableStateOf(listOf<Status>()) }
    LaunchedEffect(lazyPagingItemsForServiceItems) {
        snapshotFlow { lazyPagingItemsForServiceItems.itemCount }
            .collect { itemCount ->
                val names = (0 until itemCount).mapNotNull { index ->
                    lazyPagingItemsForServiceItems[index]?.let {
                        Status(it.id, it.name)
                    }
                }
                serviceItems.value = names
            }
    }
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
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp), verticalArrangement = Arrangement.SpaceBetween
        ) {

            Column {

                Spacer(modifier = Modifier.height(20.dp))
                SearchableDropdownField(
                    label = "Контакт",
                    placeholder = "Выберите контакт",
                    options = contact.value.map { it.name },

                    expandedState = expandedState,
                    currentId = "contact",
                    initialValue = viewModel.contactCreate.value,
                    onOptionSelected = { selectedName ->
                        val selectedContact = contact.value.find { it.name == selectedName }
                        selectedContact?.let {
                            viewModel.updateField("contact",  it.id, it.name) // Сохраняем ID контакта
                            Log.e(
                                "createBids",
                                "contact->${viewModel.contactCreate.value}   name-> ${it.name}   id-> ${it.id}"
                            )
                        }
                    }
                )
                SearchableDropdownField(
                    label = "Контрагент",
                    placeholder = "Выберите контрагента",
                    options = accounts.value.map { it.name }, // Отображаем имена в списке
                    expandedState = expandedState,
                    currentId = "accounts",
                    initialValue = viewModel.accountCreate.value, // Текущее значение
                    onOptionSelected = { selectedName ->
                        val selectedAccount = accounts.value.find { it.name == selectedName }
                        selectedAccount?.let {
                            viewModel.updateField("account",  it.id, it.name) // Сохраняем ID контрагента
                            Log.e(
                                "createBids",
                                "account->${viewModel.accountCreate.value}    name-> ${it.name}   id-> ${it.id}"
                            )
                        }
                    }
                )
                SearchableDropdownField(
                    label = "Сервисный договор",
                    placeholder = "Сервисный договор по умолчанию",
                    options = servicePacts.value.map { it.name }, // Отображаем только имена
                    expandedState = expandedState,
                    currentId = "servicePacts",
                    initialValue = viewModel.servicePactCreate.value, // Текущее значение
                    onOptionSelected = { selectedName ->
                        val selectedServicePact = servicePacts.value.find { it.name == selectedName }
                        selectedServicePact?.let {
                            viewModel.updateField("servicePact", it.id, it.name) // Сохраняем ID
                            Log.e(
                                "createBids",
                                "servicePact->${viewModel.servicePactCreate.value}   name-> ${it.name}   id-> ${it.id}"
                            )
                        }
                    }
                )

                SearchableDropdownField(
                    label = "Сервис",
                    placeholder = "Выберите сервис",
                    options = serviceItems.value.map { it.name },

                    expandedState = expandedState,
                    currentId = "serviceItem",
                    initialValue = viewModel.serviceItemCreate.value,
                    onOptionSelected = { selectedValue ->
                        val selectedServicePact = serviceItems.value.find { it.name == selectedValue }
                        selectedServicePact?.let {
                            viewModel.updateField("serviceItem",  it.id, it.name)


                        // viewModel.updateField("serviceItem", selectedValue)
                        Log.e(
                            "createBids",
                            "serviceItem->${viewModel.serviceItemCreate.value}   name-> ${it.name}   id-> ${it.id}"
                        )
                    }
                        val selectedService = lazyPagingItemsForServiceItems.itemSnapshotList.items
                            .find { it.name == selectedValue }
                        Log.i("regdata","selectedService.reaction_time.toLong()->${selectedService?.reaction_time?.toLong()}  ")
                        Log.i("regdata","selectedService.solution_time.toLong()->${selectedService?.solution_time?.toLong()}  ")
                        Log.e("createBids", "selectedService.reaction_time->${selectedService?.reaction_time?.toLong()}   -> $selectedValue")
                        Log.e("createBids", "selectedService.solution_time->${selectedService?.solution_time?.toLong()}   -> $selectedValue")

                        if (selectedService != null) {
                            selectedService.service_status_id
                            viewModel.updateServiceSelection(
                                selectedService = selectedValue,
                                reactionTime = selectedService.reaction_time.toLong(),
                                solutionTime = selectedService.solution_time.toLong()
                            )
                        }
                    }
                )
                SearchableDropdownField(
                    label = "Происхождение",
                    placeholder = "Выберите происхождение",
                    options = bidOrigins.map { it.name }, // Отображаем только имена
                    expandedState = expandedState,
                    currentId = "bidOrigins",
                    initialValue = viewModel.originCreate.value, // Текущее значение
                    onOptionSelected = { selectedName ->
                        val selectedOrigin = bidOrigins.find { it.name == selectedName }
                        selectedOrigin?.let {
                            viewModel.updateField("origin",  it.id, it.name) // Сохраняем ID
                            Log.e(
                                "createBids",
                                "origin->${viewModel.originCreate.value}   name-> ${it.name}   id-> ${it.id}"
                            )
                        }
                    }
                )

            }





            Button(
                onClick = {
                    viewModel.goToNextStep()
                    navController.navigate("step5")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 40.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF004FC7)),
                enabled = viewModel.contactCreate.value!= "" && viewModel.accountCreate.value != "" && viewModel.originCreate.value != "" && viewModel.serviceItemCreate.value != "" && viewModel.servicePactCreate.value != ""

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



