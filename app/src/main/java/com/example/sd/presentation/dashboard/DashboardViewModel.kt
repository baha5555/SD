package com.example.sd.presentation.dashboard

import android.app.Application
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.sd.data.preference.CustomPreference
import com.example.sd.domain.athorization.AuthResponse
import com.example.sd.domain.athorization.AuthUseCase
import com.example.sd.domain.bits.BidsUseCase
import com.example.sd.domain.dashboard.Dashboard
import com.example.sd.domain.dashboard.DashboardUseCase
import com.example.sd.presentation.states.AuthResponseState
import com.example.sd.presentation.states.DashboardState
import com.example.sd.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
@HiltViewModel
class DashboardViewModel @Inject constructor(
    application: Application,
    private val dashboardUseCase: DashboardUseCase
) : AndroidViewModel(application) {
    private val _stateDashboard = mutableStateOf(DashboardState())
    val stateDashboard: State<DashboardState> = _stateDashboard



    fun getDashboard() {
        dashboardUseCase.invoke().onEach { result: Resource<Dashboard> ->
            when (result) {
                is Resource.Success -> {
                    try {
                        val response: Dashboard? = result.data
                        _stateDashboard.value = DashboardState(response = response)
                        Log.e("DashboardResponse", "DashboardResponse->\n ${_stateDashboard.value}")
                    } catch (e: Exception) {
                        Log.d("Exception", "${e.message} Exception")
                    }
                }
                is Resource.Error -> {
                    Log.e("DashboardResponse", "DashboardResponseError->\n ${result.message}")
                    _stateDashboard.value = DashboardState(error = "${result.message}")
                }
                is Resource.Loading -> {
                    _stateDashboard.value = DashboardState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}
