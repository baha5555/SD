package com.example.sd.domain.knowledgeBases.knowledgeBasesDetail

import com.example.sd.domain.knowledgeBases.CreatedBy
import com.example.sd.domain.knowledgeBases.CreatedByContact

data class Data(
    val available: Int,
    val code: String,
    val created_at: String,
    val created_by: CreatedBy,
    val created_by_contact: CreatedByContact,
    val id: String,
    val keywords: String,
    val knowledge_base_type_id: KnowledgeBaseTypeId,
    val name: String,
    val notes: String,
    val tags: List<String>,
    val updated_at: String,
    val updated_by: UpdatedBy,
    val updated_by_contact: UpdatedByContact,
    val view_count: Int
)