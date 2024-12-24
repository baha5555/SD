package com.example.sd.presentation.report

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sd.domain.report.detailReport.DetailReport
import com.example.sd.domain.report.detailReport.ReportDetailedUseCase
import com.example.sd.presentation.states.ReportDetailedResponseState
import com.example.sd.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
@HiltViewModel
class ReportViewModel @Inject constructor(
    private val getReportDetailedUseCase: ReportDetailedUseCase
) : ViewModel() {


    private val _stateGetReportDetail = mutableStateOf(ReportDetailedResponseState())
    val stateGetReportDetail: State<ReportDetailedResponseState> = _stateGetReportDetail


    var selectedAccount by mutableStateOf("")


    fun itemNameAccount(): List<String> {
        val getReportDetail = _stateGetReportDetail.value.response
        return getReportDetail?.data?.map { it.name } ?: emptyList()
    }

    private val _selectedAccountData = mutableStateOf<DetailReport.Data?>(null)
    val selectedAccountData: State<DetailReport.Data?> = _selectedAccountData

    fun updateSelectedAccountData(depCode: String) {
        val response = _stateGetReportDetail.value.response
        _selectedAccountData.value = response?.data?.find { it.code == depCode }
    }


    fun getReportDetail(firstDate:String, secondDate:String, depCode:String) {
        getReportDetailedUseCase.invoke(firstDate, secondDate, depCode).onEach { result: Resource<DetailReport> ->
            when (result) {
                is Resource.Success -> {
                    try {
                        val response: DetailReport? = result.data
                        _stateGetReportDetail.value =
                            ReportDetailedResponseState(response = response)
                        Log.e(
                            "GetBidsOriginsResponseStateResponse",
                            "GetBidsOriginsResponseStateResponse->\n ${_stateGetReportDetail.value}"
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
                    _stateGetReportDetail.value =
                        ReportDetailedResponseState(error = "${result.message}")
                }

                is Resource.Loading -> {
                    _stateGetReportDetail.value = ReportDetailedResponseState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

}