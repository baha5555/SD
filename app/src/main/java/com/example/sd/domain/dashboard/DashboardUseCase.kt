package com.example.sd.domain.dashboard

import android.util.Log
import com.example.sd.data.remote.AppRepository
import com.example.sd.domain.athorization.AuthResponse
import com.example.sd.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DashboardUseCase @Inject constructor(private val repository: AppRepository) {

    operator fun invoke(): Flow<Resource<Dashboard>> =
        flow {
            try {
                emit(Resource.Loading<Dashboard>())
                // Передаем map в метод authorization
                val response: Dashboard = repository.getDashboard()
                emit(Resource.Success<Dashboard>(response))
            } catch (e: HttpException) {
                emit(
                    Resource.Error<Dashboard>(
                        e.localizedMessage ?: "Произошла непредвиденная ошибка"
                    )
                )
            } catch (e: IOException) {
                emit(Resource.Error<Dashboard>("Не удалось связаться с сервером. Проверьте подключение к Интернету."))
            } catch (e: Exception) {
                emit(Resource.Error<Dashboard>("${e.message}"))
            }
        }
}
