package com.example.sd.domain.filterResponse

import android.util.Log
import androidx.paging.LoadState
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.sd.data.preference.CustomPreference
import com.example.sd.data.remote.ApplicationApi
import com.example.sd.domain.bits.Data
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FilteredBidsUseCase @Inject constructor(
    private val applicationApi: ApplicationApi,
    private val prefs: CustomPreference
) {
    operator fun invoke(filters:  Map<String, String>): Flow<PagingData<com.example.sd.domain.bits.Data>> {
        return Pager(
            PagingConfig(pageSize = 10),
            pagingSourceFactory = { FilteredBidsPager(applicationApi, prefs, filters) }
        ).flow
    }
}

class FilteredBidsPager(
    private val applicationApi: ApplicationApi,
    private val prefs: CustomPreference,
    private val filters:  Map<String, String>
) : PagingSource<Int, com.example.sd.domain.bits.Data>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult.Page<Int, com.example.sd.domain.bits.Data> {
        val pageNumber = params.key ?: 1
        val accessToken = prefs.getAccessToken()

        return try {
            val response = applicationApi.getFilteredData(accessToken, filters)

            val prevKey = if (pageNumber > 1) pageNumber - 1 else null
            val nextKey = if (response.data.isNotEmpty()) pageNumber + 1 else null

            LoadResult.Page(
                data = response.data,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            Log.e("FilteredBidsPager", "Ошибка при загрузке данных: ${e.message}")
            LoadState.Error(e)  // Возвращаем ошибку в PagingSource
            throw Exception("Ошибка при загрузке данных: ${e.message}")
        }
    }

    override fun getRefreshKey(state: PagingState<Int, com.example.sd.domain.bits.Data>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}