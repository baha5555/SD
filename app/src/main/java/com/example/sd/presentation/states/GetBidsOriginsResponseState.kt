package com.example.sd.presentation.states

import com.example.sd.domain.bits.bidOrigins.BidOrigins

data class GetBidsOriginsResponseState (
    val isLoading: Boolean = false,
    var response: BidOrigins? = null,
    val error: String = ""
)