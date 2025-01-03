package com.example.sd.presentation.report

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.sd.R

@Composable
fun KPIByLinesTabContent() {
    Column {
        FieldWithLabel(
            label = "Период",
            placeholder = "23.01.2024 - 27.01.2024",
            leadingIcon = painterResource(id = R.drawable.icon_calendar)
        )
        Spacer(modifier = Modifier.height(16.dp))
        FieldWithLabel(
            label = "Линия",
            placeholder = "Линия 1",
            trailingIcon = Icons.Default.ArrowDropDown
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("Мухаммедов Наимон", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(16.dp))
        // Mock KPI Chart
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(Color(0xFFF5F5F5), RoundedCornerShape(8.dp))
        ) {
            Text(
                text = "КПИ Диаграмма",
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.body1.copy(color = Color.Gray)
            )
        }
    }
}