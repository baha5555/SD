package com.example.sd.presentation.states

import com.example.sd.domain.report.detailReport.DetailReport


data class ReportDetailedResponseState (
    val isLoading: Boolean = false,
    var response: DetailReport? = null,
    val error: String = ""
)