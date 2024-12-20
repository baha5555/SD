package com.example.sd.utils

import android.util.Log
import java.net.ProtocolException

suspend fun <T> safeApiCall(
    block: suspend () -> T
): Result<T> {
    return try {
        Result.success(block())
    } catch (e: ProtocolException) {
        Log.e("SafeApiCall", "Ошибка протокола: ${e.message}", e)
        Result.failure(e)
    } catch (e: Exception) {
        Log.e("SafeApiCall", "Произошла ошибка: ${e.message}", e)
        Result.failure(e)
    }
}