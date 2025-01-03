package com.example.sd.presentation.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AnimatedDonutChartReport(
    categoryData: List<Pair<String, Int>>,
    itemColors: List<Color>,
    modifier: Modifier = Modifier,
    chartSize: Dp = 130.dp,
    gapAngle: Float = 1f
) {
    val total = categoryData.sumOf { it.second }

    if (total == 0) return

    val sweepAngles = categoryData.map { (it.second.toFloat() / total) * (360f - gapAngle * (categoryData.size - 1)) }

    val animatedSweepAngles = sweepAngles.map { angle -> remember { Animatable(0f) } }

    LaunchedEffect(Unit) {
        animatedSweepAngles.forEachIndexed { index, animatable ->
            animatable.animateTo(
                targetValue = sweepAngles[index],
                animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing)
            )

        }
    }

    Canvas(modifier = modifier.size(chartSize)) {
        val strokeWidth = 20.dp.toPx()
        var startAngle = -90f

        animatedSweepAngles.forEachIndexed { index, animatable ->
            drawArc(
                color = itemColors[index % itemColors.size],
                startAngle = startAngle,
                sweepAngle = animatable.value,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Butt),
                size = Size(size.width - strokeWidth, size.height - strokeWidth),
                topLeft = Offset(strokeWidth / 2, strokeWidth / 2)
            )
            startAngle += animatable.value + gapAngle  }
    }
}

@Composable
fun StatisticsCard(item: StatisticsItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(item.backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = item.icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = item.label,
            style = TextStyle(fontSize = 16.sp, color = Color.Black),
            modifier = Modifier.weight(1f)
        )
        Text(
            text = item.value.toString(),
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        )
    }
}

data class StatisticsItem(
    val icon: Painter,
    val label: String,
    val value: Int,
    val backgroundColor: Color
)
