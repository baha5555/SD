package com.example.sd.domain.castas


import android.util.Log
import com.example.sd.utils.Resource
import com.example.sd.data.remote.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CastasUseCase @Inject constructor(private val repository: AppRepository) {

    operator fun invoke(): Flow<Resource<Castas>> =
        flow {
            try {
                emit(Resource.Loading<Castas>())
                // Передаем map в метод authorization
                val response: Castas = repository.getCastas()
                emit(Resource.Success<Castas>(response))
                Log.d("AuthUseCas2", "2222Trying to authorize with: $response")

            } catch (e: HttpException) {
                emit(
                    Resource.Error<Castas>(
                        e.localizedMessage ?: "Произошла непредвиденная ошибка"
                    )
                )
                Log.d("AuthUseCas2", "222222222222Trying to authorize with: $e")
            } catch (e: IOException) {
                Log.d("AuthUseCas3", "Trying to authorize with: $e")
                emit(Resource.Error<Castas>("Не удалось связаться с сервером. Проверьте подключение к Интернету."))
            } catch (e: Exception) {
                emit(Resource.Error<Castas>("${e.message}"))
            }
        }
}
