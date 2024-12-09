package com.example.sd.presentation.createBids

import android.app.Application
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.sd.domain.aboutMe.AboutMe
import com.example.sd.domain.bits.bidCategories.GetBidCategories
import com.example.sd.domain.bits.bidCategories.GetBidCategoriesUseCase
import com.example.sd.domain.bits.bidOrigins.BidOrigins
import com.example.sd.domain.bits.bidOrigins.BidOriginsUseCase
import com.example.sd.domain.bits.bidPriorities.GetBidPriorities
import com.example.sd.domain.bits.bidPriorities.GetBidPrioritiesUseCase
import com.example.sd.domain.bits.bidStatus.GetBidsStatus
import com.example.sd.domain.bits.bidStatus.GetBidsStatusUseCase
import com.example.sd.domain.bits.bidStore.BidStore
import com.example.sd.domain.bits.bidStore.CreateBidUseCase
import com.example.sd.domain.entityNumber.EntityNumber
import com.example.sd.domain.entityNumber.EntityNumberUseCase
import com.example.sd.domain.supportLevels.GetSupportLevelsUseCase
import com.example.sd.domain.uuid.UUIDUseCase
import com.example.sd.presentation.states.AboutMeResponseState
import com.example.sd.presentation.states.CreateBidResponseState
import com.example.sd.presentation.states.GetBidCategoriesResponseState
import com.example.sd.presentation.states.GetBidPrioritiesResponseState
import com.example.sd.presentation.states.GetBidsOriginsResponseState
import com.example.sd.presentation.states.GetBidsStatusResponseState
import com.example.sd.presentation.states.GetEntityNumberResponseState
import com.example.sd.presentation.states.GetUUIDResponseState
import com.example.sd.utils.Resource
import com.example.sd.utils.Values
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class CreateBidsViewModel @Inject constructor(
    application: Application,
    private val getBidCategoriesUseCase: GetBidCategoriesUseCase,
    private val getBidsStatusUseCase: GetBidsStatusUseCase,
    private val getBidPrioritiesUseCase: GetBidPrioritiesUseCase,
    private val getSupportLevelsUseCase: GetSupportLevelsUseCase,
    private val getBidsOriginsUseCase: BidOriginsUseCase,
    private val getUUIDUseCase: UUIDUseCase,
    private val createBidUseCase: CreateBidUseCase,
    private val entityNumberUseCase: EntityNumberUseCase
) : AndroidViewModel(application) {

    private val _stategetBidCategories = mutableStateOf(GetBidCategoriesResponseState())
    val stategetBidCategories: State<GetBidCategoriesResponseState> = _stategetBidCategories


    private val _stateCreateBid = mutableStateOf(CreateBidResponseState())
    val stateCreateBid: State<CreateBidResponseState> = _stateCreateBid

    private val _stateEntityNumber = mutableStateOf(GetEntityNumberResponseState())
    val stateEntityNumber: State<GetEntityNumberResponseState> = _stateEntityNumber

    private val _stateGetUUID = mutableStateOf(GetUUIDResponseState())
    val stateGetUUID: State<GetUUIDResponseState> = _stateGetUUID

    private val _stategetBidStatus = mutableStateOf(GetBidsStatusResponseState())
    val stategetBidStatus: State<GetBidsStatusResponseState> = _stategetBidStatus

    private val _stateBidsOrigins = mutableStateOf(GetBidsOriginsResponseState())
    val stateBidsOrigins: State<GetBidsOriginsResponseState> = _stateBidsOrigins

    private val _stategetBidPriorities = mutableStateOf(GetBidPrioritiesResponseState())
    val stategetBidPriorities: State<GetBidPrioritiesResponseState> = _stategetBidPriorities

    private val _stateSupportLevels = mutableStateOf(GetBidPrioritiesResponseState())
    val stateSupportLevels: State<GetBidPrioritiesResponseState> = _stateSupportLevels

    private val _currentStep = MutableStateFlow(1) // Текущий шаг
    val currentStep: StateFlow<Int> = _currentStep
    fun resetStep() {
        _currentStep.value = 1
    }

    private val _role = MutableStateFlow("default") // Роль пользователя
    val role: StateFlow<String> = _role

    // Шаги, доступные для текущей роли
    private val _availableSteps = MutableStateFlow(listOf(1, 2, 3, 4, 5, 6, 7))
    val availableSteps: StateFlow<List<Int>> = _availableSteps

    private val _nameCreate = MutableStateFlow("")
    val nameCreate: StateFlow<String> = _nameCreate

    private val _descriptionCreate = MutableStateFlow("")
    val descriptionCreate: StateFlow<String> = _descriptionCreate

    private val _statusCreate = MutableStateFlow("")
    val statusCreate: StateFlow<String> = _statusCreate

    private val _categoryCreate = MutableStateFlow("")
    val categoryCreate: StateFlow<String> = _categoryCreate

    private val _priorityCreate = MutableStateFlow("")
    val priorityCreate: StateFlow<String> = _priorityCreate

    private val _levelCreate = MutableStateFlow("")
    val levelCreate: StateFlow<String> = _levelCreate

    private val _contactCreate = MutableStateFlow("")
    val contactCreate: StateFlow<String> = _contactCreate

    private val _responsibleCreate = MutableStateFlow("")
    val responsibleCreate: StateFlow<String> = _responsibleCreate

    private val _accountCreate = MutableStateFlow("")
    val accountCreate: StateFlow<String> = _accountCreate

    private val _servicePactCreate = MutableStateFlow("")
    val servicePactCreate: StateFlow<String> = _servicePactCreate

    private val _serviceItemCreate = MutableStateFlow("")
    val serviceItemCreate: StateFlow<String> = _serviceItemCreate

    private val _originCreate = MutableStateFlow("")
    val originCreate: StateFlow<String> = _originCreate

    private val _regDataCreate = MutableStateFlow("")
    val regDataCreate: StateFlow<String> = _regDataCreate

    private val _respDataCreate = MutableStateFlow("ДД.ММ.ГГ 00:00")
    val respDataCreate: StateFlow<String> = _respDataCreate

    private val _resolutionDataCreate = MutableStateFlow("ДД.ММ.ГГ 00:00")
    val resolutionDataCreate: StateFlow<String> = _resolutionDataCreate


    private val _nameCreateID = MutableStateFlow("")
    val nameCreateID: StateFlow<String> = _nameCreateID

    private val _descriptionCreateID = MutableStateFlow("")
    val descriptionCreateID: StateFlow<String> = _descriptionCreateID

    private val _statusCreateID = MutableStateFlow("")
    val statusCreateID: StateFlow<String> = _statusCreateID

    private val _categoryCreateID = MutableStateFlow("")
    val categoryCreateID: StateFlow<String> = _categoryCreateID

    private val _priorityCreateID = MutableStateFlow("")
    val priorityCreateID: StateFlow<String> = _priorityCreateID

    private val _levelCreateID = MutableStateFlow("")
    val levelCreateID: StateFlow<String> = _levelCreateID

    private val _contactCreateID = MutableStateFlow("")
    val contactCreateID: StateFlow<String> = _contactCreateID

    private val _responsibleCreateID = MutableStateFlow("")
    val responsibleCreateID: StateFlow<String> = _responsibleCreateID

    private val _accountCreateID = MutableStateFlow("")
    val accountCreateID: StateFlow<String> = _accountCreateID

    private val _servicePactCreateID = MutableStateFlow("")
    val servicePactCreateID: StateFlow<String> = _servicePactCreateID

    private val _serviceItemCreateID = MutableStateFlow("")
    val serviceItemCreateID: StateFlow<String> = _serviceItemCreateID

    private val _originCreateID = MutableStateFlow("")
    val originCreateID: StateFlow<String> = _originCreateID

    private val _regDataCreateID = MutableStateFlow("")
    val regDataCreateID: StateFlow<String> = _regDataCreateID

    private val _respDataCreateID = MutableStateFlow("ДД.ММ.ГГ 00:00")
    val respDataCreateID: StateFlow<String> = _respDataCreateID

    private val _resolutionDataCreateID = MutableStateFlow("ДД.ММ.ГГ 00:00")
    val resolutionDataCreateID: StateFlow<String> = _resolutionDataCreateID

    // Функция для обновления значения
    fun updateField(field: String, id: String, name: String) {
        when (field) {
            "name" -> {
                _nameCreate.value = name
                _nameCreateID.value = id
            }
            "description" -> {
                _descriptionCreate.value = name
                _descriptionCreateID.value = id
            }
            "status" -> {
                _statusCreate.value = name
                _statusCreateID.value = id
            }
            "category" -> {
                _categoryCreate.value = name
                _categoryCreateID.value = id
            }
            "priority" -> {
                _priorityCreate.value = name
                _priorityCreateID.value = id
            }
            "level" -> {
                _levelCreate.value = name
                _levelCreateID.value = id
            }
            "contact" -> {
                _contactCreate.value = name
                _contactCreateID.value = id
            }
            "responsible" -> {
                _responsibleCreate.value = name
                _responsibleCreateID.value = id
            }
            "account" -> {
                _accountCreate.value = name
                _accountCreateID.value = id
            }
            "servicePact" -> {
                _servicePactCreate.value = name
                _servicePactCreateID.value = id
            }
            "serviceItem" -> {
                _serviceItemCreate.value = name
                _serviceItemCreateID.value = id
            }
            "origin" -> {
                _originCreate.value = name
                _originCreateID.value = id
            }
            "regData" -> {
                _regDataCreate.value = name
                _regDataCreateID.value = id
            }
            "respData" -> {
                _respDataCreate.value = name
                _respDataCreateID.value = id
            }
            "resolutionData" -> {
                _resolutionDataCreate.value = name
                _resolutionDataCreateID.value = id
            }
        }
    }
    fun resetFields() {
        _nameCreate.value = ""
        _descriptionCreate.value = ""
        _statusCreate.value = ""
        _categoryCreate.value = ""
        _priorityCreate.value = ""
        _levelCreate.value = ""
        _contactCreate.value = ""
        _responsibleCreate.value = ""
        _accountCreate.value = ""
        _servicePactCreate.value = ""
        _serviceItemCreate.value = ""
        _originCreate.value = ""
        _regDataCreate.value = ""
        _respDataCreate.value = "ДД.ММ.ГГ 00:00"
        _resolutionDataCreate.value = "ДД.ММ.ГГ 00:00"

        // Resetting ID values
        _nameCreateID.value = ""
        _descriptionCreateID.value = ""
        _statusCreateID.value = ""
        _categoryCreateID.value = ""
        _priorityCreateID.value = ""
        _levelCreateID.value = ""
        _contactCreateID.value = ""
        _responsibleCreateID.value = ""
        _accountCreateID.value = ""
        _servicePactCreateID.value = ""
        _serviceItemCreateID.value = ""
        _originCreateID.value = ""
        _regDataCreateID.value = ""
        _respDataCreateID.value = ""
        _resolutionDataCreateID.value = ""
    }
    fun getEntityNumber(entityNumber: String){

        entityNumberUseCase.invoke(entityNumber).onEach { result: Resource<EntityNumber> ->
            when (result) {
                is Resource.Success -> {
                    try {
                        val response: EntityNumber? = result.data
                        _stateEntityNumber.value = GetEntityNumberResponseState(response = response)


                        Log.e(
                            "_stateEntityNumberResponse22223",
                            "_stateEntityNumberResponse->\n ${_stateEntityNumber.value}"
                        )
                        Log.e(
                            "_stateEntityNumberResponse22223",
                            "_stateEntityNumberResponse->\n ${_stateEntityNumber.value.response!!}"
                        )
                    } catch (e: Exception) {
                        Log.d("Exception", "${e.message} Exception")
                    }
                }

                is Resource.Error -> {
                    Log.e("_stateEntityNumberResponse", "AboutMeResponseError->\n ${result.message}")
                    _stateEntityNumber.value = GetEntityNumberResponseState(error = "${result.message}")
                }

                is Resource.Loading -> {
                    _stateEntityNumber.value = GetEntityNumberResponseState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }


    fun createBid(
        onSuccess: () -> Unit
    ) {
        createBidUseCase.invoke(
            id = _stateGetUUID.value.response.toString(),
            name = _nameCreate.value,
            number = _stateEntityNumber.value.response?.number.toString(),
            symptoms = _descriptionCreate.value,
            ownerId = _responsibleCreateID.value,
            responseDate = _respDataCreate.value.format("yy-MM-dd"),
            solutionDate = _resolutionDataCreate.value.format("yy-MM-dd"),
            bidStatusId = _statusCreateID.value,
            bidPriorityId = _priorityCreateID.value,
            bidOriginId = _originCreateID.value,
            accountId = _accountCreateID.value,
            contactId = _contactCreateID.value,
            solution = null,
            satisfactionLevelId = null,
            bidCategoryId = _categoryCreateID.value,
            responseOverdue = null,
            solutionOverdue = null,
            satisfactionComment = null,
            reasonDelay = null,
            solutionRemains = null,
            servicePactId = _servicePactCreateID.value,
            serviceItemId = _serviceItemCreateID.value,
            supportLevelId = _levelCreateID.value,
            parentId = null,
            holderId = null,
            problemId = null,
            departmentId = Values.DEPARTAMENTID.value,
            fileCollection = _stateGetUUID.value.response.toString(),
            feedCollection = _stateGetUUID.value.response.toString()
        ).onEach { result: Resource<BidStore> ->
            when (result) {
                is Resource.Success -> {
                    try {
                        val response: BidStore? = result.data
                        _stateCreateBid.value = CreateBidResponseState(response = response)


                        Log.e(
                            "createBid",
                            "createBid->\n ${_stateCreateBid.value}"
                        )
                        Log.e(
                            "createBid",
                            "createBid->\n ${_stateCreateBid.value.response!!}"
                        )
                        if(response?.message =="Данные успешно добавлены" ){
                            onSuccess()
                        }
                        else {
                            Toast.makeText(getApplication(), response.toString(), Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Log.d("Exception", "${e.message} Exception")
                    }
                }

                is Resource.Error -> {
                    Log.e("createBid", "createBidError->\n ${result.message}")
                    _stateCreateBid.value = CreateBidResponseState(error = "${result.message}")
                }

                is Resource.Loading -> {
                    _stateCreateBid.value = CreateBidResponseState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateServiceSelection(
        selectedService: String,
        reactionTime: Long?,
        solutionTime: Long?
    ) {
        // Устанавливаем выбранный сервис
        _serviceItemCreate.value = selectedService

        // Определяем форматтер для формата ДД.ММ.ГГ 00:00
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm")

        // Текущая дата и время
        val registrationDateTime = LocalDateTime.now()
        _regDataCreate.value = registrationDateTime.format(formatter)
        // Функция для добавления рабочих дней
        fun addWorkingDays(startDate: LocalDateTime, workingDaysToAdd: Long): LocalDateTime {
            var date = startDate
            var daysAdded = 0L

            while (daysAdded < workingDaysToAdd) {
                date = date.plusDays(1)
                // Пропускаем субботу и воскресенье
                if (date.dayOfWeek != DayOfWeek.SATURDAY && date.dayOfWeek != DayOfWeek.SUNDAY) {
                    daysAdded++
                }
            }

            return date
        }


        if (reactionTime != null) {
            val reactionDateTime = addWorkingDays(registrationDateTime, reactionTime)
            _respDataCreate.value = reactionDateTime.format(formatter) // Форматируем дату
        }

        // Обработка solution_time
        if (solutionTime != null) {
            val resolutionDateTime = addWorkingDays(registrationDateTime, solutionTime)
            _resolutionDataCreate.value = resolutionDateTime.format(formatter) // Форматируем дату
            Log.i(
                "regdata",
                "_resolutionDataCreate.value->${_resolutionDataCreate.value}   _respDataCreate.value->${_respDataCreate.value}"
            )
        }
    }

    fun setRole() {
        val role = Values.ROLES.value
        _availableSteps.value = if (role == "portal_users") {
            listOf(1, 2, 7)
        } else {
            listOf(1, 2, 3, 4, 5, 6, 7)
        }
    }


    fun getBidCategories() {

        getBidCategoriesUseCase.invoke().onEach { result: Resource<GetBidCategories> ->
            when (result) {
                is Resource.Success -> {
                    try {
                        val response: GetBidCategories? = result.data
                        _stategetBidCategories.value =
                            GetBidCategoriesResponseState(response = response)
                        Log.e(
                            "stategetBidCategoriesResponse",
                            "stategetBidCategoriesResponse->\n ${_stategetBidCategories.value}"
                        )
                    } catch (e: Exception) {
                        Log.d("Exception", "${e.message} Exception")
                    }
                }

                is Resource.Error -> {
                    Log.e(
                        "stategetBidCategoriesResponse",
                        "stategetBidCategoriesResponseError->\n ${result.message}"
                    )
                    _stategetBidCategories.value =
                        GetBidCategoriesResponseState(error = "${result.message}")
                }

                is Resource.Loading -> {
                    _stategetBidCategories.value = GetBidCategoriesResponseState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getUUID() {

        getUUIDUseCase.invoke().onEach { result: Resource<String> ->
            when (result) {
                is Resource.Success -> {
                    try {
                        val response: String? = result.data
                        _stateGetUUID.value =
                            GetUUIDResponseState(response = response)
                        Log.e(
                            "GetUUIDResponseStateResponse",
                            "GetUUIDResponseStateResponse->\n ${_stategetBidCategories.value}"
                        )
                    } catch (e: Exception) {
                        Log.d("Exception", "${e.message} Exception")
                    }
                }

                is Resource.Error -> {
                    Log.e(
                        "GetUUIDResponseStateResponse",
                        "GetUUIDResponseStateResponseError->\n ${result.message}"
                    )
                    _stateGetUUID.value =
                        GetUUIDResponseState(error = "${result.message}")
                }

                is Resource.Loading -> {
                    _stateGetUUID.value = GetUUIDResponseState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getBidsOrigins() {

        getBidsOriginsUseCase.invoke().onEach { result: Resource<BidOrigins> ->
            when (result) {
                is Resource.Success -> {
                    try {
                        val response: BidOrigins? = result.data
                        _stateBidsOrigins.value =
                            GetBidsOriginsResponseState(response = response)
                        Log.e(
                            "GetBidsOriginsResponseStateResponse",
                            "GetBidsOriginsResponseStateResponse->\n ${_stategetBidCategories.value}"
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
                    _stateBidsOrigins.value =
                        GetBidsOriginsResponseState(error = "${result.message}")
                }

                is Resource.Loading -> {
                    _stateBidsOrigins.value = GetBidsOriginsResponseState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getBidStatus() {

        getBidsStatusUseCase.invoke().onEach { result: Resource<GetBidsStatus> ->
            when (result) {
                is Resource.Success -> {
                    try {
                        val response: GetBidsStatus? = result.data
                        _stategetBidStatus.value = GetBidsStatusResponseState(response = response)
                        Log.e(
                            "GetBidsStatusResponseState",
                            "stategetBidCategoriesResponse->\n ${_stategetBidCategories.value}"
                        )
                    } catch (e: Exception) {
                        Log.d("Exception", "${e.message} Exception")
                    }
                }

                is Resource.Error -> {
                    Log.e(
                        "GetBidsStatusResponseState",
                        "stategetBidCategoriesResponseError->\n ${result.message}"
                    )
                    _stategetBidStatus.value =
                        GetBidsStatusResponseState(error = "${result.message}")
                }

                is Resource.Loading -> {
                    _stategetBidStatus.value = GetBidsStatusResponseState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getBidPriorities() {

        getBidPrioritiesUseCase.invoke().onEach { result: Resource<GetBidPriorities> ->
            when (result) {
                is Resource.Success -> {
                    try {
                        val response: GetBidPriorities? = result.data
                        _stategetBidPriorities.value =
                            GetBidPrioritiesResponseState(response = response)
                        Log.e(
                            "GetBidPrioritiesResponseState",
                            "GetBidPrioritiesResponseStateResponse->\n ${_stategetBidCategories.value}"
                        )
                    } catch (e: Exception) {
                        Log.d("Exception", "${e.message} Exception")
                    }
                }

                is Resource.Error -> {
                    Log.e(
                        "GetBidPrioritiesResponseState",
                        "GetBidPrioritiesResponseStateResponseError->\n ${result.message}"
                    )
                    _stategetBidPriorities.value =
                        GetBidPrioritiesResponseState(error = "${result.message}")
                }

                is Resource.Loading -> {
                    _stategetBidPriorities.value = GetBidPrioritiesResponseState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getSupportLevels() {

        getSupportLevelsUseCase.invoke().onEach { result: Resource<GetBidPriorities> ->
            when (result) {
                is Resource.Success -> {
                    try {
                        val response: GetBidPriorities? = result.data
                        _stateSupportLevels.value =
                            GetBidPrioritiesResponseState(response = response)
                        Log.e(
                            "GetBidPrioritiesResponseState",
                            "GetBidPrioritiesResponseStateResponse->\n ${_stategetBidCategories.value}"
                        )
                    } catch (e: Exception) {
                        Log.d("Exception", "${e.message} Exception")
                    }
                }

                is Resource.Error -> {
                    Log.e(
                        "GetBidPrioritiesResponseState",
                        "GetBidPrioritiesResponseStateResponseError->\n ${result.message}"
                    )
                    _stateSupportLevels.value =
                        GetBidPrioritiesResponseState(error = "${result.message}")
                }

                is Resource.Loading -> {
                    _stateSupportLevels.value = GetBidPrioritiesResponseState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }


    fun goToNextStep() {
        val nextIndex = _availableSteps.value.indexOf(_currentStep.value) + 1
        if (nextIndex in _availableSteps.value.indices) {
            _currentStep.value = _availableSteps.value[nextIndex]
        }
    }

    fun goToPreviousStep() {
        val prevIndex = _availableSteps.value.indexOf(_currentStep.value) - 1
        if (prevIndex in _availableSteps.value.indices) {
            _currentStep.value = _availableSteps.value[prevIndex]
        }
    }
}