package com.example.sd.domain.knowledgeBases


import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.sd.data.preference.CustomPreference
import com.example.sd.data.remote.ApplicationApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class KnowledgeBasesUseCase @Inject constructor(
    private val applicationApi: ApplicationApi,
    private val prefs: CustomPreference
) {
    operator fun invoke(filters: Map<String, String>): Flow<PagingData<Data>> {
        return Pager(
            PagingConfig(pageSize = 10),
            pagingSourceFactory = { KnowledgeBasesPager(applicationApi, prefs, filters) }
        ).flow.catch { e ->
            emit(
                PagingData.empty<Data>()
            )
            // Логируем ошибку
            Log.e("KnowledgeBasesUseCase", "Ошибка при загрузке данных: ${e.message}", e)
        }
    }
}


class KnowledgeBasesPager(
    private val applicationApi: ApplicationApi,
    private val prefs: CustomPreference,
    private val filters: Map<String, String>
) : PagingSource<Int, Data>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        val pageNumber = params.key ?: 1
        return try {
            // Выполняем запрос через API
            val response = applicationApi.getKnowledgeBases(prefs.getAccessToken(), filters)


            Log.d("KnowledgeBasesPager", "Ответ: $response")

            val prevKey = if (pageNumber > 1) pageNumber - 1 else null
            val nextKey = if (response.data.isNullOrEmpty()) null else pageNumber + 1
            val data = response.data ?: emptyList()

            // Возвращаем результат
            LoadResult.Page(
                data = data,
                prevKey = prevKey,
                nextKey = nextKey
            )

        } catch (e: HttpException) {
            // Обработка ошибок HTTP
            Log.e("KnowledgeBasesPager", "Ошибка HTTP: ${e.message()}", e)
            LoadResult.Error(e)

        } catch (e: IOException) {
            // Обработка ошибок сети
            Log.e("KnowledgeBasesPager", "Ошибка сети: ${e.message}", e)
            LoadResult.Error(e)

        } catch (e: Exception) {
            // Обработка любых других ошибок
            Log.e("KnowledgeBasesPager", "Непредвиденная ошибка: ${e.message}", e)
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}