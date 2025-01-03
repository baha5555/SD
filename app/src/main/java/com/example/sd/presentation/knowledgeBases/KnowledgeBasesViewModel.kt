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
    private val getKnowledgeBasesDetailUseCase: GetKnowledgeBasesDetailUseCase
) : ViewModel() {

    private val _stateGetKnowledgeBasesDetail = mutableStateOf(KnowledgeBasesDetailResponseState())
    val stateGetKnowledgeBasesDetail: State<KnowledgeBasesDetailResponseState> =
        _stateGetKnowledgeBasesDetail


    private val _stateGetKnowledgeBasesType = mutableStateOf(KnowledgeBasesTypeResponseState())
    val stateGetKnowledgeBasesType: State<KnowledgeBasesTypeResponseState> =
        _stateGetKnowledgeBasesType


    var selectedDateTime by mutableStateOf(Pair("", ""))
    var selectedPlanDateTime by mutableStateOf(Pair("", ""))

    var createStartDate by mutableStateOf("ДД.ММ.ГГ")
    var createEndDate by mutableStateOf("ДД.ММ.ГГ")
    var updateStarDate by mutableStateOf("ДД.ММ.ГГ")
    var updateEndDate by mutableStateOf("ДД.ММ.ГГ")
    var selectedType by mutableStateOf("")
    var selectedCreate by mutableStateOf("")
    var selectedUpdate by mutableStateOf("")
    var selectedTeg by mutableStateOf("")
    var selectedName by mutableStateOf("")


    private val _selectedFilters = mutableStateListOf<String>()
    val selectedFilters: List<String> get() = _selectedFilters


    fun showFilter() {
        if (selectedDateTime != Pair("", "")) {
            _selectedFilters.add("${selectedDateTime.first},${selectedDateTime.second}")
        }
        if (selectedPlanDateTime != Pair("", "")) {
            _selectedFilters.add("${selectedPlanDateTime.first},${selectedPlanDateTime.second}")
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
        if (selectedName.isNotEmpty()) {
            _selectedFilters.add(selectedName)
        }

    }

    fun removeFilter(filter: String) {
        _selectedFilters.remove(filter)
        when (filter) {
            selectedDateTime.toString() -> selectedDateTime = Pair("","")
            selectedPlanDateTime.toString() -> selectedPlanDateTime = Pair("","")
            selectedType -> selectedType = ""
            selectedCreate -> selectedCreate = ""
            selectedUpdate -> selectedUpdate = ""
            selectedTeg -> selectedTeg = ""
            selectedName -> selectedName = ""

        }
    }

    fun clearFilters() {
        _selectedFilters.clear()
        selectedDateTime = Pair("","")
        selectedPlanDateTime = Pair("","")
        selectedType = ""
        selectedCreate = ""
        selectedUpdate = ""
        selectedTeg = ""
        selectedName = ""

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
                        _stateGetKnowledgeBasesDetail.value =
                            KnowledgeBasesDetailResponseState(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
    }


    fun getFilterMap(): Map<String, String> {
        val filters = mutableMapOf<String, String>()



        if (selectedDateTime != Pair("","")) {
            filters["filter[created]"] = "${selectedDateTime.first},${selectedDateTime.second}"
        }
        if (selectedPlanDateTime != Pair("","")) {
            filters["filter[updated]"] = "${selectedPlanDateTime.first},${selectedPlanDateTime.second}"
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
        if (selectedName != "") {
            filters["filter[name]"] = selectedName
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
                    _stateGetKnowledgeBasesType.value =
                        KnowledgeBasesTypeResponseState(isLoading = true)
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