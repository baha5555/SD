package com.example.sd.presentation.filter

import android.app.Application
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.sd.domain.bits.Data
import com.example.sd.domain.filterResponse.FilteredBidsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(

    private val filteredBidsUseCase: FilteredBidsUseCase
) : ViewModel() {

    var selectedDateTime by mutableStateOf("ДД.ММ.ГГ 00:00")
    var selectedPlanDateTime by mutableStateOf("ДД.ММ.ГГ 00:00")
    var selectedState by mutableStateOf("Выберите состояние")
    var selectedCategory by mutableStateOf("Выберите категорию")
    var selectedLine by mutableStateOf("Выберите линию")
    var selectedService by mutableStateOf("Выберите сервис")
    var selectedContact by mutableStateOf("Выберите контакт")
    var selectedResponsible by mutableStateOf("Выберите ответственного")

    // Храните выбранные фильтры
    private val _selectedFilters = mutableStateListOf<String>()
    val selectedFilters: List<String> get() = _selectedFilters




    fun addFilter(filter: String) {
        if (filter != "ДД.ММ.ГГ 00:00" && filter != "Выберите состояние" &&
            filter != "Выберите категорию" && filter != "Выберите линию" &&
            filter != "Выберите сервис" && filter != "Выберите контакт" &&
            filter != "Выберите ответственного" && !_selectedFilters.contains(filter)) {
            _selectedFilters.add(filter)
        }
    }

    // Удалить фильтр
    fun removeFilter(filter: String) {
        _selectedFilters.remove(filter)
        when (filter) {
            selectedDateTime -> selectedDateTime = "ДД.ММ.ГГ 00:00"
            selectedPlanDateTime -> selectedPlanDateTime = "ДД.ММ.ГГ 00:00"
            selectedState -> selectedState = "Выберите состояние"
            selectedCategory -> selectedCategory = "Выберите категорию"
            selectedLine -> selectedLine = "Выберите линию"
            selectedService -> selectedService = "Выберите сервис"
            selectedContact -> selectedContact = "Выберите контакт"
            selectedResponsible -> selectedResponsible = "Выберите ответственного"
        }
    }

    // Очистить все фильтры
    fun clearFilters() {
        _selectedFilters.clear()
        selectedDateTime = "ДД.ММ.ГГ 00:00"
        selectedPlanDateTime = "ДД.ММ.ГГ 00:00"
        selectedState = "Выберите состояние"
        selectedCategory = "Выберите категорию"
        selectedLine = "Выберите линию"
        selectedService = "Выберите сервис"
        selectedContact = "Выберите контакт"
        selectedResponsible = "Выберите ответственного"
    }

    // Преобразуем фильтры в Map для отправки на сервер
    fun getFilterMap(): Map<String, String> {
        val filters = mutableMapOf<String, String>()

        if (selectedDateTime != "ДД.ММ.ГГ 00:00") {
            filters["filter[created]"] = selectedDateTime
        }
        if (selectedPlanDateTime != "ДД.ММ.ГГ 00:00") {
            filters["filter[solution_date]"] = selectedPlanDateTime
        }
        if (selectedState != "Выберите состояние") {
            filters["filter[status]"] = selectedState
        }
        if (selectedCategory != "Выберите категорию") {
            filters["filter[category]"] = selectedCategory
        }
        if (selectedLine != "Выберите линию") {
            filters["filter[support_level]"] = selectedLine
        }
        if (selectedService != "Выберите сервис") {
            filters["filter[service_item]"] = selectedService
        }
        if (selectedContact != "Выберите контакт") {
            filters["filter[contact]"] = selectedContact
        }
        if (selectedResponsible != "Выберите ответственного") {
            filters["filter[owner]"] = selectedResponsible
        }

        return filters
    }

    
    fun fetchFilteredBids(): Flow<PagingData<com.example.sd.domain.bits.Data>> {
        return try {
            // Получаем карту фильтров
            val filters = getFilterMap()
            Log.e("FilterViewModel", "filters = getFilterMap(): ${filters}")

            // Вызываем UseCase с фильтрами
            filteredBidsUseCase(filters)
                .cachedIn(viewModelScope) // Сохраняем данные при повороте экрана
        } catch (e: Exception) {
            Log.e("ViewModel", "Ошибка при загрузке данных: ${e.message}")

            throw Exception("Ошибка при загрузке данных: ${e.message}")

        }
    }

}