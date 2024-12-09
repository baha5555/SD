package com.example.sd.presentation.createBids

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.sd.presentation.components.SearchableDropdownField
import com.example.sd.presentation.components.StepProgressBar
import com.example.sd.presentation.contact.ContactViewModel
import com.example.sd.utils.Values

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun CreateBidsScreen2(
    navController: NavController,
    viewModel: CreateBidsViewModel,

    ) {
    Log.i("UUID", "Values UUID2 ->${Values.UUID.value}")
    val expandedState = remember { mutableStateOf<String?>(null) }
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
                            // Обновите поле ID и имени в ViewModel
                            viewModel.updateField("category", it.id, it.name)
                        Log.e("createBids", "category->${viewModel.categoryCreate.value}   name-> ${it.name}   id-> ${it.id}")
                    }}
                )


                if (Values.ROLES.value != "portal_users") {
                    SearchableDropdownField(
                        label = "Состояние",
                        placeholder = "Новое",
                        options = bidStatus.map { it.name }, // Передаем данные

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
                }


            }


            Button(
                onClick = {
                    viewModel.goToNextStep()
                    if (Values.ROLES.value != "portal_users") {
                        navController.navigate("step3")
                    } else {
                        navController.navigate("step6")

                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 40.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF004FC7)),
                enabled = viewModel.categoryCreate.collectAsState().value!= "" && viewModel.statusCreate.collectAsState().value != "" && viewModel.priorityCreate.collectAsState().value != ""

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