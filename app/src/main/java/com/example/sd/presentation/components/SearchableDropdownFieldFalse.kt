package com.example.sd.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sd.R

@Composable
fun SearchableDropdownFieldFalse(
    label: String,
    placeholder: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()

            .padding(vertical = 10.dp)
    ) {

        Text(
            text = label,
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 21.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF2C2D2E),
                letterSpacing = 0.2.sp,
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .border(
                    width = 1.dp,
                    color = Color(0xFFE2E8F0),
                    shape = RoundedCornerShape(12.dp)
                )
                .background(color = Color(0xFFF4F4F5), RoundedCornerShape(12.dp))
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (label != "Департамент") {
                Icon(
                    painter = painterResource(id = R.drawable.icon_profile_2),
                    contentDescription = "", modifier = Modifier.size(24.dp)
                )
            }
            Text(
                text = placeholder,
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 21.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF2C2D2E),
                    letterSpacing = 0.2.sp,
                )
            )
        }

    }
}