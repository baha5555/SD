package com.example.sd.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun SearchableDropdownField(
    label: String,
    placeholder: String,
    options: List<String>,
    expandedState: MutableState<String?>,
    currentId: String,
    initialValue: String,
    onOptionSelected: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf(initialValue) }

    LaunchedEffect(initialValue) {
        searchQuery = initialValue
    }
    val filteredOptions = if (searchQuery.isEmpty()) options else options.filter {
        it.contains(searchQuery, ignoreCase = true)
    }
    val isExpanded = expandedState.value == currentId && filteredOptions.isNotEmpty()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        // Метка
        Text(
            text = label,
            style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 21.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(600),
                color = Color(0xFF5D6A83),
                letterSpacing = 0.2.sp,
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Поле ввода
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { query ->
                searchQuery = query
                onOptionSelected(searchQuery)
                expandedState.value = currentId
            },
            placeholder = {
                Text(
                    text = placeholder,
                    fontSize = 16.sp,
                    color = Color(0xFFAFBCCB)
                )
            },
            singleLine = true,
            trailingIcon = {
                Icon(modifier = Modifier.clickable {
                    expandedState.value = null
                },
                    painter =if(isExpanded) painterResource(id = R.drawable.icon_up) else painterResource(id = R.drawable.icon_down) ,
                    contentDescription = null,
                    tint = Color(0xFFA0AEC0)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expandedState.value = currentId },
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFE2E8F0),
                unfocusedBorderColor = Color(0xFFE2E8F0),
                textColor = Color.Black
            )
        )

        // Динамический список
        if (isExpanded ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .background(Color.White)
                    .border(1.dp, Color(0xFFD9DCE1), RoundedCornerShape(8.dp))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 200.dp)
                        .verticalScroll(rememberScrollState())
                        .padding(8.dp)
                ) {
                    filteredOptions.forEach { option ->
                        Text(
                            text = option,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    searchQuery = option
                                    onOptionSelected(option)
                                    expandedState.value = null
                                }
                                .padding(8.dp),
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
            }
        }
    }
}