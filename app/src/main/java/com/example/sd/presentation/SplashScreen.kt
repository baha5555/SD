package com.example.sd.presentation

import android.util.Log
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sd.R
import com.example.sd.data.preference.CustomPreference
import com.example.sd.presentation.authorization.AuthViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    var showSecondScreen by remember { mutableStateOf(false) }
    val viewModel: AuthViewModel = hiltViewModel()
    val preferences = CustomPreference(LocalContext.current)
val context = LocalContext.current

    Log.e("accsesstoken", "${viewModel.stateAuth.value.response}")
    LaunchedEffect(Unit) {
        delay(1000)
        showSecondScreen = true
    }

    LaunchedEffect(showSecondScreen) {
        if (showSecondScreen) {
            delay(1000)
            if (preferences.getAccessToken() == "" || viewModel.stateAuth.value.response == null) {
                navController.navigate("login") {
                    Toast.makeText(context,"ваш срок токена истек!",Toast.LENGTH_SHORT).show()
                    popUpTo("splash") {
                        inclusive = true
                    }
                }
            } else {
                navController.navigate("drawer") {
                    popUpTo("splash") {
                        inclusive = true
                    }
                }
            }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Page2()

        AnimatedVisibility(
            visible = !showSecondScreen,
            exit = scaleOut(
                animationSpec = tween(durationMillis = 300),
                targetScale = 0.01f
            )
        ) {
            Page1()

        }
    }
}


@Composable
fun Page1() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF004FC7)),
        contentAlignment = Alignment.Center,
    ) {

        Image(
            painter = painterResource(id = R.drawable.logo_eskh),
            contentDescription = "logo"
        )

    }
}

@Composable
fun Page2() {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.icon_ws_bg),
            contentDescription = "",
            tint = Color.Unspecified
        )
    }
}