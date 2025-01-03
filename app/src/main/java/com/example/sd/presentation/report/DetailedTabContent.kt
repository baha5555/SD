package com.example.sd.presentation.report

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sd.R
import com.example.sd.domain.report.detailReport.DetailReport
import com.example.sd.presentation.components.AnimatedDonutChartReport
import com.example.sd.presentation.components.DateRangePickerInput

import com.example.sd.presentation.components.SearchableDropdownField
import com.example.sd.presentation.components.StatisticsCard
import com.example.sd.presentation.components.StatisticsItem
import java.util.Calendar

@SuppressLint("SuspiciousIndentation")
@Composable
fun DetailedTabContent(reportViewModel: ReportViewModel) {



    var selectedDateRange by remember { mutableStateOf(Pair<Long?, Long?>(null, null)) }
    val expandedState = remember { mutableStateOf<String?>(null) }


    LaunchedEffect(reportViewModel.stateGetReportDetail.value.response) {
        reportViewModel.stateGetReportDetail.value.response
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        DateRangePickerInput(
            selectedDateRange = selectedDateRange,
            onDateRangeSelected = { dateRange ->
                selectedDateRange = dateRange
            },
            onApiCall = { first,second ->

                reportViewModel.getReportDetail(first,second,"1")
            }
        )
        Spacer(modifier = Modifier.height(26.dp))
        Divider()

        if(reportViewModel.stateGetReportDetail.value.response != null){
        Spacer(modifier = Modifier.height(16.dp))

        SearchableDropdownField(
            label = "Филиал",
            placeholder = "Выберите филиал",
            options = reportViewModel.itemNameAccount(),
            expandedState = expandedState,
            currentId = "Account",
            initialValue =reportViewModel.selectedAccount,
            onOptionSelected = { selectedName ->
                reportViewModel.selectedAccount = selectedName
                val selectedDepCode = reportViewModel.stateGetReportDetail.value.response?.data
                    ?.find { it.name == selectedName }?.code

                    reportViewModel.updateSelectedAccountData(selectedDepCode.toString())
                Log.d("selectedDepCode", selectedDepCode.toString())

            }
        )
        Spacer(modifier = Modifier.height(16.dp))


            reportViewModel.selectedAccountData.value?.let {
                StatisticsScreen(reportViewModel.selectedAccountData)
            }
        }

    }
}






@Composable
fun StatisticsScreen(reportViewModel: State<DetailReport.Data?>) {
    val data  = reportViewModel.value?.data
    val items = listOf(
        StatisticsItem(
            icon = painterResource(id = R.drawable.icon_all_bids),
            label = "Всего обращений",
            value = data?.total!!,
            backgroundColor = Color(0xFF003399)
        ),
        StatisticsItem(
            icon = painterResource(id = R.drawable.icon_close),
            label = "Закрыто",
            value = data.canceled,
            backgroundColor = Color(0xFFFF4C4C)
        ),
        StatisticsItem(
            icon = painterResource(id = R.drawable.colendar_in_time),
            label = "Закрыто в срок",
            value = data.completedOnTime,
            backgroundColor = Color(0xFF28C76F)
        ),
        StatisticsItem(
            icon = painterResource(id = R.drawable.icon_refresh),
            label = "Просрочено",
            value = data.notCompletedOnTime,
            backgroundColor = Color(0xFFFFC107)
        ),
        StatisticsItem(
            icon = painterResource(id = R.drawable.icon_door),
            label = "Не закрыто",
            value = data.inWork,
            backgroundColor = Color(0xFF6C757D)
        ),
        StatisticsItem(
            icon = painterResource(id = R.drawable.icon_division),
            label = "Не закрыто в процентах",
            value = data.prcFailure.toInt(),
            backgroundColor = Color(0xFF212529)
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFF2F6FF))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .padding(16.dp),
            verticalArrangement =Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Статистика",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
            )


            val chartData = items.map { it.label to it.value }
            AnimatedDonutChartReport(
                categoryData = chartData,
                itemColors = items.map { it.backgroundColor })
        }
        Spacer(modifier = Modifier.height(16.dp))

        items.forEach { item ->
            StatisticsCard(item = item)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}