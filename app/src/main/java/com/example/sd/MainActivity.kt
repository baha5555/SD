package com.example.sd

import MainScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sd.presentation.authorization.ChangePasswordScreen
import com.example.sd.presentation.authorization.LoadingScreen
import com.example.sd.presentation.authorization.SuccessChangePassword
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         enableEdgeToEdge()
        setContent {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(top = 35.dp)) {
                MainScreen()
            }
        }
    }
}

