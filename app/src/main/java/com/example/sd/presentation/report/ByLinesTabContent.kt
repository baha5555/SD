package com.example.sd.presentation.report

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sd.R
import com.example.sd.domain.report.detailReport.DetailReport
import com.example.sd.domain.report.linesReport.ReportLines
import com.example.sd.presentation.components.AnimatedDonutChartReport
import com.example.sd.presentation.components.DateRangePickerInput
import com.example.sd.presentation.components.SearchableDropdownField
import com.example.sd.presentation.components.StatisticsCard
import com.example.sd.presentation.components.StatisticsItem
import com.example.sd.presentation.knowledgeBases.formatTo

@Composable
fun ByLinesTabContent(reportViewModel: ReportViewModel) {


    var selectedDateRange by remember { mutableStateOf(Pair<Long?, Long?>(null, null)) }
    val expandedState = remember { mutableStateOf<String?>(null) }


    LaunchedEffect(reportViewModel.stateGetReportByLine.value.response) {
        reportViewModel.stateGetReportByLine.value.response
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
            onApiCall = { first, second ->
                val startDate = first.formatTo("yyyy-MM-dd")
                val endDate = second.formatTo("yyyy-MM-dd")
                reportViewModel.getReportByLine(startDate, endDate)
            }
        )
        Spacer(modifier = Modifier.height(26.dp))
        Divider()

        if (reportViewModel.stateGetReportByLine.value.isLoading) {
            CircularProgressIndicator()
        } else if (reportViewModel.stateGetReportByLine.value.response != null) {
            Spacer(modifier = Modifier.height(16.dp))
            if ( reportViewModel.stateGetReportByLine.value.response!!.size <= 0) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Нет данных по этим переодом")

                }
            }
            SearchableDropdownField(
                label = "Линия",
                placeholder = "Выберите линию",
                options = reportViewModel.itemNameLines(),
                expandedState = expandedState,
                currentId = "Account",
                initialValue = reportViewModel.selectedLine,
                onOptionSelected = { selectedName ->
                    reportViewModel.selectedLine = selectedName
                    val line = reportViewModel.stateGetReportByLine.value.response
                        ?.find { it.level == selectedName }?.level

                    reportViewModel.updateSelectedLineData(line.toString())
                    Log.d("selectedLine", line.toString())

                }
            )
            Spacer(modifier = Modifier.height(16.dp))


            reportViewModel.selectedLineData.value?.let {
                StatisticsScreenLine(reportViewModel.selectedLineData)
            }


        }
    }
}

@Composable
fun StatisticsScreenLine(reportViewModel: State<ReportLines.ReportLinesItem?>) {
    val data = reportViewModel.value
    val items = listOf(
        StatisticsItem(
            icon = painterResource(id = R.drawable.icon_all_bids),
            label = "Всего обращений",
            value = data?.totalBids!!,
            backgroundColor = Color(0xFF003399)
        ),
        StatisticsItem(
            icon = painterResource(id = R.drawable.icon_close),
            label = "Закрыто",
            value = data.allDone,
            backgroundColor = Color(0xFFFF4C4C)
        ),
        StatisticsItem(
            icon = painterResource(id = R.drawable.colendar_in_time),
            label = "Закрыто в срок",
            value = data.doneInTime,
            backgroundColor = Color(0xFF28C76F)
        ),
        StatisticsItem(
            icon = painterResource(id = R.drawable.icon_refresh),
            label = "Просрочено",
            value = data.doneAfterTime,
            backgroundColor = Color(0xFFFFC107)
        ),
        StatisticsItem(
            icon = painterResource(id = R.drawable.icon_door),
            label = "Не закрыто",
            value = data.undone,
            backgroundColor = Color(0xFF6C757D)
        ),
        StatisticsItem(
            icon = painterResource(id = R.drawable.icon_division),
            label = "Не закрыто в процентах",
            value = data.undonePercent.toInt(),
            backgroundColor = Color(0xFF212529)
        ),
        StatisticsItem(
            icon = painterResource(id = R.drawable.icon_time),
            label = "Задержка",
            value = data.delay,
            backgroundColor = Color(0xFFAFBCCB)
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
            verticalArrangement = Arrangement.Center,
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