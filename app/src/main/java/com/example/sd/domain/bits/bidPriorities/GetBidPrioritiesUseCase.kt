package com.example.sd.domain.bits.bidPriorities

import android.util.Log
import com.example.sd.data.remote.AppRepository
import com.example.sd.domain.bits.bidStatus.GetBidsStatus
import com.example.sd.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetBidPrioritiesUseCase @Inject constructor(private val repository: AppRepository) {

    operator fun invoke(): Flow<Resource<GetBidPriorities>> =
        flow {
            try {
                emit(Resource.Loading<GetBidPriorities>())
                // Передаем map в метод authorization
                val response: GetBidPriorities = repository.getBidPriorities()
                emit(Resource.Success<GetBidPriorities>(response))
            } catch (e: HttpException) {
                emit(
                    Resource.Error<GetBidPriorities>(
                        e.localizedMessage ?: "Произошла непредвиденная ошибка"
                    )
                )
                Log.d("GetBidPriorities", "Trying to GetBidPriorities with: $e")
            } catch (e: IOException) {
                Log.d("GetBidPriorities", "Trying to GetBidPriorities with: $e")
                emit(Resource.Error<GetBidPriorities>("Не удалось связаться с сервером. Проверьте подключение к Интернету."))
            } catch (e: Exception) {
                emit(Resource.Error<GetBidPriorities>("${e.message}"))
            }
        }
}
