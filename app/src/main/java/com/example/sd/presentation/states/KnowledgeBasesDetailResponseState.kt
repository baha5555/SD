package com.example.sd.presentation.states

import com.example.sd.domain.athorization.AuthResponse
import com.example.sd.domain.contacts.contactType.ContactType
import com.example.sd.domain.knowledgeBases.knowledgeBasesDetail.KnowledgeBasesDetail
import com.example.sd.domain.knowledgeBases.knowledgeBasesType.KnowledgeBasesType
import kotlinx.coroutines.Job


data class KnowledgeBasesDetailResponseState(
    val isLoading: Boolean = false,
    var response: KnowledgeBasesDetail? = null,
    val error: String = ""
)