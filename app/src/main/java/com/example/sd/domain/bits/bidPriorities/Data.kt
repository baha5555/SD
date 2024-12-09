package com.example.sd.domain.bits.bidPriorities

data class Data(
    val available: Int,
    val code: String,
    val created_at: String,
    val created_by: CreatedBy,
    val description: String,
    val id: String,
    val name: String,
    val updated_at: String,
    val updated_by: UpdatedBy
)