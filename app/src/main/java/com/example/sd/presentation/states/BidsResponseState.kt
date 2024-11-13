package com.example.sd.presentation.states

import com.example.sd.domain.bits.GetBids


data class BidsResponseState (
    val isLoading: Boolean = false,
    var response: GetBids? = null,
    val error: String = ""
)