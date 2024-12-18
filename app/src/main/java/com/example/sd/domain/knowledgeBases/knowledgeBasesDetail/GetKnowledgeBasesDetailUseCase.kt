package com.example.sd.domain.knowledgeBases.knowledgeBasesDetail

import android.util.Log
import com.example.sd.data.remote.AppRepository
import com.example.sd.domain.changePassword.ChangePassword
import com.example.sd.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetKnowledgeBasesDetailUseCase  @Inject constructor(private val repository: AppRepository) {

    operator fun invoke(knowledgeBaseId: String): Flow<Resource<KnowledgeBasesDetail>> =
        flow {
            try {
                emit(Resource.Loading<KnowledgeBasesDetail>())
                // Передаем map в метод authorization
                val response: KnowledgeBasesDetail = repository.getKnowledgeBasesDetail(knowledgeBaseId)
                emit(Resource.Success<KnowledgeBasesDetail>(response))
            } catch (e: HttpException) {
                emit(
                    Resource.Error<KnowledgeBasesDetail>(
                        e.localizedMessage ?: "Произошла непредвиденная ошибка"
                    )
                )
                Log.d("ChangePassword33", "Trying to authorize22 with: $e")
            } catch (e: IOException) {
                Log.d("ChangePassword3333", "Trying to authori222ze with: $e")
                emit(Resource.Error<KnowledgeBasesDetail>("Не удалось связаться с сервером. Проверьте подключение к Интернету."))
            } catch (e: Exception) {
                emit(Resource.Error<KnowledgeBasesDetail>("${e.message}"))
            }
        }
}
