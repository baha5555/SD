package com.example.sd.presentation.states

import com.example.sd.domain.bits.bidPriorities.GetBidPriorities
import com.example.sd.domain.entityNumber.EntityNumber

data class GetEntityNumberResponseState (
    val isLoading: Boolean = false,
    var response: EntityNumber? = null,
    val error: String = ""
)