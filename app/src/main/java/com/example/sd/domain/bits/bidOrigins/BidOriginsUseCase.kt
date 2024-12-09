package com.example.sd.domain.bits.bidOrigins

import android.util.Log
import com.example.sd.data.remote.AppRepository
import com.example.sd.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class BidOriginsUseCase @Inject constructor(private val repository: AppRepository) {

    operator fun invoke(): Flow<Resource<BidOrigins>> =
        flow {
            try {
                emit(Resource.Loading<BidOrigins>())
                // Передаем map в метод authorization
                val response: BidOrigins = repository.getBidOrigins()
                emit(Resource.Success<BidOrigins>(response))
            } catch (e: HttpException) {
                emit(
                    Resource.Error<BidOrigins>(
                        e.localizedMessage ?: "Произошла непредвиденная ошибка"
                    )
                )
                Log.d("AuthUseCas2", "Trying to authorize with: $e")
            } catch (e: IOException) {
                Log.d("AuthUseCas3", "Trying to authorize with: $e")
                emit(Resource.Error<BidOrigins>("Не удалось связаться с сервером. Проверьте подключение к Интернету."))
            } catch (e: Exception) {
                emit(Resource.Error<BidOrigins>("${e.message}"))
            }
        }
}
