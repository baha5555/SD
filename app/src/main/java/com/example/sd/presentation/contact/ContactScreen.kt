package com.example.sd.presentation.contact

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.sd.R
import com.example.sd.domain.bits.Data
import com.example.sd.presentation.authorization.AuthViewModel
import com.example.sd.presentation.components.CustomBackHandle
import com.example.sd.presentation.filter.FilterViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.flow.Flow


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ContactScreen(
    navController: NavController,
    viewModel: ContactViewModel,
) {
    // Создаем переменные для данных
    val pagingData: Flow<PagingData<com.example.sd.domain.contacts.Data>> = viewModel.searchContactFilter()


    val lazyPagingItems = pagingData.collectAsLazyPagingItems()
    LaunchedEffect(viewModel.selectedFilters) {
        lazyPagingItems.refresh()
    }


    Scaffold(
        topBar = {

                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth().fillMaxHeight(if (viewModel.selectedFilters.isNotEmpty()) 0.23f else 0.18f).padding(horizontal = 1.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .background(Color.White)
                        ) {
                            IconButton(modifier = Modifier, onClick = {
                                navController.popBackStack()
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.icon_left),
                                    contentDescription = "",
                                )
                            }
                            Text(
                                text = "Контакты",
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    lineHeight = 18.sp,
                                    fontFamily = FontFamily(Font(R.font.inter)),
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFF2C2D2E),
                                    textAlign = TextAlign.Center,
                                )
                            )

                            Box(modifier = Modifier.size(50.dp))
                        }

                        Row(
                            modifier = Modifier
                                .padding(top = 20.dp)
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            OutlinedTextField(
                                value = "",
                                onValueChange = {},
                                placeholder = {
                                    Text(
                                        text = "Поиск",
                                        fontSize = 18.sp,
                                        color = Color(0xFFAFBCCB)
                                    )
                                },
                                textStyle = TextStyle.Default.copy(fontSize = 18.sp),
                                modifier = Modifier
                                    .fillMaxWidth(0.75f)
                                    .height(53.dp)
                                    .background(
                                        MaterialTheme.colors.background,
                                        shape = RoundedCornerShape(12.dp)
                                    ),
                                shape = RoundedCornerShape(12.dp),
                                singleLine = true,
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.icon_search),
                                        contentDescription = "Поиск",
                                        tint = Color.Black
                                    )
                                },
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = Color(0xFFE2E8F0),
                                    unfocusedBorderColor = Color(0xFFE2E8F0),
                                    errorBorderColor = Color(0xFFE2E8F0),
                                    cursorColor = Color(0xFF004FC7),
                                    textColor = Color.Black
                                )
                            )
                            Box(
                                modifier = Modifier
                                    .height(50.dp)
                                    .width(95.dp)
                                    .padding(start = 35.dp)
                                    .border(
                                        width = 1.dp,
                                        shape = RoundedCornerShape(12.dp),
                                        color = Color(0xFFE2E8F0),
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                IconButton(onClick = { navController.navigate("ContactFilterScreen") }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.icon_filter),
                                        contentDescription = "Фильтр",
                                        tint = Color.Black,
                                    )
                                }
                            }

                        }
                        Column(
                            modifier = Modifier
                                .fillMaxSize().padding(horizontal = 16.dp)
                                .fillMaxHeight(), verticalArrangement = Arrangement.Center
                        ) {

                            Row(
                                modifier = Modifier
                                    .horizontalScroll(rememberScrollState())
                                    .fillMaxWidth()
                                    .height(50.dp)
                                    .padding(vertical = 6.dp),
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                viewModel.selectedFilters.distinct().forEach { filter ->
                                    FilterChip(filter) { viewModel.removeFilter(filter) }
                                }
                            }
                        }
                    }



        },
        content = {


            SwipeRefresh(
                modifier = Modifier.fillMaxSize(),
                state = rememberSwipeRefreshState(isRefreshing = lazyPagingItems.loadState.refresh is LoadState.Loading),
                onRefresh = {
                    lazyPagingItems.retry()
                    lazyPagingItems.refresh()
                }
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    items(lazyPagingItems) { item ->
                        Log.d("Loading4444444", "item: $item")
                        item?.let {
                            ExpandableTicketCard(ticket = it) {
                                viewModel.updateSelectedContact(it)
                                navController.navigate("DetailScreenContact")
                            }
                        }
                    }
                    if (lazyPagingItems.loadState.append is LoadState.Loading) {
                        item {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ExpandableTicketCard(ticket: com.example.sd.domain.contacts.Data, onClick: () -> Unit) {
    /*val color = when (ticket.bid_status_id?.name) {
        "Новое" -> Color(0xFFFF7B00)
        "Ожидает обработки" -> Color(0xFF004FC7)
        "Ожидает назначения ответственного" -> Color(0xFFD09E00)
        "Отклонено по SLA" -> Color(0xFFBF00FF)
        "Закрыто" -> Color(0xFF696E82)
        "Отменено" -> Color(0xFFEA3C4A)
        "Разрешено" -> Color(0xFF16A34A)
        "В работе" -> Color(0xFF03C3B3)
        "Ожидает реакцию пользователя" -> Color(0xFF549CF0)
        else -> Color.Black
    }*/

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp)
            .clickable { onClick() },
        backgroundColor = Color.White,
        shape = RoundedCornerShape(12.dp),
        elevation = 0.dp,
        border = BorderStroke(width = 1.dp, color = Color(0xFFE2E8F0))
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = ticket.contact_type_id?.name.toString(),
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 18.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF96A3BE),
                    letterSpacing = 0.2.sp,
                )
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = ticket.name.toString(),
                style = TextStyle(
                    fontSize = 18.sp,
                    lineHeight = 27.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF2C2D2E),
                    letterSpacing = 0.2.sp,
                )
            )
            Spacer(Modifier.height(12.dp))


           FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
               if(ticket.mobile_phone!=null){Element(label = ticket.mobile_phone)}
               if(ticket.casta_id!=null){Element2(label = ticket.casta_id.name.toString())}
               if(ticket.branch_id!=null){Element3(label = ticket.branch_id.name.toString())}
               if(ticket.department_id!=null){Element4(label = ticket.department_id.name.toString())}




            }

        }
    }
}

