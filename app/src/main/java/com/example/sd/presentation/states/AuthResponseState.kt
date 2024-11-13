package com.example.sd.presentation.states

import com.example.sd.domain.athorization.AuthResponse



data class AuthResponseState(
    val isLoading: Boolean = false,
    var response: AuthResponse? = null,
    val error: String = ""
)