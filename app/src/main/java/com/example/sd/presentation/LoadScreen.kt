package com.example.sd.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.sd.R
import com.example.sd.presentation.authorization.AuthViewModel
import com.example.sd.presentation.contact.ContactViewModel
import com.example.sd.presentation.createBids.CreateBidsViewModel
import com.example.sd.presentation.dashboard.DashboardViewModel
import com.example.sd.presentation.knowledgeBases.KnowledgeBasesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext




@SuppressLint("SuspiciousIndentation")
@Composable
fun LoadingScreen(
    dashboardViewModel: DashboardViewModel,
    viewModel: AuthViewModel,
    createBidsViewModel: CreateBidsViewModel,
    navController: NavController,
    contactViewModel: ContactViewModel,
    knowledgeBasesViewModel: KnowledgeBasesViewModel
) {



    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.load))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = true) {
        //bids
        dashboardViewModel.getDashboard()
        viewModel.getAboutMe()
        createBidsViewModel.getBidCategories()
        createBidsViewModel.getBidStatus()
        createBidsViewModel.getBidPriorities()
        createBidsViewModel.getSupportLevels()
        createBidsViewModel.getBidsOrigins()
        //contact
        contactViewModel.getContactType()
        //castas
        contactViewModel.getCastas()
        //KnowledgeBases
        knowledgeBasesViewModel.getKnowledgeBasesType()


        // Ждем 2 секунды, даже если данные уже загрузились
        delay(2000)
        isLoading = false
    }

    if (isLoading) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            LottieAnimation(
                composition = composition,
                modifier = Modifier
                    .size(100.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop,
                progress = { progress }
            )

            Text(
                text = "Загрузка данных",
                style = TextStyle(
                    // стили текста
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Пожалуйста подождите",
                style = TextStyle(
                    // стили текста
                )
            )
        }
    } else {
        LaunchedEffect(key1 = true) {
            navController.navigate("drawer")
        }
    }
}