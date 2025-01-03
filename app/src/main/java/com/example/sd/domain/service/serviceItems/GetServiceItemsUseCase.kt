package com.example.sd.domain.service.serviceItems

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
import javax.inject.Inject

class GetServiceItemsUseCase @Inject constructor(
    private val applicationApi: ApplicationApi,
    private val prefs: CustomPreference
) {
    operator fun invoke(): Flow<PagingData<Data>> {
        return   Pager(
                PagingConfig(pageSize = 10),
                pagingSourceFactory = { AccountsPager(applicationApi, prefs, ) }
            ).flow.catch { e ->
            emit(
                PagingData.empty<Data>() // Возвращаем пустой список данных
            )
            // Логируем ошибку
            Log.e("KnowledgeBasesUseCase", "Ошибка при загрузке данных: ${e.message}", e)
        }

    }
}


class AccountsPager(
    private val applicationApi: ApplicationApi,
    private val prefs: CustomPreference,
) : PagingSource<Int, Data>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        val pageNumber = params.key ?: 1 // Текущая страница

        return try {
            val response = applicationApi.getServiceItems(
                token = prefs.getAccessToken(),
                page = pageNumber
            )

            val meta = response.meta
            val nextPage = if (meta.current_page < meta.last_page) meta.current_page + 1 else null

            LoadResult.Page(
                data = response.data,
                prevKey = if (meta.current_page > 1) meta.current_page - 1 else null,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
