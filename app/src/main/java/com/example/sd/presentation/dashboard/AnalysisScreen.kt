package com.example.sd.presentation.dashboard

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sd.R
import com.example.sd.presentation.components.CustomBackHandle
import kotlinx.coroutines.launch


@Composable
fun AnalysisScreen(navController: NavController, dashboardViewModel: DashboardViewModel) {
    val bidsDashboard = dashboardViewModel.stateDashboard.value.response?.data?.bids
    Log.e("Dashboard_total", "Dashboard_total->${bidsDashboard?.total?.count}")
    Log.e(
        "Dashboard_current_month",
        "Dashboard_current_month->${bidsDashboard?.current_month?.count}"
    )
    CustomBackHandle(true)
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 19.dp, vertical = 8.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            shape = RoundedCornerShape(16.dp),
            backgroundColor = Color(0xFF004FC7),
            contentColor = contentColorFor(backgroundColor),
            border = null,
            elevation = 0.dp,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(RoundedCornerShape(12.dp)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    Modifier
                        .padding(21.dp)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Всего обращений",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                    Text(
                        text = "${bidsDashboard?.total?.count}",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.icon_eskh_for_bids), // Use your logo drawable here
                    contentDescription = null,
                    modifier = Modifier
                )
            }
        }
        Spacer(modifier = Modifier.height(19.dp))

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(0.47f),
                shape = RoundedCornerShape(16.dp),
                backgroundColor = Color(0xFFE8EDFF),
                border = null,
                elevation = 0.dp,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .width(170.dp)
                        .height(110.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .padding(21.dp)
                        .fillMaxHeight(1f),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Обращений за текущий месяц",
                        style = TextStyle(
                            color = Color(0xFF004FC7),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal, letterSpacing = 0.3.sp
                        )
                    )
                    Text(
                        text = "${bidsDashboard?.current_month?.count}",
                        style = TextStyle(
                            color = Color(0xFF004FC7),
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.width(19.dp))
            Card(
                modifier = Modifier.fillMaxWidth(1f),
                shape = RoundedCornerShape(16.dp),
                backgroundColor = Color(0xFF003399),
                border = null,
                elevation = 0.dp,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .width(170.dp)
                        .height(110.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .padding(21.dp)
                        .fillMaxHeight(1f),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Обращений за прошлый месяц",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal, letterSpacing = 0.3.sp

                        )
                    )
                    Text(
                        text = "${bidsDashboard?.previous_month?.count}",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (!dashboardViewModel.stateDashboard.value.isLoading) {
            CategoryChartScreen(dashboardViewModel)
        }
    }
}


@Composable
fun CategoryChartScreen(dashboardViewModel: DashboardViewModel) {

    val bidsDashboard = dashboardViewModel.stateDashboard.value.response?.data?.bids

    val changeCount = bidsDashboard?.total?.category?.change ?: 0
    val serviceCount = bidsDashboard?.total?.category?.service ?: 0
    val incidentCount = bidsDashboard?.total?.category?.incident ?: 300
    val notSpecifiedCount = bidsDashboard?.total?.category?.not_specified ?: 0

    // Формируем categoryData из значений
    val categoryData = listOf(
        Pair("Запрос на изменение", changeCount),
        Pair("Запрос на обслуживание", serviceCount),
        Pair("Инцидент", incidentCount),
        Pair("Не указан", notSpecifiedCount)
    )
    val colors = listOf(
        Color(0xFF003399),
        Color(0xFF004FC7),
        Color(0xFF549CF0),
        Color(0xFFA5CAEC)
    )

    // Суммируем данные
    val total = categoryData.sumOf { it.second }



    Log.e("Dashboard_total", "Dashboard_total   ->     ${total}")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(shape = RoundedCornerShape(16.dp), color = Color(0xFFF2F6FF))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            backgroundColor = Color.White,
            border = null,
            elevation = 0.dp,
        ) {
            Column(
                Modifier
                    .size(width = 315.dp, height = 244.dp)
                    .padding(vertical = 25.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Категория обращений",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold, letterSpacing = 0.2.sp
                    ),
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.height(25.dp))

                AnimatedDonutChart(categoryData = categoryData)
                /*
                    Canvas(modifier = Modifier.size(130.dp)) {
                        drawArc(
                            color = Color(0xFFE8EDFF),
                            startAngle = 0f,
                            sweepAngle = 360f,
                            useCenter = false,
                            style = Stroke(width = 25.dp.toPx(), cap = StrokeCap.Butt)
                        )
                    }*/


                Spacer(modifier = Modifier.height(16.dp))

            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        // Список описаний под кругом
        categoryData.forEachIndexed { index, (label, count) ->
            CategoryDescriptionItem(label = label, count = count, color = colors[index])
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}


@Composable
fun AnimatedDonutChart(
    categoryData: List<Pair<String, Int>>,
    modifier: Modifier = Modifier,
    chartSize: Dp = 130.dp,
    gapAngle: Float = 1f // Устанавливаем угол зазора между сегментами
) {
    val colors = listOf(
        Color(0xFF003399),
        Color(0xFF004FC7),
        Color(0xFF549CF0),
        Color(0xFFA5CAEC)
    )

    // Суммируем данные
    val total = categoryData.sumOf { it.second }

    // Рассчитываем углы для каждого сегмента, учитывая зазоры
    val sweepAngles = categoryData.map { (it.second.toFloat() / total) * (360f - gapAngle * (categoryData.size - 1)) }

    // Анимируем каждый угол
    val animatedSweepAngles = sweepAngles.map { angle -> remember { Animatable(0f) } }

    // Запускаем анимацию при первом отображении
    LaunchedEffect(Unit) {
        animatedSweepAngles.forEachIndexed { index, animatable ->
            animatable.animateTo(
                targetValue = sweepAngles[index],
                animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing)
            )
        }
    }

    Canvas(modifier = modifier.size(chartSize)) {
        val strokeWidth = 20.dp.toPx() // Толщина дуги
        var startAngle = -90f // Начальный угол (начинаем сверху)

        // Рисуем каждый сегмент с анимацией и равными зазорами
        animatedSweepAngles.forEachIndexed { index, animatable ->
            drawArc(
                color = colors[index % colors.size],
                startAngle = startAngle,
                sweepAngle = animatable.value,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Butt),
                size = Size(size.width - strokeWidth, size.height - strokeWidth),
                topLeft = Offset(strokeWidth / 2, strokeWidth / 2)
            )
            startAngle += animatable.value + gapAngle // Добавляем угол сегмента и зазор только между сегментами
        }
    }
}



@Composable
fun CategoryDescriptionItem(label: String, count: Int, color: Color) {


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color.White,
        contentColor = contentColorFor(backgroundColor),
        border = null,
        elevation = 0.dp,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(20.dp)
        ) {
            Canvas(
                modifier = Modifier.size(14.dp)
            ) {
                drawCircle(color = color)
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = label,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal, letterSpacing = 0.2.sp
                ),
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "$count",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            )
        }
    }

}
