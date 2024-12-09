package com.example.sd.presentation.states

import com.example.sd.domain.bits.bidPriorities.GetBidPriorities

data class GetUUIDResponseState (
    val isLoading: Boolean = false,
    var response: String? = null,
    val error: String = ""
)