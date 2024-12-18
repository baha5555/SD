package com.example.sd.presentation.knowledgeBases

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberImagePainter
import com.example.sd.R
import com.example.sd.presentation.authorization.AuthViewModel
import com.example.sd.presentation.components.SearchableDropdownField
import com.example.sd.presentation.contact.ContactViewModel
import com.example.sd.presentation.createBids.Status
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.util.Calendar


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun KnowledgeBasesDetailScreen(navController: NavController, viewModel: KnowledgeBasesViewModel) {
    val data = viewModel.stateGetKnowledgeBasesDetail.value.response?.data
    val context = LocalContext.current
    val webView = remember { WebView(context) }

    // Обработка нажатия на кнопку "Back"
    val clearWebView = rememberUpdatedState {
        // Очистка WebView
        webView.apply {
            stopLoading()  // Останавливаем текущую загрузку
            clearHistory()  // Очищаем историю
            clearCache(true)  // Очищаем кэш
            destroy()  // Уничтожаем WebView
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.height(60.dp),
                backgroundColor = Color.White,
                contentColor = Color.Black,
                elevation = 0.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp)
                        .padding(top = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {

                        clearWebView.value()

                        navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                    Text(
                        text = "Статья базы знаний",
                        style = TextStyle(
                            fontSize = 18.sp,
                            lineHeight = 18.sp,
                            fontFamily = FontFamily(Font(R.font.inter)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF2C2D2E),
                            textAlign = TextAlign.Center,
                        )
                    )
                    Box(modifier = Modifier.size(25.dp)) {

                    }
                }
            }
        },
        content = {
            if (data != null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.88f)
                    ) {
                        item {
                            Text(
                                text = data.name ?: "Нет названия",
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    lineHeight = 24.3.sp,
                                    fontFamily = FontFamily(Font(R.font.inter)),
                                    fontWeight = FontWeight(700),
                                    color = Color(0xFF2C2D2E),
                                    letterSpacing = 0.2.sp,
                                )
                            )

                            HtmlContentScreen(data.notes ?: "Нет описания")



                            Divider(modifier = Modifier.padding(vertical = 16.dp))

                            InfoItem(
                                label = "Тип",
                                value = data.knowledge_base_type_id?.name ?: "Не указан"
                            )
                            InfoItem(
                                label = "Автор",
                                value = data.created_by?.name ?: "Не указан"
                            )
                            InfoItem(
                                label = "Дата создания",
                                value = data.created_at ?: "Не указана"
                            )
                            InfoItem(
                                label = "Последнее изменение",
                                value = data.updated_at ?: "Не указано"
                            )
                            InfoItem(
                                label = "Теги",
                                value = data.tags?.joinToString(", ") ?: "Нет тегов"
                            )
                        }
                    }
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        },
    )
}

@Composable
fun InfoItem(label: String, value: String, valueColor: Color = Color.Black) {
    Column(modifier = Modifier.padding(vertical = 15.dp), horizontalAlignment = Alignment.Start) {
        androidx.compose.material3.Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(5.dp))
        androidx.compose.material3.Text(
            text = value,
            fontSize = 16.sp,
            color = valueColor,
            fontWeight = FontWeight.Medium
        )
    }
}


@Composable
fun DisplayHtmlContent(htmlContent: String) {

    val styledText = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(htmlContent, Html.FROM_HTML_MODE_LEGACY)
    } else {
        @Suppress("DEPRECATION")
        Html.fromHtml(htmlContent)
    }

    Column {
        Text(
            text = styledText.toString(),
            style = TextStyle(
                fontFamily = FontFamily.Serif,
                fontSize = 16.sp
            )
        )
    }
}
@Composable
fun HtmlWebViewScreen(htmlContent: String) {
    val context = LocalContext.current
    val webView = remember { WebView(context) }

    // Обновляем WebView при изменении HTML-контента
    LaunchedEffect(htmlContent) {
        webView.loadDataWithBaseURL(
            null,
            htmlContent,
            "text/html",
            "UTF-8",
            null
        )
    }

    // Отключаем WebView при выходе с экрана
    DisposableEffect(context) {
        onDispose {
            webView.clearHistory()
            webView.clearCache(true)
        }
    }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            webView.apply {
                settings.javaScriptEnabled = true
                webViewClient = WebViewClient()
            }
        }
    )
}


@Composable
fun HtmlContentScreen(htmlContent: String) {
    // Извлекаем изображения из HTML контента
    val imageUrls = extractImageUrls(htmlContent)

    // Основной контейнер для отображения текста и изображений
    Column(modifier = Modifier.fillMaxSize()) {
        // Отображаем HTML текст
        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = { context ->
                TextView(context).apply {
                    text = Html.fromHtml(htmlContent, Html.FROM_HTML_MODE_COMPACT)
                }
            }
        )


        imageUrls.forEach { imageUrl ->
            Image(
                painter = rememberImagePainter(imageUrl),
                contentDescription = "Image",
                modifier = Modifier.size(200.dp)
            )
        }
    }
}

// Функция для извлечения URL изображений из HTML
fun extractImageUrls(htmlContent: String): List<String> {
    val imageUrls = mutableListOf<String>()
    val regex = "<img[^>]*src=[\"']([^\"']*)[\"'][^>]*>".toRegex() // Регулярное выражение для поиска изображений
    val matches = regex.findAll(htmlContent)

    matches.forEach {
        imageUrls.add(it.groupValues[1]) // Добавляем найденные URL изображений
    }

    return imageUrls
}
