package com.example.sd.presentation.states

import com.example.sd.domain.bits.GetBids
import com.example.sd.domain.castas.Castas
import com.example.sd.domain.changePassword.ChangePassword


data class CastasResponseState (
    val isLoading: Boolean = false,
    var response: Castas? = null,
    val error: String = ""
)