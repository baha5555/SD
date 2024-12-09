package com.example.sd.presentation.accounts

import android.app.Application
import android.util.Log
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.sd.domain.accounts.Data
import com.example.sd.domain.accounts.GetAccountsUseCase
import com.example.sd.domain.service.serviceItems.GetServiceItemsUseCase
import com.example.sd.domain.service.servicePacts.GetServicePactsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class AccountsViewModel @Inject constructor(
    application: Application,
    private val accountsUseCase: GetAccountsUseCase,
    private val servicePactsUseCase: GetServicePactsUseCase,
    private val serviceItemsUseCase: GetServiceItemsUseCase,
) : AndroidViewModel(application) {




    fun getFilterMap(): Map<String, String> {
        val filterMap = mutableMapOf<String, String>()
        return filterMap
    }

    fun searchAccounts(): Flow<PagingData<Data>> {
        return snapshotFlow { getFilterMap() }
            .debounce(300)
            .flatMapLatest { query ->
                val filters = getFilterMap()
                accountsUseCase(filters).cachedIn(viewModelScope)
            }
            .catch { e ->
                Log.e("ContactViewModel", "Ошибка загрузки: ${e.message}")
            }
    }
    fun searchServiceItems(): Flow<PagingData<com.example.sd.domain.service.serviceItems.Data>> {
        return snapshotFlow { getFilterMap() }
            .debounce(300)
            .flatMapLatest { query ->
                val filters = getFilterMap()
                serviceItemsUseCase(filters).cachedIn(viewModelScope)
            }
            .catch { e ->
                Log.e("ContactViewModel", "Ошибка загрузки: ${e.message}")
            }
    }
    fun searchServicePacts(): Flow<PagingData<com.example.sd.domain.service.servicePacts.Data>> {
        return snapshotFlow { getFilterMap() }
            .debounce(300)
            .flatMapLatest { query ->
                val filters = getFilterMap()
                servicePactsUseCase(filters).cachedIn(viewModelScope)
            }
            .catch { e ->
                Log.e("ContactViewModel", "Ошибка загрузки: ${e.message}")
            }
    }

}