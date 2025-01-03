package com.example.sd.domain.contacts

import android.util.Log
import androidx.paging.LoadState
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.sd.data.preference.CustomPreference
import com.example.sd.data.remote.AppRepository
import com.example.sd.data.remote.ApplicationApi
import com.example.sd.domain.bits.bidPriorities.GetBidPriorities
import com.example.sd.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetContactsUseCase @Inject constructor(
    private val applicationApi: ApplicationApi,
    private val prefs: CustomPreference
) {
    operator fun invoke(filters: Map<String, String>): Flow<PagingData<Data>> {
        return Pager(
            PagingConfig(pageSize = 1),
            pagingSourceFactory = { ContactsPager(applicationApi, prefs, filters) }
        ).flow.catch { e ->
            emit(
                PagingData.empty<Data>() // Возвращаем пустой список данных
            )
            // Логируем ошибку
            Log.e("KnowledgeBasesUseCase", "Ошибка при загрузке данных: ${e.message}", e)
        }

    }
}

class ContactsPager(
    private val applicationApi: ApplicationApi,
    private val prefs: CustomPreference,
    private val filters: Map<String, String>
) : PagingSource<Int, Data>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        val pageNumber = params.key ?: 1
        return try {
            val response = applicationApi.getSearchContact(prefs.getAccessToken(), filters,pageNumber )

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
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}