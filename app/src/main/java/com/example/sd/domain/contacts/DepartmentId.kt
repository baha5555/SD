package com.example.sd.domain.contacts

data class DepartmentId(
    val code: String,
    val id: String,
    val name: String,
    val parent_id: ParentId
)