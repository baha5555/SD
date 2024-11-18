package com.example.sd.domain.filterResponse

data class Data(
    val account_id: AccountId,
    val branch_id: BranchId,
    val casta_id: CastaId,
    val contact_type_id: ContactTypeId,
    val created_at: String,
    val created_by: CreatedBy,
    val created_by_contact: Any,
    val department_id: DepartmentId,
    val email: String,
    val id: String,
    val mobile_phone: Any,
    val name: String,
    val owner_id: OwnerId,
    val phone: Any,
    val updated_at: String,
    val updated_by: UpdatedBy,
)