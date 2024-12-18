package com.example.sd.presentation.knowledgeBases

import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.sd.domain.contacts.GetContactsUseCase
import com.example.sd.domain.knowledgeBases.KnowledgeBasesUseCase
import com.example.sd.domain.knowledgeBases.knowledgeBasesDetail.GetKnowledgeBasesDetailUseCase
import com.example.sd.domain.knowledgeBases.knowledgeBasesDetail.KnowledgeBasesDetail
import com.example.sd.domain.knowledgeBases.knowledgeBasesType.KnowledgeBaseTypesUseCase
import com.example.sd.domain.knowledgeBases.knowledgeBasesType.KnowledgeBasesType
import com.example.sd.presentation.states.KnowledgeBasesDetailResponseState
import com.example.sd.presentation.states.KnowledgeBasesTypeResponseState
import com.example.sd.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class KnowledgeBasesViewModel @Inject constructor(
    private val knowledgeBasesUseCase: KnowledgeBasesUseCase,
    private val getKnowledgeBaseTypesUseCase: KnowledgeBaseTypesUseCase,
    private val contactsUseCase: GetContactsUseCase,
    private val getKnowledgeBasesDetailUseCase : GetKnowledgeBasesDetailUseCase
) : ViewModel() {

    var selectedCreate1 by mutableStateOf("")

    // Функция для поиска
    fun searchContactName(name: String): Flow<PagingData<com.example.sd.domain.contacts.Data>> {
        return contactsUseCase(mapOf("filter[name]" to name)) // Здесь мы передаем фильтр в UseCase
            .cachedIn(viewModelScope)
    }

    fun addFilter1(selectedName: String) {
        selectedCreate = selectedName // Обновляем фильтр
    }


    private val _stateGetKnowledgeBasesDetail = mutableStateOf(KnowledgeBasesDetailResponseState())
    val stateGetKnowledgeBasesDetail: State<KnowledgeBasesDetailResponseState> = _stateGetKnowledgeBasesDetail

    private val _selectedOrder = mutableStateOf(com.example.sd.domain.knowledgeBases.Data())
    val selectedOrder: State<com.example.sd.domain.knowledgeBases.Data> get() = _selectedOrder

    private val _stateGetKnowledgeBasesType = mutableStateOf(KnowledgeBasesTypeResponseState())
    val stateGetKnowledgeBasesType: State<KnowledgeBasesTypeResponseState> = _stateGetKnowledgeBasesType

    var createStartDate by mutableStateOf("ДД.ММ.ГГ")
    var createEndDate by mutableStateOf("ДД.ММ.ГГ")
    var updateStarDate by mutableStateOf("ДД.ММ.ГГ")
    var updateEndDate by mutableStateOf("ДД.ММ.ГГ")
    var selectedType by mutableStateOf("")
    var selectedCreate by mutableStateOf("")
    var selectedUpdate by mutableStateOf("")
    var selectedTeg by mutableStateOf("")


    private val _selectedFilters1 = mutableStateListOf<String>()
    private val _selectedFilters = mutableStateListOf<String>()
    val selectedFilters: List<String> get() = _selectedFilters




    fun showFilter() {
        if (createStartDate != "ДД.ММ.ГГ") {
            _selectedFilters.add(createStartDate)
        }
        if (createEndDate != "ДД.ММ.ГГ") {
            _selectedFilters.add(createEndDate)
        }
        if (updateStarDate != "ДД.ММ.ГГ") {
            _selectedFilters.add(updateStarDate)
        }
        if (updateEndDate != "ДД.ММ.ГГ") {
            _selectedFilters.add(updateEndDate)
        }
        if (selectedType.isNotEmpty()) {
            _selectedFilters.add(selectedType)
        }
        if (selectedCreate.isNotEmpty()) {
            _selectedFilters.add(selectedCreate)
        }
        if (selectedUpdate.isNotEmpty()) {
            _selectedFilters.add(selectedUpdate)
        }
        if (selectedTeg.isNotEmpty()) {
            _selectedFilters.add(selectedTeg)
        }

    }

    fun removeFilter(filter: String) {
        _selectedFilters.remove(filter)
        when (filter) {
            createStartDate -> createStartDate = "ДД.ММ.ГГ"
            createEndDate -> createEndDate = "ДД.ММ.ГГ"
            updateEndDate -> updateEndDate = "ДД.ММ.ГГ"
            updateStarDate -> updateStarDate = "ДД.ММ.ГГ"
            selectedType -> selectedType = ""
            selectedCreate -> selectedCreate = ""
            selectedUpdate -> selectedUpdate = ""
            selectedTeg -> selectedTeg = ""

        }
    }

    fun clearFilters() {
        _selectedFilters.clear()
        createStartDate = "ДД.ММ.ГГ"
        createEndDate = "ДД.ММ.ГГ"
        updateEndDate = "ДД.ММ.ГГ"
        updateStarDate = "ДД.ММ.ГГ"
        selectedType = ""
        selectedCreate = ""
        selectedUpdate = ""
        selectedTeg = ""

    }


    fun getKnowledgeBasesDetail(
        knowledgeBaseId: String,
        success: (Boolean, String?) -> Unit
    ) {
        getKnowledgeBasesDetailUseCase.invoke(knowledgeBaseId)
            .onEach { result: Resource<KnowledgeBasesDetail> ->
                when (result) {
                    is Resource.Success -> {
                        try {
                            val response: KnowledgeBasesDetail? = result.data
                            _stateGetKnowledgeBasesDetail.value =
                                KnowledgeBasesDetailResponseState(response = response)
                            Log.d(
                                "ChangePasswordResponse",
                                "ChangePasswordResponse->\n ${_stateGetKnowledgeBasesDetail.value}"
                            )
                            success.invoke(true, null)
                        } catch (e: Exception) {
                            Log.d("Exception", "${e.message} Exception")
                            success.invoke(false, e.message)
                        }
                    }

                    is Resource.Error -> {
                        Log.d(
                            "ChangePasswordResponse",
                            "ChangePasswordResponseError->\n ${result.message}"
                        )
                        _stateGetKnowledgeBasesDetail.value =
                            KnowledgeBasesDetailResponseState(error = "${result.message}")
                        success.invoke(false, "${result.message}")
                    }

                    is Resource.Loading -> {
                        _stateGetKnowledgeBasesDetail.value = KnowledgeBasesDetailResponseState(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
    }


    fun getFilterMap(): Map<String, String> {
        val filters = mutableMapOf<String, String>()

        if (createStartDate != "ДД.ММ.ГГ" && createEndDate != "ДД.ММ.ГГ") {
            filters["filter[created]"] =
                "${createStartDate.formatTo("dd-MM-yyyy")},${createEndDate.formatTo("dd-MM-yyyy")}"
        } else if (createStartDate != "ДД.ММ.ГГ" && createEndDate == "ДД.ММ.ГГ") {
            filters["filter[created]"] = "${createStartDate.formatTo("dd-MM-yyyy")}}"
        } else if (createStartDate == "ДД.ММ.ГГ" && createEndDate != "ДД.ММ.ГГ") {
            filters["filter[created]"] = ",${createEndDate.formatTo("dd-MM-yyyy")}"
        }



        if (updateStarDate != "ДД.ММ.ГГ" || updateEndDate != "ДД.ММ.ГГ") {
            filters["filter[updated]"] =
                "${updateStarDate.formatTo("dd-MM-yyyy")},${updateEndDate.formatTo("dd-MM-yyyy")}"
        } else if (updateStarDate != "ДД.ММ.ГГ" && updateEndDate == "ДД.ММ.ГГ") {
            filters["filter[created]"] = "${createStartDate.formatTo("dd-MM-yyyy")}}"
        } else if (updateStarDate == "ДД.ММ.ГГ" && updateEndDate != "ДД.ММ.ГГ") {
            filters["filter[created]"] = ",${updateEndDate.formatTo("dd-MM-yyyy")}"
        }

        if (selectedType != "") {
            filters["filter[type]"] = selectedType
        }
        if (selectedCreate != "") {
            filters["filter[created_contact]"] = selectedCreate
        }
        if (selectedUpdate != "") {
            filters["filter[updated_contact]"] = selectedUpdate
        }
        if (selectedTeg != "Введите тег" && selectedTeg.isNotEmpty()) {
            filters["filter[tag]"] = selectedTeg
        }

        return filters
    }


    fun filterKnowledgeBases(): Flow<PagingData<com.example.sd.domain.knowledgeBases.Data>> {
        return try {
            // Получаем карту фильтров
            val filters = getFilterMap()
            Log.e("FilterViewModel", "filters = getFilterMap(): ${filters}")

            // Вызываем UseCase с фильтрами
            knowledgeBasesUseCase(filters)
                .cachedIn(viewModelScope) // Сохраняем данные при повороте экрана
        } catch (e: Exception) {
            Log.e("ViewModel", "Ошибка при загрузке данных: ${e.message}")

            throw Exception("Ошибка при загрузке данных: ${e.message}")

        }
    }


    fun updateSelectedOrder(order: com.example.sd.domain.knowledgeBases.Data) {

        _selectedOrder.value = order

    }

    fun itemTypeKnowledge(): List<String> {
        val knowledgeBasesType = _stateGetKnowledgeBasesType.value.response
        return knowledgeBasesType?.data?.map { it.name } ?: emptyList()
    }


    fun getKnowledgeBasesType() {
        getKnowledgeBaseTypesUseCase.invoke().onEach { result: Resource<KnowledgeBasesType> ->
            when (result) {
                is Resource.Success -> {
                    try {
                        val response: KnowledgeBasesType? = result.data
                        _stateGetKnowledgeBasesType.value =
                            KnowledgeBasesTypeResponseState(response = response)
                        Log.e(
                            "GetBidsOriginsResponseStateResponse",
                            "GetBidsOriginsResponseStateResponse->\n ${_stateGetKnowledgeBasesType.value}"
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
                    _stateGetKnowledgeBasesType.value =
                        KnowledgeBasesTypeResponseState(error = "${result.message}")
                }

                is Resource.Loading -> {
                    _stateGetKnowledgeBasesType.value = KnowledgeBasesTypeResponseState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}

fun String.formatTo(newFormat: String, currentFormat: String = "dd.MM.yyyy"): String {
    val parser = SimpleDateFormat(currentFormat, Locale.getDefault())
    val formatter = SimpleDateFormat(newFormat, Locale.getDefault())
    val date = parser.parse(this)
    return formatter.format(date!!)
}