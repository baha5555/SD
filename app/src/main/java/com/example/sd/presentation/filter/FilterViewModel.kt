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
import androidx.paging.map
import com.example.sd.domain.bits.Data
import com.example.sd.domain.filterResponse.FilteredBidsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(

    private val filteredBidsUseCase: FilteredBidsUseCase
) : ViewModel() {



    var selectedDateTime by mutableStateOf(Pair("", ""))
    var selectedPlanDateTime by mutableStateOf(Pair("", ""))
    var selectedState by mutableStateOf("")
    var selectedCategory by mutableStateOf("")
    var selectedLine by mutableStateOf("")
    var selectedService by mutableStateOf("")
    var selectedContact by mutableStateOf("")
    var selectedResponsible by mutableStateOf("")
    var selectedName by mutableStateOf("")


    // Храните выбранные фильтры
    private val _selectedFilters = mutableStateListOf<String>()
    val selectedFilters: List<String> get() = _selectedFilters



    fun showFilter() {
        if (selectedDateTime != Pair("","")) {
            _selectedFilters.add("${selectedDateTime.first},${selectedDateTime.second}")
        }
        if (selectedPlanDateTime != Pair("","")) {
            _selectedFilters.add("${selectedPlanDateTime.first},${selectedPlanDateTime.second}")
        }
        if (selectedState != "") {
            _selectedFilters.add(selectedState)
        }
        if (selectedCategory != "") {
            _selectedFilters.add(selectedCategory)
        }
        if (selectedLine!= "") {
            _selectedFilters.add(selectedLine)
        }
        if (selectedService!= "") {
            _selectedFilters.add(selectedService)
        }
        if (selectedContact!= "") {
            _selectedFilters.add(selectedContact)
        }
        if (selectedResponsible!= "") {
            _selectedFilters.add(selectedResponsible)
        }
        if (selectedName.isNotEmpty()) {
            _selectedFilters.add(selectedName)
        }

    }

    // Удалить фильтр
    fun removeFilter(filter: String) {
        _selectedFilters.remove(filter)
        when (filter) {
            selectedDateTime.toString() -> selectedDateTime = Pair("","")
            selectedPlanDateTime.toString() -> selectedPlanDateTime = Pair("","")
            selectedState -> selectedState = ""
            selectedCategory -> selectedCategory = ""
            selectedLine -> selectedLine = ""
            selectedService -> selectedService = ""
            selectedContact -> selectedContact = ""
            selectedResponsible -> selectedResponsible = ""
            selectedName -> selectedName = ""
        }
    }

    // Очистить все фильтры
    fun clearFilters() {
        _selectedFilters.clear()
        selectedDateTime = Pair("","")
        selectedPlanDateTime = Pair("","")
        selectedState = ""
        selectedCategory = ""
        selectedLine = ""
        selectedService = ""
        selectedContact = ""
        selectedResponsible = ""
        selectedName = ""
    }

    // Преобразуем фильтры в Map для отправки на сервер
    fun getFilterMap(): Map<String, String> {
        val filters = mutableMapOf<String, String>()

        if (selectedDateTime != Pair("","")) {
            filters["filter[created]"] = "${selectedDateTime.first},${selectedDateTime.second}"
        }
        if (selectedPlanDateTime != Pair("","")) {
            filters["filter[solution_date]"] = "${selectedPlanDateTime.first},${selectedPlanDateTime.second}"
        }
        if (selectedState != "") {
            filters["filter[status]"] = selectedState
        }
        if (selectedCategory != "") {
            filters["filter[category]"] = selectedCategory
        }
        if (selectedLine != "") {
            filters["filter[support_level]"] = selectedLine
        }
        if (selectedService != "") {
            filters["filter[service_item]"] = selectedService
        }
        if (selectedContact != "") {
            filters["filter[contact]"] = selectedContact
        }
        if (selectedResponsible != "") {
            filters["filter[owner]"] = selectedResponsible
        }
        if (selectedName != "") {
            filters["filter[name]"] = selectedName
        }

        return filters
    }

    
    fun fetchFilteredBids(): Flow<PagingData<Data>> {
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