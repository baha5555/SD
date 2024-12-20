package com.example.sd.presentation.knowledgeBases

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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.sd.R
import com.example.sd.utils.Values
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.flow.Flow


@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun KnowledgeBasesScreen(
    navController: NavController,
    knowledgeBasesViewModel: KnowledgeBasesViewModel
) {

    val pagingData: Flow<PagingData<com.example.sd.domain.knowledgeBases.Data>> =
        knowledgeBasesViewModel.filterKnowledgeBases()



    val lazyPagingItems = pagingData.collectAsLazyPagingItems()
    LaunchedEffect(knowledgeBasesViewModel.selectedFilters) {
        lazyPagingItems.refresh()
    }

    Scaffold(
        topBar = {

                    Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth().fillMaxHeight(if (knowledgeBasesViewModel.selectedFilters.isNotEmpty()) 0.23f else 0.18f).padding(horizontal = 1.dp)) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                        ) {
                            IconButton(onClick = {
                               navController.popBackStack()
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.icon_left),
                                    contentDescription = ""
                                )
                            }
                            Text(
                                text = "База знаний",
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
                                value = knowledgeBasesViewModel.selectedName,
                                onValueChange = { newText ->
                                    knowledgeBasesViewModel.selectedName = newText
                                    lazyPagingItems.refresh()
                                },
                                placeholder = {
                                    Text(
                                        text = "Поиск по названию",
                                        style = TextStyle(
                                            fontSize = 14.sp,
                                            lineHeight = 21.sp,
                                            fontWeight = FontWeight(500),
                                            color = Color(0xFFA0AEC0),
                                            letterSpacing = 0.2.sp,
                                        )
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
                                ),
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    imeAction = ImeAction.Search
                                ),
                                keyboardActions = KeyboardActions(
                                    onSearch = {

                                        knowledgeBasesViewModel.showFilter()
                                    }
                                )
                            )
                            Box(
                                modifier = Modifier
                                    .height(53.dp)
                                    .width(95.dp)
                                    .padding(start = 35.dp)
                                    .border(
                                        width = 1.dp,
                                        shape = RoundedCornerShape(12.dp),
                                        color = Color(0xFFE2E8F0),
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                IconButton(onClick = { navController.navigate("KnowledgeBasesFilterScreen") }) {
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
                                .fillMaxSize().padding(horizontal =16.dp)
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
                                    knowledgeBasesViewModel.selectedFilters.distinct().forEach { filter ->
                                    FilterChip(filter) { knowledgeBasesViewModel.removeFilter(filter) }
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

                if (lazyPagingItems.itemCount > 0) {

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                    ) {
                        items(lazyPagingItems) { item ->
                            Log.d("Loading4444444", "item: $item")
                            item?.let {
                                ExpandableTicketCard(ticket = it) {
                                    knowledgeBasesViewModel.getKnowledgeBasesDetail(it.id.toString()) { success, error ->
                                        if (success) {
                                            navController.navigate("KnowledgeBasesDetailScreen")
                                        } else {
                                        }
                                    }
                                }
                            }
                        }
                        lazyPagingItems.apply {
                            when {
                                loadState.refresh is LoadState.Loading -> {
                                    item { Text("Загрузка...") }
                                }

                                loadState.append is LoadState.Loading -> {
                                    item { Text("Загрузка еще...") }
                                }

                                loadState.refresh is LoadState.Error -> {
                                    item { Text("Ошибка загрузки") }
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

                } else {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Нет результатов")
                    }
                }
            }
        }
    )
}


@Composable
fun ExpandableTicketCard(ticket: com.example.sd.domain.knowledgeBases.Data, onClick: () -> Unit) {
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
        Column(Modifier.padding(24.dp)) {
            Text(
                text = ticket.created_at.toString(),
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
                    lineHeight = 24.3.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight.W900,
                    color = Color(0xFF2C2D2E),
                    letterSpacing = 0.2.sp,
                )
            )
            Spacer(Modifier.height(24.dp))
            Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_help),
                    contentDescription = "",
                    tint = Color(0xFFA0AEC0)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = ticket.knowledge_base_type_id?.name.toString(),
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 21.sp,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFFA0AEC0),
                        letterSpacing = 0.2.sp,
                    )
                )
            }

        }
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
