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
import javax.inject.Inject

class KnowledgeBasesUseCase @Inject constructor(private val applicationApi: ApplicationApi,
                                      private val prefs: CustomPreference
) {
    operator fun invoke(filters: Map<String, String>): Flow<PagingData<Data>> {
        return try {
            Pager(
                PagingConfig(pageSize = 10),
                pagingSourceFactory = { KnowledgeBasesPager(applicationApi, prefs,filters) }
            ).flow
        } catch (e: Exception) {
            // Обработка ошибки, например, запись в лог или отображение сообщения пользователю
            throw Exception("Ошибка при загрузке данных: ${e.message}")
        }
    }
}


class KnowledgeBasesPager(
    private val applicationApi: ApplicationApi,
    private val prefs: CustomPreference,
    private val filters:  Map<String, String>
) : PagingSource<Int, Data>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult.Page<Int, Data> {
        val pageNumber = params.key ?: 1
        val response = applicationApi.getKnowledgeBases(prefs.getAccessToken(),filters)
        Log.d("KnowledgeBasesPagerPager","$response")
        val prevKey = if (pageNumber > 0) pageNumber - 1 else null
        val nextKey = if (response.data.size!=null) pageNumber + 1 else null
        return LoadResult.Page(
            data = response.data,
            prevKey = prevKey,
            nextKey = nextKey
        )
    }
    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}