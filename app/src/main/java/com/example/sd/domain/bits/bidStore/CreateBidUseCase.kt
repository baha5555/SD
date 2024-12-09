package com.example.sd.domain.bits.bidStore

import android.util.Log
import com.example.sd.data.remote.AppRepository
import com.example.sd.domain.bits.bidOrigins.BidOrigins
import com.example.sd.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CreateBidUseCase @Inject constructor(private val repository: AppRepository) {

    operator fun invoke(
        id: String?,
        name: String,
        number: String,
        symptoms: String?,
        ownerId: String?,
        responseDate: String?,
        solutionDate: String?,
        bidStatusId: String,
        bidPriorityId: String,
        bidOriginId: String,
        accountId: String,
        contactId: String,
        solution: String?,
        satisfactionLevelId: String?,
        bidCategoryId: String,
        responseOverdue: String?,
        solutionOverdue: String?,
        satisfactionComment: String?,
        reasonDelay: String?,
        solutionRemains: String?,
        servicePactId: String,
        serviceItemId: String,
        supportLevelId: String,
        parentId: String?,
        holderId: String?,
        problemId: String?,
        departmentId: String?,
        fileCollection: String,
        feedCollection: String
    ): Flow<Resource<BidStore>> =
        flow {
            try {
                emit(Resource.Loading<BidStore>())
                // Передаем map в метод authorization
                val response: BidStore = repository.createBid(
                    id,
                    name,
                    number,
                    symptoms,
                    ownerId,
                    responseDate,
                    solutionDate,
                    bidStatusId,
                    bidPriorityId,
                    bidOriginId,
                    accountId,
                    contactId,
                    solution,
                    satisfactionLevelId,
                    bidCategoryId,
                    responseOverdue,
                    solutionOverdue,
                    satisfactionComment,
                    reasonDelay,
                    solutionRemains,
                    servicePactId,
                    serviceItemId,
                    supportLevelId,
                    parentId,
                    holderId,
                    problemId,
                    departmentId,
                    fileCollection,
                    feedCollection
                )
                emit(Resource.Success<BidStore>(response))
                Log.d("createBid222", "2222Trying to authorize with: $response")

            } catch (e: HttpException) {
                emit(
                    Resource.Error<BidStore>(
                        e.localizedMessage ?: "Произошла непредвиденная ошибка"
                    )
                )
                Log.d("createBid222", "222222Trying to createBid with: $e")
            } catch (e: IOException) {
                Log.d("createBid", "Trying to createBid with: $e")
                emit(Resource.Error<BidStore>("Не удалось связаться с сервером. Проверьте подключение к Интернету."))
            } catch (e: Exception) {
                emit(Resource.Error<BidStore>("${e.message}"))
            }
        }
}
