package com.example.sd.presentation.states

import com.example.sd.domain.bits.bidPriorities.GetBidPriorities

data class GetBidPrioritiesResponseState (
    val isLoading: Boolean = false,
    var response: GetBidPriorities? = null,
    val error: String = ""
)