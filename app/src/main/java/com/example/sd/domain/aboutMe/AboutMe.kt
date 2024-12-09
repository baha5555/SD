package com.example.sd.domain.aboutMe

data class AboutMe(
    val account_id: AccountId,
    val contact_id: ContactId,
    val created_at: String,
    val department_id: DepartmentId,
    val email: String,
    val ext_code: String,
    val fio: String,
    val id: String,
    val name: String,
    val `operator`: Int,
    val ps_admin: Int,
    val roles: List<Role>,
    val updated_at: String
)