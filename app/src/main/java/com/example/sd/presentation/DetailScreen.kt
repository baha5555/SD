package com.example.sd.presentation

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
fun DetailScreen(navController: NavController, viewModel: AuthViewModel) {

    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    val color = when (viewModel.selectedOrder.value.bid_status_id?.name) {
        "Новое" -> Color(0xFFFF7B00)
        "Ожидает обработки" -> Color(0xFF004FC7)
        "Ожидает назначения ответственного" -> Color(0xFFD09E00)
        "Отклонено по SLA" -> Color(0xFFBF00FF)
        "Закрыто" -> Color(0xFF696E82)
        "Отменено" -> Color(0xFFEA3C4A)
        "Разрешено" -> Color(0xFF16A34A)
        "В работе" -> Color(0xFF03C3B3)
        "Ожидает реакцию пользователя" -> Color(0xFF549CF0)
        else -> {
            Color.Black
        }
    }

    ModalBottomSheetLayout(
        sheetContent = {
            Column(
                modifier = Modifier.padding(
                    vertical = 16.dp,
                    horizontal = 24.dp
                )
            ) {
                ActionItem(
                    icon = R.drawable.ic_registration,
                    label = "Регистрационная информация"
                )
                ActionItem(icon = R.drawable.ic_solution, label = "Решение")
                ActionItem(
                    icon = R.drawable.ic_feedback,
                    label = "Закрытие и обратная связь"
                )
                ActionItem(icon = R.drawable.ic_files, label = "Файлы")
                ActionItem(icon = R.drawable.ic_feed, label = "Лента")
                ActionItem(icon = R.drawable.ic_time_track, label = "Тайм трек")
                ActionItem(
                    icon = R.drawable.ic_history,
                    label = "История изменений"
                )
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                Text(
                    text = "Закрыть окно",
                    color = Color(0xFF696E82),
                    fontSize = 14.sp,
                    modifier = Modifier
                        .clickable {
                            coroutineScope.launch {
                                sheetState.hide()
                            }
                        }
                        .padding(vertical = 8.dp)
                        .align(Alignment.CenterHorizontally)
                )
            } // Ваша функция с содержимым диалога
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
                            text = "${viewModel.selectedOrder.value.number}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        IconButton(onClick = { /* действие для иконки копирования */ }) {
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
                                label = "Тема",
                                value = "${viewModel.selectedOrder.value.name}"
                            )
                            InfoItem(
                                label = "Категория",
                                value = "${viewModel.selectedOrder.value.bid_category_id?.name}"
                            )
                            InfoItem(
                                label = "Уровень поддержки",
                                value = "${viewModel.selectedOrder.value.support_level_id?.name}"
                            )
                            InfoItem(
                                label = "Сервис",
                                value = "${viewModel.selectedOrder.value.service_item_id?.name}"
                            )
                            InfoItem(
                                label = "Дата регистрации",
                                value = "${viewModel.selectedOrder.value.created_at}"
                            )
                            InfoItem(
                                label = "План решения",
                                value = "${viewModel.selectedOrder.value.solution_date}"
                            )
                            InfoItem(
                                label = "Контакт",
                                value = "${viewModel.selectedOrder.value.contact_id?.name}"
                            )
                            InfoItem(
                                label = "Ответственный",
                                value = "${viewModel.selectedOrder.value.owner_id?.name}"
                            )
                            InfoItem(
                                label = "Создано пользователем",
                                value = "${viewModel.selectedOrder.value.created_user_id?.name}"
                            )
                            InfoItem(
                                label = "Состояние",
                                value = "${viewModel.selectedOrder.value.bid_status_id?.name}",
                                valueColor = color
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OutlinedButton(
                            onClick = {
                                coroutineScope.launch {
                                    if (sheetState.isVisible) {
                                        sheetState.hide()
                                    } else {
                                        sheetState.show()
                                    }
                                }
                            },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(60.dp)
                        ) {
                            Text("Действия", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = { /* действие для кнопки "Изменить" */ },
                            modifier = Modifier
                                .weight(1f)
                                .height(60.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF004FC7))
                        ) {
                            Text(
                                "Изменить",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
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


@Composable
fun ActionItem(icon: Int, label: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .clickable { /* Handle item click */ },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = label,
            tint = Color.Black,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
    }
}

