package com.example.sd.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.captionBarPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.sd.R
import com.example.sd.domain.service.serviceItems.Data
import com.example.sd.presentation.contact.ContactViewModel
import com.example.sd.presentation.createBids.Status

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchableDropdownFieldWithPaginationContact(
    label: String,
    placeholder: String,
    viewModel: ContactViewModel,
    expandedState: MutableState<String?>,
    currentId: String,
    initialValue: String,
    onOptionSelected: (Status) -> Unit
) {

    val lazyPagingItems = viewModel.searchContact().collectAsLazyPagingItems()


    var searchQuery by remember { mutableStateOf(initialValue) }

    LaunchedEffect(initialValue) {
        searchQuery = initialValue
    }

    val isExpanded = expandedState.value == currentId

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        Text(
            text = label,
            style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 21.sp,
                fontWeight = FontWeight(600),
                color = Color(0xFF5D6A83),
                letterSpacing = 0.2.sp,
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )


        OutlinedTextField(

            value = searchQuery,
            onValueChange = { query ->
                expandedState.value = currentId
                searchQuery = query
                viewModel.selectedFIO = query
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
                Icon(
                    modifier = Modifier.clickable {
                        expandedState.value =
                            if (expandedState.value == currentId) null else currentId
                    },
                    painter = if (isExpanded) painterResource(id = R.drawable.icon_up) else painterResource(
                        id = R.drawable.icon_down
                    ),
                    contentDescription = null,
                    tint = Color(0xFFA0AEC0)
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    // Раскрываем список
                    lazyPagingItems.refresh()
                }
            ),
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

        Spacer(modifier = Modifier.height(10.dp))

        if (isExpanded) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth().imePadding()
                    .imeNestedScroll()
                    .heightIn(max = 200.dp)
                    .background(Color.White)
                    .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(8.dp))
            ) {
                items(lazyPagingItems) { item ->

                    item?.let {
                        Text(
                            text = it.name.toString(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    searchQuery = it.name.toString()
                                    onOptionSelected(Status(it.id.toString(), it.name.toString()))
                                    expandedState.value = null
                                }
                                .padding(8.dp),
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
                /* if(lazyPagingItems.itemCount<1){
                    item{ Text(
                         text = "Нет результатов",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        style = MaterialTheme.typography.body1)
                 }}*/



                lazyPagingItems.apply {
                    when {
                        loadState.append is LoadState.Loading || loadState.refresh is LoadState.Loading -> {
                            item {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(68.dp),
                                    horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically
                                ) {

                                    CircularProgressIndicator(
                                        modifier = Modifier
                                            .size(30.dp), color = Color(0xFF004FC7)
                                    )
                                }
                            }
                        }

                        loadState.append is LoadState.NotLoading -> {
                            item {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(68.dp),
                                    horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically
                                ) {
                                Text(
                                    text = "Ничего не найдено",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        lineHeight = 24.sp,
                                        fontFamily = FontFamily(Font(R.font.inter)),
                                        fontWeight = FontWeight(500),
                                        color = Color(0xFF96A3BE),
                                        textAlign = TextAlign.Center,
                                        letterSpacing = 0.03.sp,
                                    ), modifier = Modifier.fillMaxWidth()
                                )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}