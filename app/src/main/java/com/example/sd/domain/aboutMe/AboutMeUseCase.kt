package com.example.sd.domain.aboutMe

import android.util.Log
import com.example.sd.data.remote.AppRepository
import com.example.sd.domain.athorization.AuthResponse
import com.example.sd.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AboutMeUseCase @Inject constructor(private val repository: AppRepository) {

    operator fun invoke(): Flow<Resource<AboutMe>> =
        flow {
            try {
                emit(Resource.Loading<AboutMe>())
                // Передаем map в метод authorization
                val response: AboutMe = repository.aboutMe()
                emit(Resource.Success<AboutMe>(response))
            } catch (e: HttpException) {
                emit(
                    Resource.Error<AboutMe>(
                        e.localizedMessage ?: "Произошла непредвиденная ошибка"
                    )
                )
                Log.d("AuthUseCas2", "Trying to authorize with: $e")
            } catch (e: IOException) {
                Log.d("AuthUseCas3", "Trying to authorize with: $e")
                emit(Resource.Error<AboutMe>("Не удалось связаться с сервером. Проверьте подключение к Интернету."))
            } catch (e: Exception) {
                emit(Resource.Error<AboutMe>("${e.message}"))
            }
        }
}
