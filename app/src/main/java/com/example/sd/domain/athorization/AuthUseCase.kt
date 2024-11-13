package com.example.sd.domain.athorization


import android.util.Log
import com.example.sd.utils.Resource
import com.example.sd.data.remote.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AuthUseCase @Inject constructor(private val repository: AppRepository) {

    operator fun invoke(name: String, password: String): Flow<Resource<AuthResponse>> =
        flow {
            try {
                emit(Resource.Loading<AuthResponse>())
                // Передаем map в метод authorization
                val response: AuthResponse = repository.authorization(name, password)
                emit(Resource.Success<AuthResponse>(response))
            } catch (e: HttpException) {
                emit(
                    Resource.Error<AuthResponse>(
                        e.localizedMessage ?: "Произошла непредвиденная ошибка"
                    )
                )
                Log.d("AuthUseCas2", "Trying to authorize with: $e")
            } catch (e: IOException) {
                Log.d("AuthUseCas3", "Trying to authorize with: $e")
                emit(Resource.Error<AuthResponse>("Не удалось связаться с сервером. Проверьте подключение к Интернету."))
            } catch (e: Exception) {
                emit(Resource.Error<AuthResponse>("${e.message}"))
            }
        }
}
