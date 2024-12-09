package com.example.sd.presentation.states

import com.example.sd.domain.contacts.GetContacts

data class GetContactsResponseState (
    val isLoading: Boolean = false,
    var response: GetContacts? = null,
    val error: String = ""
)