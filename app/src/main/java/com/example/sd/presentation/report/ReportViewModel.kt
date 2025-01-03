package com.example.sd.presentation.report

import android.os.Environment
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sd.domain.report.detailReport.DetailReport
import com.example.sd.domain.report.detailReport.ReportDetailedUseCase
import com.example.sd.domain.report.linesReport.ReportByLineUseCase
import com.example.sd.domain.report.linesReport.ReportLines
import com.example.sd.presentation.states.ReportByLineResponseState
import com.example.sd.presentation.states.ReportDetailedResponseState
import com.example.sd.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

import javax.inject.Inject
@HiltViewModel
class ReportViewModel @Inject constructor(
    private val getReportDetailedUseCase: ReportDetailedUseCase,
    private val getReportByLineUseCase: ReportByLineUseCase
) : ViewModel() {


    private val _stateGetReportDetail = mutableStateOf(ReportDetailedResponseState())
    val stateGetReportDetail: State<ReportDetailedResponseState> = _stateGetReportDetail
    private val _stateGetReportByLine = mutableStateOf(ReportByLineResponseState())
    val stateGetReportByLine: State<ReportByLineResponseState> = _stateGetReportByLine


    var selectedAccount by mutableStateOf("")
    var selectedLine by mutableStateOf("")


    fun itemNameAccount(): List<String> {
        val getReportDetail = _stateGetReportDetail.value.response
        return getReportDetail?.data?.map { it.name } ?: emptyList()
    }
    fun itemNameLines(): List<String> {
        val getReportDetail = _stateGetReportByLine.value.response
        return getReportDetail?.map { it.level } ?: emptyList()
    }

    private val _selectedAccountData = mutableStateOf<DetailReport.Data?>(null)
    val selectedAccountData: State<DetailReport.Data?> = _selectedAccountData

    fun updateSelectedAccountData(depCode: String) {
        val response = _stateGetReportDetail.value.response
        _selectedAccountData.value = response?.data?.find { it.code == depCode }
    }


    private val _selectedLineData = mutableStateOf<ReportLines.ReportLinesItem?>(null)
    val selectedLineData: State<ReportLines.ReportLinesItem?> = _selectedLineData

    fun updateSelectedLineData(line: String) {
        val response = _stateGetReportByLine.value.response
        _selectedLineData.value = response?.find { it.level == line }
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
    fun getReportByLine(firstDate:String, secondDate:String) {
        getReportByLineUseCase.invoke(firstDate, secondDate).onEach { result: Resource<ReportLines> ->
            when (result) {
                is Resource.Success -> {
                    try {
                        val response: ReportLines? = result.data
                        _stateGetReportByLine.value =
                            ReportByLineResponseState(response = response)
                        Log.e(
                            "GetBidsOriginsResponseStateResponse",
                            "GetBidsOriginsResponseStateResponse->\n ${_stateGetReportByLine.value}"
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
                    _stateGetReportByLine.value =
                        ReportByLineResponseState(error = "${result.message}")
                }

                is Resource.Loading -> {
                    _stateGetReportByLine.value = ReportByLineResponseState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }


    /*fun generateExcel(detailReport: DetailReport): File {
        val workbook: Workbook = XSSFWorkbook()
        val sheet: Sheet = workbook.createSheet("Detail Report")

        // Заголовки столбцов
        val headerRow = sheet.createRow(0)
        val headers = listOf("Code", "Name", "Total", "Completed All", "Completed On Time", "Not Completed On Time", "In Work", "Canceled", "Prc Failure")
        headers.forEachIndexed { index, header ->
            val cell = headerRow.createCell(index)
            cell.setCellValue(header)
        }

        // Данные
        detailReport.data.forEachIndexed { rowIndex, item ->
          *//*  val row = sheet.createRow(rowIndex + 1)
            row.createCell(0).setCellValue(item.code)
            row.createCell(1).setCellValue(item.name)
            row.createCell(2).setCellValue(item.data.total.toDouble())
            row.createCell(3).setCellValue(item.data.completed_all.toDouble())
            row.createCell(4).setCellValue(item.data.completed_on_time.toDouble())
            row.createCell(5).setCellValue(item.data.not_completed_on_time.toDouble())
            row.createCell(6).setCellValue(item.data.in_work.toDouble())
            row.createCell(7).setCellValue(item.data.canceled.toDouble())
            row.createCell(8).setCellValue(item.data.prc_failure)*//*
        }

        // Сохранение в файл
        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            "DetailReport.xlsx"
        )
        FileOutputStream(file).use { outputStream ->
            workbook.write(outputStream)
            workbook.close()
        }

        return file
    }*/


}