@Composable
fun Element(label: String) {
    Box(
        modifier = Modifier
            .background(
                color = Color(0xFFFF004FC7),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(8.dp)
    ) {
        Text(
            text = label,maxLines = 1,overflow = TextOverflow.Ellipsis, style = TextStyle(
                fontSize = 12.sp,
                lineHeight = 18.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFFFFFFFF),
                letterSpacing = 0.4.sp,
            )
        )

    }
}

@Composable
fun Element2(label: String) {
    Box(
        modifier = Modifier
            .background(
                color = Color(0xFFE8EDFF),
                shape = RoundedCornerShape(size = 12.dp)
            )
            .padding(8.dp)
    ) {
        Text(
            text = label,maxLines = 1,overflow = TextOverflow.Ellipsis, style = TextStyle(
                fontSize = 12.sp,

                lineHeight = 18.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF004FC7),
                letterSpacing = 0.4.sp,
            )
        )

    }
}

@Composable
fun Element3(label: String) {
    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = Color(0xFFD9D9D9),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(8.dp)
    ) {
        Text(
            text = label,maxLines = 1,overflow = TextOverflow.Ellipsis, style = TextStyle(
                fontSize = 12.sp,
                lineHeight = 18.sp,

                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF2C2D2E),
                letterSpacing = 0.4.sp,
            )
        )

    }
}

@Composable
fun Element4(label: String) {
    Box(
        modifier = Modifier
            .background(
                color = Color(0xFF5D6A83),
                shape = RoundedCornerShape(size = 12.dp)
            )
            .padding(8.dp)
    ) {
        Text(
            text = label,maxLines = 1,overflow = TextOverflow.Ellipsis, style = TextStyle(
                fontSize = 12.sp,
                lineHeight = 18.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFFFFFFFF),
                letterSpacing = 0.4.sp,
            )
        )

    }
}


@Composable
fun FilterChip(filter: String, onRemove: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxHeight()
            .border(
                width = 1.dp,
                color = Color(0xFFE2E8F0),
                shape = RoundedCornerShape(size = 8.dp)
            )
            .background(color = Color.White, shape = RoundedCornerShape(size = 8.dp))
            .padding(10.dp)

    ) {
        androidx.compose.material.Text(filter, color = Color.Black, fontSize = 14.sp)

        Icon(
            painter = painterResource(id = R.drawable.icon_remove),
            contentDescription = "",
            tint = Color.Unspecified,
            modifier = Modifier
                .clickable { onRemove() })
    }
}
