package com.example.sd.presentation.createBids

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
import com.example.sd.presentation.components.SearchableDropdownFieldFalse
import com.example.sd.presentation.components.StepProgressBar
import com.example.sd.presentation.contact.ContactViewModel
import com.example.sd.presentation.filter.DateTimePickerField
import com.example.sd.utils.Values
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreateBidsScreen5(
    navController: NavController,
    searchViewModel: ContactViewModel,
    viewModel: CreateBidsViewModel,
) {

    val currentDateTime = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
    val formattedDate = currentDateTime.format(formatter)
    var selectedDateTime = remember { mutableStateOf("$formattedDate") }
    var selectedDateTime2 = remember { mutableStateOf("${viewModel.respDataCreate.value}") }
    var selectedDateTime3 = remember { mutableStateOf("${viewModel.resolutionDataCreate.value}") }
    val lazyPagingItems = searchViewModel.searchContact().collectAsLazyPagingItems()
    val contact = remember { mutableStateOf(listOf<String>()) }
    LaunchedEffect(lazyPagingItems) {
        snapshotFlow { lazyPagingItems.itemCount }
            .collect { itemCount ->
                val names = (0 until itemCount).mapNotNull { index ->
                    lazyPagingItems[index]?.name
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
                DateTimePickerField("Дата регистрации", selectedDateTime.value) {
                    selectedDateTime.value = it
                    viewModel.updateField("regData", name = it, id = "")
                    Log.e("createBids", "regData->${selectedDateTime.value}   -> $it")

                }

                DateTimePickerField("План реакция", selectedDateTime2.value) {
                    selectedDateTime2.value = it
                    viewModel.updateField("respData",name = it, id = "")
                    Log.e("createBids", "respData->${selectedDateTime2.value}   -> $it")

                }
                DateTimePickerField("План разрешения", selectedDateTime3.value) {
                    selectedDateTime3.value = it
                    viewModel.updateField("resolutionData",name = it, id = "")
                    Log.e("createBids", "resolutionData->${selectedDateTime3.value}   -> $it")

                }

                SearchableDropdownFieldFalse(
                    label = "Департамент",
                    placeholder = Values.DEPARTAMENT.value,

                )

                SearchableDropdownFieldFalse(
                    label = "Владелец",
                    placeholder = Values.FIO.value,
                )
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
                    .padding(bottom = 40.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF004FC7)),
                enabled = selectedDateTime.value != "ДД.ММ.ГГ 00:00" && selectedDateTime2.value != " ДД.ММ.ГГ 00:00"&& selectedDateTime3.value != "ДД.ММ.ГГ 00:00"


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



