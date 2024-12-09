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
import com.example.sd.presentation.components.SearchableDropdownField
import com.example.sd.presentation.components.StepProgressBar
import com.example.sd.presentation.contact.ContactViewModel
import com.example.sd.utils.Values.FIO

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun CreateBidsScreen3(
    navController: NavController,
    searchViewModel: ContactViewModel,
    viewModel: CreateBidsViewModel,

    ) {
    val expandedState = remember { mutableStateOf<String?>(null) }

    val supportLevels =
        viewModel.stateSupportLevels.value.response?.data?.map { Status(it.id, it.name) }
            ?: emptyList()


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
                SearchableDropdownField(
                    label = "Ответственный",
                    placeholder = FIO.value,
                    options = contact.value.map { it.name },
                    expandedState = expandedState,
                    currentId = "responsible",
                    initialValue = viewModel.responsibleCreate.value,
                    onOptionSelected = { selectedName ->
                        val selectedStatus = contact.value.find { it.name == selectedName }
                        selectedStatus?.let {
                            viewModel.updateField("responsible",  it.id, it.name) // Сохраняем ID
                            Log.e(
                                "createBids",
                                "responsible->${viewModel.responsibleCreate.value}    name-> ${it.name}   id-> ${it.id}"
                            )
                        }
                    }
                )
            }





            Button(
                onClick = {
                    viewModel.goToNextStep()
                    navController.navigate("step4")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 40.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF004FC7)),
                enabled = viewModel.levelCreate.value != "" && viewModel.responsibleCreate.value != ""
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

