package com.example.sd.presentation.components

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sd.R
import java.util.Calendar

@Composable
fun DateRangePickerInput(
    selectedDateRange: Pair<Long?, Long?>,
    onDateRangeSelected: (Pair<Long?, Long?>) -> Unit,
    onApiCall: (String,String) -> Unit
) {

    var showDateRangePicker by remember { mutableStateOf(false) }

    // Функция для преобразования миллисекунд в строку с форматом dd.MM.yyyy
    fun formatDate(dateMillis: Long?): String {
        return dateMillis?.let {
            val date = java.util.Date(it)
            val formatter = java.text.SimpleDateFormat("dd.MM.yyyy", java.util.Locale.getDefault())
            formatter.format(date)
        } ?: "Выберите диапазон дат"
    }

    val text = if(formatDate(selectedDateRange.first) =="Выберите диапазон дат" &&formatDate(selectedDateRange.second) =="Выберите диапазон дат" ) "Выберите период" else "${formatDate(selectedDateRange.first)} - ${formatDate(selectedDateRange.second)}"

    OutlinedButton(
        onClick = { showDateRangePicker = true },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .height(56.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(width = 1.dp, color = Color(0xFFE2E8F0)),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_calendar),
                contentDescription = null,
                tint = Color(0xFF96A3BE),
                modifier = Modifier.size(25.dp)
            )
            Spacer(modifier = Modifier.width(15.dp))
            Text(
                text = text,
                fontSize = 16.sp,
                color = if (selectedDateRange.first == null || selectedDateRange.second == null) Color(0xFFA0AEC0) else Color.Black,
                textAlign = TextAlign.Start,
                modifier = Modifier.weight(1f)
            )
        }
    }


    if (showDateRangePicker) {
        DateRangePickerModal(
            onDateRangeSelected = { dateRange ->
                // Обработка выбранного диапазона дат
                onDateRangeSelected(dateRange)
                showDateRangePicker = false

                // Форматируем даты перед отправкой на сервер
                val formattedFirstDate = formatDate(dateRange.first)
                val formattedSecondDate = formatDate(dateRange.second)

                // Передаем отформатированные даты в API
                val formattedDateRange = "$formattedFirstDate&$formattedSecondDate"
                onApiCall(formattedFirstDate,formattedSecondDate)
            },
            onDismiss = {
                // Закрыть модалку
                showDateRangePicker = false
            }
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePickerModal(
    onDateRangeSelected: (Pair<Long?, Long?>) -> Unit,
    onDismiss: () -> Unit
) {
    val dateRangePickerState = rememberDateRangePickerState()

    fun getFormattedDate(timeInMillis: Long): String{
        val calender = Calendar.getInstance()
        calender.timeInMillis = timeInMillis
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        return dateFormat.format(calender.timeInMillis)
    }
    DatePickerDialog(
        colors = DatePickerDefaults.colors(
            containerColor = Color.White,


            ),
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    // Передаем выбранный диапазон дат
                    onDateRangeSelected(
                        Pair(
                            dateRangePickerState.selectedStartDateMillis,
                            dateRangePickerState.selectedEndDateMillis
                        )
                    )
                    onDismiss()
                }
            ) {
                Text("OK", color = Color(0xFF004FC7))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel",color = Color(0xFF004FC7))
            }
        }
    ) {

        DateRangePicker(dateRangePickerState,
            modifier = Modifier.background(Color.White).fillMaxWidth()
                .height(500.dp)
                .padding(8.dp),
            title = {
                Text(text = "Выберите период", modifier = Modifier
                    .padding(16.dp))
            },
            headline = {
                Row(modifier = Modifier.fillMaxWidth()
                    .padding(16.dp),horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.weight(1f)) {
                        (if(dateRangePickerState.selectedStartDateMillis!=null) dateRangePickerState.selectedStartDateMillis?.let { getFormattedDate(it) } else "Start Date")?.let { Text(text = it) }
                    }
                    Box(Modifier.weight(1f)) {
                        (if(dateRangePickerState.selectedEndDateMillis!=null) dateRangePickerState.selectedEndDateMillis?.let { getFormattedDate(it) } else "End Date")?.let { Text(text = it) }
                    }


                }
            },
            showModeToggle = true,
            colors = DatePickerDefaults.colors(

                containerColor = Color.White,
                titleContentColor = Color.Black,
                headlineContentColor = Color(0xFF004FC7),
                weekdayContentColor = Color.Black,
                subheadContentColor = Color.Black,
                yearContentColor = Color.Green,
                currentYearContentColor = Color.Red,
                selectedYearContainerColor = Color.Red,
                disabledDayContentColor = Color.Gray,
                todayDateBorderColor = Color.Blue,
                dayInSelectionRangeContainerColor = Color(0xFFEFF4FB),
                dayInSelectionRangeContentColor = Color.Black,
                selectedDayContainerColor = Color(0xFF004FC7)
            )
        )
    }
}