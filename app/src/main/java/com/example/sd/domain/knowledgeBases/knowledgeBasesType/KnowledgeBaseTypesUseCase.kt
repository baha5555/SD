package com.example.sd.domain.knowledgeBases.knowledgeBasesType

import android.util.Log
import com.example.sd.data.remote.AppRepository
import com.example.sd.domain.contacts.contactType.ContactType
import com.example.sd.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class KnowledgeBaseTypesUseCase  @Inject constructor(private val repository: AppRepository) {

    operator fun invoke(): Flow<Resource<KnowledgeBasesType>> =
        flow {
            try {
                emit(Resource.Loading<KnowledgeBasesType>())
                // Передаем map в метод authorization
                val response: KnowledgeBasesType = repository.getKnowledgeBaseTypes()
                emit(Resource.Success<KnowledgeBasesType>(response))
                Log.d("AuthUseCas2", "2222Trying to authorize with: $response")

            } catch (e: HttpException) {
                emit(
                    Resource.Error<KnowledgeBasesType>(
                        e.localizedMessage ?: "Произошла непредвиденная ошибка"
                    )
                )
                Log.d("AuthUseCas2", "222222222222Trying to authorize with: $e")
            } catch (e: IOException) {
                Log.d("AuthUseCas3", "Trying to authorize with: $e")
                emit(Resource.Error<KnowledgeBasesType>("Не удалось связаться с сервером. Проверьте подключение к Интернету."))
            } catch (e: Exception) {
                emit(Resource.Error<KnowledgeBasesType>("${e.message}"))
            }
        }
}
