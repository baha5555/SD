package com.example.sd.domain.aboutMe

data class AboutMe(
    val account_id: Any,
    val contact_id: Any,
    val created_at: String,
    val department_id: Any,
    val email: String,
    val ext_code: Any,
    val fio: String,
    val id: String,
    val name: String,
    val `operator`: Int,
    val ps_admin: Int,
    val roles: List<Role>,
    val updated_at: String
)