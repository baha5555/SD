package com.example.sd.presentation.states

import com.example.sd.domain.bits.bidStatus.GetBidsStatus

data class GetBidsStatusResponseState (
    val isLoading: Boolean = false,
    var response: GetBidsStatus? = null,
    val error: String = ""
)