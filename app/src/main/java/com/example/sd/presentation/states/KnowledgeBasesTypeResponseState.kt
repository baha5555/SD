package com.example.sd.presentation.states

import com.example.sd.domain.athorization.AuthResponse
import com.example.sd.domain.contacts.contactType.ContactType
import com.example.sd.domain.knowledgeBases.knowledgeBasesType.KnowledgeBasesType
import kotlinx.coroutines.Job


data class KnowledgeBasesTypeResponseState(
    val isLoading: Boolean = false,
    var response: KnowledgeBasesType? = null,
    val error: String = ""
)