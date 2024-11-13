package com.example.sd.domain.changePassword

import android.util.Log
import com.example.sd.data.remote.AppRepository
import com.example.sd.domain.athorization.AuthResponse
import com.example.sd.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(private val repository: AppRepository) {

    operator fun invoke(userId: String,password: String,password_confirmation: String): Flow<Resource<ChangePassword>> =
        flow {
            try {
                emit(Resource.Loading<ChangePassword>())
                // Передаем map в метод authorization
                val response: ChangePassword = repository.changePassword(userId,password,password_confirmation)
                emit(Resource.Success<ChangePassword>(response))
            } catch (e: HttpException) {
                emit(
                    Resource.Error<ChangePassword>(
                        e.localizedMessage ?: "Произошла непредвиденная ошибка"
                    )
                )
                Log.d("ChangePassword33", "Trying to authorize22 with: $e")
            } catch (e: IOException) {
                Log.d("ChangePassword3333", "Trying to authori222ze with: $e")
                emit(Resource.Error<ChangePassword>("Не удалось связаться с сервером. Проверьте подключение к Интернету."))
            } catch (e: Exception) {
                emit(Resource.Error<ChangePassword>("${e.message}"))
            }
        }
}
