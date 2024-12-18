package com.example.sd.domain.knowledgeBases

data class Data(
    val created_at: String? = null,
    val created_by: CreatedBy? = null,
    val created_by_contact: CreatedByContact? = null,
    val id: String? = null,
    val knowledge_base_type_id: KnowledgeBaseTypeId? = null,
    val name: String? = null,
    val updated_at: String? = null,
    val updated_by: UpdatedBy? = null,
    val updated_by_contact: UpdatedByContact? = null,
    val view_count: Int? = null,
)