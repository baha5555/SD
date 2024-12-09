package com.example.sd.presentation.contact

import android.app.Application
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.sd.domain.bits.Data
import com.example.sd.domain.contacts.GetContactsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    application: Application,
    private val contactsUseCase: GetContactsUseCase
) : AndroidViewModel(application) {

    var selectedFIO by mutableStateOf("")

    private val _selectedContact = mutableStateOf(com.example.sd.domain.contacts.Data())
    val selectedContact: State<com.example.sd.domain.contacts.Data> get() = _selectedContact

    fun getFilterMap(): Map<String, String> {
        // Например, можно создать фильтр на основе значений, которые вы ввели в UI
        val filterMap = mutableMapOf<String, String>()
        if (selectedFIO.isNotEmpty()) {
            filterMap["filter[name]"] = selectedFIO
        }
        // Добавить другие фильтры по аналогии
        return filterMap
    }

    fun searchContact(): Flow<PagingData<com.example.sd.domain.contacts.Data>> {
        return snapshotFlow { getFilterMap() } // Следим за изменением `selectedFIO`
            .debounce(300) // Добавляем задержку, чтобы уменьшить количество запросов
            .flatMapLatest { query ->
                val filters = getFilterMap()
                contactsUseCase(filters).cachedIn(viewModelScope)
            }
            .catch { e ->
                Log.e("ContactViewModel", "Ошибка загрузки: ${e.message}")
            }
    }


    fun updateSelectedContact(order: com.example.sd.domain.contacts.Data) {
        Log.e("it22222", "it22222->\n ${order}")
        _selectedContact.value = order
        Log.e("it22222222", "it22222222->\n ${_selectedContact.value}")
        Log.e("it22222222", "it2222222222222222222->\n ${_selectedContact.value}")
    }
}