package com.example.sd.presentation.states

import com.example.sd.domain.bits.GetBids
import com.example.sd.domain.changePassword.ChangePassword


data class ChangePasswordResponseState (
    val isLoading: Boolean = false,
    var response: ChangePassword? = null,
    val error: String = ""
)