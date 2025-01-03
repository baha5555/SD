package com.example.sd.presentation.contact

import android.app.Application
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.sd.domain.castas.Castas
import com.example.sd.domain.castas.CastasUseCase
import com.example.sd.domain.contacts.GetContactsUseCase
import com.example.sd.domain.contacts.contactType.ContactType
import com.example.sd.domain.contacts.contactType.ContactTypeUseCase
import com.example.sd.presentation.knowledgeBases.formatTo
import com.example.sd.presentation.states.CastasResponseState
import com.example.sd.presentation.states.ContactTypeResponseState
import com.example.sd.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    application: Application,
    private val contactsUseCase: GetContactsUseCase,
    private val getContactTypeUseCase: ContactTypeUseCase,
    private val getCastasUseCase: CastasUseCase
) : AndroidViewModel(application) {

    private val _stateGetContactType = mutableStateOf(ContactTypeResponseState())
    val stateGetContactType: State<ContactTypeResponseState> = _stateGetContactType

    private val _stateCastas = mutableStateOf(CastasResponseState())
    val stateCastas: State<CastasResponseState> = _stateCastas

    var selectedFIO by mutableStateOf("")

    private val _selectedFilters = mutableStateListOf<String>()
    val selectedFilters: List<String> get() = _selectedFilters

    var creationDateStart by mutableStateOf("ДД.ММ.ГГ")
    var creationDateEnd by mutableStateOf("ДД.ММ.ГГ")

    var modificationDateStart by mutableStateOf("ДД.ММ.ГГ")
    var modificationDateEnd by mutableStateOf("ДД.ММ.ГГ")

    var birthDateStart by mutableStateOf("ДД.ММ.ГГ")
    var birthDateEnd by mutableStateOf("ДД.ММ.ГГ")

    var selectedType by mutableStateOf("")
    var selectedGender by mutableStateOf("")  // Пол
    var selectedContragent by mutableStateOf("")  // Контрагент
    var selectedBranch by mutableStateOf("")  // Филиал
    var selectedDepartment by mutableStateOf("")  // Отдел
    var selectedPosition by mutableStateOf("")  // Должность
    var mobilePhone by mutableStateOf("")  // Мобильный телефон
    var homePhone by mutableStateOf("")  // Домашний телефон
    var email by mutableStateOf("")  // Email
    var externalSystemId by mutableStateOf("")  // ID внешней системы
    var createdBy by mutableStateOf("")  // Создал
    var modifiedBy by mutableStateOf("")  // Изменил
    var responsiblePerson by mutableStateOf("")  // Ответственный

    private val _selectedContact = mutableStateOf(com.example.sd.domain.contacts.Data())
    val selectedContact: State<com.example.sd.domain.contacts.Data> get() = _selectedContact

    fun getFilterMapFilter(): Map<String, String> {
        val filters = mutableMapOf<String, String>()

        // Фильтры для создания
        if (creationDateStart != "ДД.ММ.ГГ" && creationDateEnd != "ДД.ММ.ГГ") {
            filters["filter[created]"] =
                "${creationDateStart.formatTo("dd-MM-yyyy")},${creationDateEnd.formatTo("dd-MM-yyyy")}"
        } else if (creationDateStart != "ДД.ММ.ГГ" && creationDateEnd == "ДД.ММ.ГГ") {
            filters["filter[created]"] = "${creationDateStart.formatTo("dd-MM-yyyy")}}"
        } else if (creationDateStart == "ДД.ММ.ГГ" && creationDateEnd != "ДД.ММ.ГГ") {
            filters["filter[created]"] = ",${creationDateEnd.formatTo("dd-MM-yyyy")}"
        }

        // Фильтры для изменения
        if (modificationDateStart != "ДД.ММ.ГГ" || modificationDateEnd != "ДД.ММ.ГГ") {
            filters["filter[updated]"] =
                "${modificationDateStart.formatTo("dd-MM-yyyy")},${modificationDateEnd.formatTo("dd-MM-yyyy")}"
        } else if (modificationDateStart != "ДД.ММ.ГГ" && modificationDateEnd == "ДД.ММ.ГГ") {
            filters["filter[updated]"] = "${modificationDateStart.formatTo("dd-MM-yyyy")}}"
        } else if (modificationDateStart == "ДД.ММ.ГГ" && modificationDateEnd != "ДД.ММ.ГГ") {
            filters["filter[updated]"] = ",${modificationDateEnd.formatTo("dd-MM-yyyy")}"
        }


        if (birthDateStart != "ДД.ММ.ГГ" || birthDateEnd != "ДД.ММ.ГГ") {
            filters["filter[birth_date]"] =
                "${birthDateStart.formatTo("dd-MM-yyyy")},${birthDateEnd.formatTo("dd-MM-yyyy")}"
        } else if (birthDateStart != "ДД.ММ.ГГ" && birthDateEnd == "ДД.ММ.ГГ") {
            filters["filter[birth_date]"] = "${birthDateStart.formatTo("dd-MM-yyyy")}"
        } else if (birthDateStart == "ДД.ММ.ГГ" && birthDateEnd != "ДД.ММ.ГГ") {
            filters["filter[birth_date]"] = ",${birthDateEnd.formatTo("dd-MM-yyyy")}"
        }


        if (selectedType.isNotEmpty()) {
            filters["filter[type]"] = selectedType
        }
        if (selectedGender.isNotEmpty()) {
            filters["filter[gender]"] = selectedGender
        }
        if (selectedContragent.isNotEmpty()) {
            filters["filter[account]"] = selectedContragent
        }
        if (selectedBranch.isNotEmpty()) {
            filters["filter[branch]"] = selectedBranch
        }
        if (selectedDepartment.isNotEmpty()) {
            filters["filter[department]"] = selectedDepartment
        }
        if (selectedPosition.isNotEmpty()) {
            filters["filter[casta]"] = selectedPosition
        }
        if (mobilePhone.isNotEmpty()) {
            filters["filter[mobile_phone]"] = mobilePhone
        }
        if (homePhone.isNotEmpty()) {
            filters["filter[home_phone]"] = homePhone
        }
        if (email.isNotEmpty()) {
            filters["filter[email]"] = email
        }
        if (externalSystemId.isNotEmpty()) {
            filters["filter[ext_code]"] = externalSystemId
        }
        if (createdBy.isNotEmpty()) {
            filters["filter[created_user]"] = createdBy
        }
        if (modifiedBy.isNotEmpty()) {
            filters["filter[updated_user]"] = modifiedBy
        }
        if (responsiblePerson.isNotEmpty()) {
            filters["filter[owner]"] = responsiblePerson
        }

        return filters
    }

    fun getFilterMap(): Map<String, String> {
        val filterMap = mutableMapOf<String, String>()
        if (selectedFIO.isNotEmpty()) {
            filterMap["filter[name]"] = selectedFIO
        }
        // Добавить другие фильтры по аналогии
        return filterMap
    }


    fun showFilter() {
        if (creationDateStart != "ДД.ММ.ГГ") {
            _selectedFilters.add(creationDateStart)
        }
        if (creationDateEnd != "ДД.ММ.ГГ") {
            _selectedFilters.add(creationDateEnd)
        }
        if (modificationDateStart != "ДД.ММ.ГГ") {
            _selectedFilters.add(modificationDateStart)
        }
        if (modificationDateEnd != "ДД.ММ.ГГ") {
            _selectedFilters.add(modificationDateEnd)
        }
        if (birthDateStart != "ДД.ММ.ГГ") {
            _selectedFilters.add(birthDateStart)
        }
        if (birthDateEnd != "ДД.ММ.ГГ") {
            _selectedFilters.add(birthDateEnd)
        }
        if (selectedType.isNotEmpty()) {
            _selectedFilters.add(selectedType)
        }
        if (selectedGender.isNotEmpty()) {
            _selectedFilters.add(selectedGender)
        }
        if (selectedContragent.isNotEmpty()) {
            _selectedFilters.add(selectedContragent)
        }
        if (selectedBranch.isNotEmpty()) {
            _selectedFilters.add(selectedBranch)
        }
        if (selectedDepartment.isNotEmpty()) {
            _selectedFilters.add(selectedDepartment)
        }
        if (selectedPosition.isNotEmpty()) {
            _selectedFilters.add(selectedPosition)
        }
        if (mobilePhone.isNotEmpty()) {
            _selectedFilters.add(mobilePhone)
        }
        if (homePhone.isNotEmpty()) {
            _selectedFilters.add(homePhone)
        }
        if (email.isNotEmpty()) {
            _selectedFilters.add(email)
        }
        if (externalSystemId.isNotEmpty()) {
            _selectedFilters.add(externalSystemId)
        }
        if (createdBy.isNotEmpty()) {
            _selectedFilters.add(createdBy)
        }
        if (modifiedBy.isNotEmpty()) {
            _selectedFilters.add(modifiedBy)
        }
        if (responsiblePerson.isNotEmpty()) {
            _selectedFilters.add(responsiblePerson)
        }
    }

    fun removeFilter(filter: String) {
        _selectedFilters.remove(filter)
        when (filter) {
            creationDateStart -> creationDateStart = "ДД.ММ.ГГ"
            creationDateEnd -> creationDateEnd = "ДД.ММ.ГГ"
            modificationDateStart -> modificationDateStart = "ДД.ММ.ГГ"
            modificationDateEnd -> modificationDateEnd = "ДД.ММ.ГГ"
            birthDateStart -> birthDateStart = "ДД.ММ.ГГ"
            birthDateEnd -> birthDateEnd = "ДД.ММ.ГГ"
            selectedType -> selectedType = ""
            selectedGender -> selectedGender = ""
            selectedContragent -> selectedContragent = ""
            selectedBranch -> selectedBranch = ""
            selectedDepartment -> selectedDepartment = ""
            selectedPosition -> selectedPosition = ""
            mobilePhone -> mobilePhone = ""
            homePhone -> homePhone = ""
            email -> email = ""
            externalSystemId -> externalSystemId = ""
            createdBy -> createdBy = ""
            modifiedBy -> modifiedBy = ""
            responsiblePerson -> responsiblePerson = ""
        }
    }

    fun clearFilters() {
        _selectedFilters.clear()
        creationDateStart = "ДД.ММ.ГГ"
        creationDateEnd = "ДД.ММ.ГГ"
        modificationDateStart = "ДД.ММ.ГГ"
        modificationDateEnd = "ДД.ММ.ГГ"
        birthDateStart = "ДД.ММ.ГГ"
        birthDateEnd = "ДД.ММ.ГГ"
        selectedType = ""
        selectedGender = ""
        selectedContragent = ""
        selectedBranch = ""
        selectedDepartment = ""
        selectedPosition = ""
        mobilePhone = ""
        homePhone = ""
        email = ""
        externalSystemId = ""
        createdBy = ""
        modifiedBy = ""
        responsiblePerson = ""
    }
    fun itemTypeKnowledge(): List<String> {
        val knowledgeBasesType =  _stateGetContactType.value.response
        return knowledgeBasesType?.data?.map { it.name } ?: emptyList()
    }
    fun itemCasta(): List<String> {
        val stateCastas =  _stateCastas.value.response
        return stateCastas?.data?.map { it.name } ?: emptyList()
    }

    fun searchContactFilter(): Flow<PagingData<com.example.sd.domain.contacts.Data>> {
        return snapshotFlow { getFilterMapFilter() }
            .debounce(300)
            .flatMapLatest { query ->
                val filters = getFilterMapFilter()
                contactsUseCase(filters).cachedIn(viewModelScope)
            }
            .catch { e ->
                Log.e("ContactViewModel", "Ошибка загрузки: ${e.message}")
            }
    }
    fun searchContact(): Flow<PagingData<com.example.sd.domain.contacts.Data>> {
        return snapshotFlow {  }
            .debounce(300)
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
    fun getCastas() {
        getCastasUseCase.invoke().onEach { result: Resource<Castas> ->
            when (result) {
                is Resource.Success -> {
                    try {
                        val response: Castas? = result.data
                        _stateCastas.value =
                            CastasResponseState(response = response)
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
                    _stateCastas.value =
                        CastasResponseState(error = "${result.message}")
                }

                is Resource.Loading -> {
                    _stateCastas.value = CastasResponseState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}