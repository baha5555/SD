package com.example.sd.domain.aboutMe

data class Role(
    val created_at: String,
    val guard_name: String,
    val id: String,
    val name: String,
    val name_ru: String,
    val pivot: Pivot,
    val updated_at: String
)