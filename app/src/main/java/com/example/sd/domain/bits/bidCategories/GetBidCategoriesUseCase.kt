package com.example.sd.domain.bits.bidCategories

import android.util.Log
import com.example.sd.data.remote.AppRepository
import com.example.sd.domain.athorization.AuthResponse
import com.example.sd.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetBidCategoriesUseCase @Inject constructor(private val repository: AppRepository) {

    operator fun invoke(): Flow<Resource<GetBidCategories>> =
        flow {
            try {
                emit(Resource.Loading<GetBidCategories>())
                // Передаем map в метод authorization
                val response: GetBidCategories = repository.getBidCategories()
                emit(Resource.Success<GetBidCategories>(response))
            } catch (e: HttpException) {
                emit(
                    Resource.Error<GetBidCategories>(
                        e.localizedMessage ?: "Произошла непредвиденная ошибка"
                    )
                )
                Log.d("GetBidCategories", "Trying to GetBidCategories with: $e")
            } catch (e: IOException) {
                Log.d("GetBidCategories", "Trying to GetBidCategories with: $e")
                emit(Resource.Error<GetBidCategories>("Не удалось связаться с сервером. Проверьте подключение к Интернету."))
            } catch (e: Exception) {
                emit(Resource.Error<GetBidCategories>("${e.message}"))
            }
        }
}
