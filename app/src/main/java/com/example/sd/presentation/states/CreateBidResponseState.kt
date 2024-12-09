package com.example.sd.presentation.states

import com.example.sd.domain.bits.bidStore.BidStore

data class CreateBidResponseState (
    val isLoading: Boolean = false,
    var response: BidStore? = null,
    val error: String = ""
)