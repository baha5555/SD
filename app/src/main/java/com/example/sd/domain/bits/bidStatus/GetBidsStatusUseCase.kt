package com.example.sd.domain.bits.bidStatus

import android.util.Log
import com.example.sd.data.remote.AppRepository
import com.example.sd.domain.bits.bidCategories.GetBidCategories
import com.example.sd.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetBidsStatusUseCase @Inject constructor(private val repository: AppRepository) {

    operator fun invoke(): Flow<Resource<GetBidsStatus>> =
        flow {
            try {
                emit(Resource.Loading<GetBidsStatus>())
                // Передаем map в метод authorization
                val response: GetBidsStatus = repository.getBidStatus()
                emit(Resource.Success<GetBidsStatus>(response))
            } catch (e: HttpException) {
                emit(
                    Resource.Error<GetBidsStatus>(
                        e.localizedMessage ?: "Произошла непредвиденная ошибка"
                    )
                )
                Log.d("GetBidsStatus", "Trying to GetBidsStatus with: $e")
            } catch (e: IOException) {
                Log.d("GetBidsStatus", "Trying to GetBidsStatus with: $e")
                emit(Resource.Error<GetBidsStatus>("Не удалось связаться с сервером. Проверьте подключение к Интернету."))
            } catch (e: Exception) {
                emit(Resource.Error<GetBidsStatus>("${e.message}"))
            }
        }
}
