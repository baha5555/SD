package com.example.sd.domain.contacts.contactType

data class Data(
    val available: Int,
    val created_at: String,
    val created_by: CreatedBy,
    val id: String,
    val name: String,
    val updated_at: String,
    val updated_by: UpdatedBy
)