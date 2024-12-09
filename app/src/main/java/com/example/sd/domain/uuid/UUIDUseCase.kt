package com.example.sd.domain.uuid

import android.util.Log
import com.example.sd.data.remote.AppRepository
import com.example.sd.data.remote.ApplicationApi
import com.example.sd.domain.bits.bidOrigins.BidOrigins
import com.example.sd.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File
import java.io.IOException
import javax.inject.Inject
class UUIDUseCase @Inject constructor(private val repository: AppRepository) {

    operator fun invoke(): Flow<Resource<String>> =
        flow {
            try {
                emit(Resource.Loading<String>())
                // Передаем map в метод authorization
                val response: String = repository.getUUID()
                emit(Resource.Success<String>(response))
            } catch (e: HttpException) {
                emit(
                    Resource.Error<String>(
                        e.localizedMessage ?: "Произошла непредвиденная ошибка"
                    )
                )
                Log.d("AuthUseCas2", "Trying to authorize with: $e")
            } catch (e: IOException) {
                Log.d("AuthUseCas3", "Trying to authorize with: $e")
                emit(Resource.Error<String>("Не удалось связаться с сервером. Проверьте подключение к Интернету."))
            } catch (e: Exception) {
                emit(Resource.Error<String>("${e.message}"))
            }
        }
}