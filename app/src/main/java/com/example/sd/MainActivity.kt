package com.example.sd

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.sd.presentation.MainNavigation
import com.example.sd.presentation.knowledgeBases.KnowledgeBasesFilterScreen
import com.example.sd.presentation.report.ReportsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(){
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         enableEdgeToEdge()
        setContent {
            SetStatusBarColorCompat(Color.Transparent)
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
            Log.d(
                "TAGFONT",
                "fontScale=" + configuration.fontScale
            ) //Custom Log class, you can use Log.w
            Log.d("TAGFONT", "font too big. scale down...") //Custom Log class, you can use Log.w
            configuration.fontScale = 1f
            val metrics = resources.displayMetrics
            val wm = getSystemService(WINDOW_SERVICE) as WindowManager
            wm.defaultDisplay.getMetrics(metrics)
            metrics.scaledDensity = configuration.fontScale * metrics.density
            baseContext.resources.updateConfiguration(configuration, metrics)
        }
    }

    @Composable
    fun SetStatusBarColorCompat(color: Color) {
        val context = LocalContext.current
        val window = (context as? androidx.activity.ComponentActivity)?.window
        window?.statusBarColor = color.toArgb()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decorView = window?.decorView
            val flags = decorView?.systemUiVisibility ?: 0
            window?.decorView?.systemUiVisibility = if (color.luminance() > 0.5) {
                flags or android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                flags and android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            }
        }
    }
}

