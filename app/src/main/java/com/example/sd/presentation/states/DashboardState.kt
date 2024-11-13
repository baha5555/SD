package com.example.sd.presentation.states

import com.example.sd.domain.bits.GetBids
import com.example.sd.domain.dashboard.Dashboard


data class DashboardState (
    val isLoading: Boolean = false,
    var response: Dashboard? = null,
    val error: String = ""
)