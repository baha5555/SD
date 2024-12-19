package com.example.sd.domain.bits


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

class BidsUseCase @Inject constructor(private val applicationApi: ApplicationApi,
                                      private val prefs: CustomPreference
) {
    operator fun invoke(pageSize: Int): Flow<PagingData<Data>> {
        return Pager(
            PagingConfig(pageSize = pageSize),
            pagingSourceFactory = { BidsPager(applicationApi, prefs) }
        ).flow.catch { e ->
            emit(
                PagingData.empty<Data>() // Возвращаем пустой список данных
            )
            // Логируем ошибку
            Log.e("KnowledgeBasesUseCase", "Ошибка при загрузке данных: ${e.message}", e)


        }
    }
}


class BidsPager(
    private val applicationApi: ApplicationApi,
    private val prefs: CustomPreference
) : PagingSource<Int, Data>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult.Page<Int, Data> {
        val pageNumber = params.key ?: 1
        val response = applicationApi.getBids(prefs.getAccessToken(),pageNumber)
        Log.d("BidsPager","$response")
        val prevKey = if (pageNumber > 0) pageNumber - 1 else null
        val nextKey = if (response.data.isNullOrEmpty()) null else pageNumber + 1
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