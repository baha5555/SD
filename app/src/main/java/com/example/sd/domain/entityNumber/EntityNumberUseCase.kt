package com.example.sd.domain.entityNumber

import android.util.Log
import com.example.sd.data.remote.AppRepository
import com.example.sd.domain.athorization.AuthResponse
import com.example.sd.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

 class EntityNumberUseCase @Inject constructor(private val repository: AppRepository) {

    operator fun invoke(entityType: String): Flow<Resource<EntityNumber>> =
        flow {
            try {
                emit(Resource.Loading<EntityNumber>())
                // Передаем map в метод authorization
                val response: EntityNumber = repository.generateEntityNumber(entityType)
                emit(Resource.Success<EntityNumber>(response))
            } catch (e: HttpException) {
                emit(
                    Resource.Error<EntityNumber>(
                        e.localizedMessage ?: "Произошла непредвиденная ошибка"
                    )
                )
                Log.d("AuthUseCas2", "Trying to authorize with: $e")
            } catch (e: IOException) {
                Log.d("AuthUseCas3", "Trying to authorize with: $e")
                emit(Resource.Error<EntityNumber>("Не удалось связаться с сервером. Проверьте подключение к Интернету."))
            } catch (e: Exception) {
                emit(Resource.Error<EntityNumber>("${e.message}"))
            }
        }
}
