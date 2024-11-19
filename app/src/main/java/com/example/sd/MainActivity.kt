package com.example.sd

import MainScreen
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sd.presentation.CreateBidsScreen
import com.example.sd.presentation.MainNavigation
import com.example.sd.presentation.authorization.ChangePasswordScreen
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
                MainNavigation()
            }
        }
        adjustFontScale(getResources().getConfiguration())
    }


    fun adjustFontScale(configuration: Configuration) {
        if (configuration.fontScale > 1.10) {
            Log.e(
                "TAGFONT",
                "fontScale=" + configuration.fontScale
            ) //Custom Log class, you can use Log.w
            Log.e("TAGFONT", "font too big. scale down...") //Custom Log class, you can use Log.w
            configuration.fontScale = 1f
            val metrics = resources.displayMetrics
            val wm = getSystemService(WINDOW_SERVICE) as WindowManager
            wm.defaultDisplay.getMetrics(metrics)
            metrics.scaledDensity = configuration.fontScale * metrics.density
            baseContext.resources.updateConfiguration(configuration, metrics)
        }
    }
}

