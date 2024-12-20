package com.example.sd.presentation.states

import com.example.sd.domain.athorization.AuthResponse
import com.example.sd.domain.contacts.contactType.ContactType


data class ContactTypeResponseState(
    val isLoading: Boolean = false,
    var response: ContactType? = null,
    val error: String = ""
)