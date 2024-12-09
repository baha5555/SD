package com.example.sd.domain.accounts

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.sd.data.preference.CustomPreference
import com.example.sd.data.remote.ApplicationApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAccountsUseCase  @Inject constructor(
    private val applicationApi: ApplicationApi,
    private val prefs: CustomPreference
) {
    operator fun invoke(filters:  Map<String, String>): Flow<PagingData<Data>> {
        return   Pager(
                PagingConfig(pageSize = 10),
                pagingSourceFactory = { AccountsPager(applicationApi, prefs, filters) }
            ).flow

    }
}

class AccountsPager(
    private val applicationApi: ApplicationApi,
    private val prefs: CustomPreference,
    private val filters:  Map<String, String>
) : PagingSource<Int, Data>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult.Page<Int, Data> {
        val pageNumber = params.key ?: 1
        val response = applicationApi.getAccounts(prefs.getAccessToken(),filters)

        val prevKey = if (pageNumber > 1) pageNumber - 1 else null
        val nextKey = if (response.data.isNotEmpty()) pageNumber + 1 else null

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