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
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.map
import com.example.sd.domain.bits.Data
import com.example.sd.domain.bits.bidOrigins.BidOrigins
import com.example.sd.domain.contacts.GetContactsUseCase
import com.example.sd.domain.contacts.contactType.ContactType
import com.example.sd.domain.contacts.contactType.ContactTypeUseCase
import com.example.sd.presentation.states.ContactTypeResponseState
import com.example.sd.presentation.states.GetBidsOriginsResponseState
import com.example.sd.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    application: Application,
    private val contactsUseCase: GetContactsUseCase,
    private val getContactTypeUseCase: ContactTypeUseCase
) : AndroidViewModel(application) {

    private val _stateGetContactType = mutableStateOf(ContactTypeResponseState())
    val stateGetContactType: State<ContactTypeResponseState> = _stateGetContactType

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
    fun searchContact1(name: String): Flow<PagingData<com.example.sd.domain.contacts.Data>> {
        return flowOf(name)
            .debounce(300)
            .distinctUntilChanged()
            .flatMapLatest { query ->
                contactsUseCase(mapOf("filter[name]" to query)).cachedIn(viewModelScope)
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


    fun getContactType() {
        getContactTypeUseCase.invoke().onEach { result: Resource<ContactType> ->
            when (result) {
                is Resource.Success -> {
                    try {
                        val response: ContactType? = result.data
                        _stateGetContactType.value =
                            ContactTypeResponseState(response = response)
                        Log.e(
                            "GetBidsOriginsResponseStateResponse",
                            "GetBidsOriginsResponseStateResponse->\n ${_stateGetContactType.value}"
                        )
                    } catch (e: Exception) {
                        Log.d("Exception", "${e.message} Exception")
                    }
                }

                is Resource.Error -> {
                    Log.e(
                        "GetBidsOriginsResponseStateResponse",
                        "GetBidsOriginsResponseStateResponseError->\n ${result.message}"
                    )
                    _stateGetContactType.value =
                        ContactTypeResponseState(error = "${result.message}")
                }

                is Resource.Loading -> {
                    _stateGetContactType.value = ContactTypeResponseState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}