package com.example.sd.utils

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf


object Constants {
    val TAG = "TAG"
    var FCM_TOKEN: String? = ""
    const val LOCAL_BASE_URL = "http://10.250.1.96/"
    const val TEST_URL = "http://192.168.206.54/api/v1/"
    const val BASE_URL = "https://api-sd.eskhata.tj/api/v1/"
    const val URL = TEST_URL
    var IDENTIFY_TO_SCREEN = ""
    const val ACCESS_TOKEN = "ACCESS_TOKEN"
    const val APP_PREFERENCES = "APP_PREFERENCES"
}
object Values {
    var USERID: MutableState<String> = mutableStateOf("")
}