package com.example.sd.presentation.components

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.example.sd.presentation.createBids.CreateBidsViewModel

@Composable
fun StepProgressBar(viewModel: CreateBidsViewModel) {
    val currentStep by viewModel.currentStep.collectAsState()
    val totalSteps = 6

    val progressAnimDuration = 1500
    val progressAnimation by animateFloatAsState(
        targetValue = currentStep.toFloat() / totalSteps,
        animationSpec = tween(durationMillis = progressAnimDuration, easing = FastOutLinearInEasing)
    )

    LinearProgressIndicator(
        progress = progressAnimation,
        color = Color(0xFF194BFB),
        strokeCap= StrokeCap.Round,// Цвет активного прогресса
        modifier = Modifier
            .fillMaxWidth()
            .height(4.dp)
            .clip(RoundedCornerShape(16.dp)), // Скругленные углы
        backgroundColor = Color(0xFFE2E8F0) // Цвет фона
    )
}

