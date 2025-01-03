package com.example.sd.presentation.states

import com.example.sd.domain.report.detailReport.DetailReport
import com.example.sd.domain.report.linesReport.ReportLines


data class ReportByLineResponseState (
    val isLoading: Boolean = false,
    var response: ReportLines? = null,
    val error: String = ""
)