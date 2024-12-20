package com.example.sd.presentation.contact

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sd.R
import com.example.sd.presentation.authorization.AuthViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailScreenContact(navController: NavController, viewModel: ContactViewModel) {

    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)



    ModalBottomSheetLayout(
        sheetContent = {

        },
        sheetState = sheetState,
        scrimColor = Color.Transparent // Убирает затемнение фона
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    modifier = Modifier.height(60.dp),
                    backgroundColor = Color.White,
                    contentColor = Color.Black,
                    elevation = 0.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 8.dp)
                            .padding(top = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                        Text(
                            text = "${viewModel.selectedContact.value.contact_type_id?.name}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        IconButton(onClick = {  }) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_copy),
                                contentDescription = "Copy"
                            )
                        }
                    }
                }
            },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.88f)
                    ) {
                        item {
                            Text(
                                text = "Основная информация",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                            InfoItem(
                                label = "ФИО",
                                value = "${viewModel.selectedContact.value.name}"
                            )
                            InfoItem(
                                label = "Пол",
                                value = "${viewModel.selectedContact.value.gender_id?.name}"
                            )
                            InfoItem(
                                label = "Дата рождения",
                                value = "${viewModel.selectedContact.value.birth_date}"
                            )
                            InfoItem(
                                label = "Мобильный телефон",
                                value = "${viewModel.selectedContact.value.mobile_phone}"
                            )
                            InfoItem(
                                label = "Должность",
                                value = "${viewModel.selectedContact.value.casta_id?.name}"
                            )
                            InfoItem(
                                label = "Департамент",
                                value = "${viewModel.selectedContact.value.department_id?.name}"
                            )
                            InfoItem(
                                label = "Дата создания",
                                value = "${viewModel.selectedContact.value.created_at}"
                            )
                            InfoItem(
                                label = "Дата изменения",
                                value = "${viewModel.selectedContact.value.updated_at}"
                            )
                            InfoItem(
                                label = "Ответственный",
                                value = "${viewModel.selectedContact.value.owner_id?.name}"
                            )

                        }
                    }
                }
            },
        )
    }
}

@Composable
fun InfoItem(label: String, value: String, valueColor: Color = Color.Black) {
    Column(modifier = Modifier.padding(vertical = 15.dp), horizontalAlignment = Alignment.Start) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = value,
            fontSize = 16.sp,
            color = valueColor,
            fontWeight = FontWeight.Medium
        )
    }
}


