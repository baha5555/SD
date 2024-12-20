package com.example.sd.domain.contacts

data class Data(
    val account_id: AccountId?=null,
    val birth_date: String?=null,
    val branch_id: BranchId?=null,
    val casta_id: CastaId?=null,
    val contact_type_id: ContactTypeId?=null,
    val created_at: String?=null,
    val created_by: Any?=null,
    val created_by_contact: Any?=null,
    val department_id: DepartmentId?=null,
    val email: Any?=null,
    val gender_id: GenderId?=null,
    val id: String?=null,
    val mobile_phone: String?=null,
    val name: String?=null,
    val owner_id: OwnerId?=null,
    val phone: Any?=null,
    val updated_at: String?=null,
    val updated_by: Any?=null,
    val updated_by_contact: Any?=null
)