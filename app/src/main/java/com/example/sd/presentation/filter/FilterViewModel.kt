package com.example.sd.presentation.filter

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    application: Application,

) : AndroidViewModel(application) {

    var selectedDateTime by mutableStateOf("Выберите дату и время")
    var selectedPlanDateTime by mutableStateOf("Выберите дату и время")
    var selectedState by mutableStateOf("Выберите состояние")
    var selectedCategory by mutableStateOf("Выберите категорию")
    var selectedLine by mutableStateOf("Выберите дату и время")
    var selectedService by mutableStateOf("Выберите сервис")
    var selectedContact by mutableStateOf("Выберите контакт")
    var selectedResponsible by mutableStateOf("Выберите ответственного")

    // Храните выбранные фильтры
    private val _selectedFilters = mutableStateListOf<String>()
    val selectedFilters: List<String> get() = _selectedFilters

    // Добавить фильтр
    fun addFilter(filter: String) {
        if (filter != "Выберите дату и время" && filter != "Выберите состояние" &&
            filter != "Выберите категорию" && filter != "Выберите линию" &&
            filter != "Выберите сервис" && filter != "Выберите контакт" &&
            filter != "Выберите ответственного" && !_selectedFilters.contains(filter)) {
            _selectedFilters.add(filter)
        }
    }

    // Удалить фильтр
    fun removeFilter(filter: String) {
        _selectedFilters.remove(filter)
    }

    // Очистить все фильтры
    fun clearFilters() {
        _selectedFilters.clear()
        selectedDateTime = "Выберите дату и время"
        selectedPlanDateTime = "Выберите дату и время"
        selectedState = "Выберите состояние"
        selectedCategory = "Выберите категорию"
        selectedLine = "Выберите линию"
        selectedService = "Выберите сервис"
        selectedContact = "Выберите контакт"
        selectedResponsible = "Выберите ответственного"
    }
}