package com.example.sd.domain.bits

data class Data(
    val account_id: AccountId?=null,
    val bid_category_id: BidCategoryId?=null,
    val bid_status_id: BidStatusId?=null,
    val change_id: ChangeId?=null,
    val closure_code_id: Any?=null,
    val closure_date: Any?=null,
    val contact_id: ContactId?=null,
    val created_at: String?=null,
    val created_contact_id: CreatedContactId?=null,
    val created_user_id: CreatedUserId?=null,
    val department_id: DepartmentId?=null,
    val holder_id: HolderId?=null,
    val id: String?=null,
    val name: String?=null,
    val number: String?=null,
    val origin_id: OriginId?=null,
    val owner_id: OwnerId?=null,
    val owner_roles:  Any? = null,
    val parent_id: Any?=null,
    val priority_id: PriorityId?=null,
    val problem_id: Any?=null,
    val responded_date: String?=null,
    val response_date: String?=null,
    val response_overdue: Int?=null,
    val satisfaction_level_id: Any?=null,
    val service_item_category: ServiceItemCategory?=null,
    val service_item_id: ServiceItemId?=null,
    val service_pact_id: ServicePactId?=null,
    val solution_date: String?=null,
    val solution_overdue: Int?=null,
    val solution_provided_date: String?=null,
    val support_level_id: SupportLevelId?=null,
    val symptoms: String?=null,
    val updated_at: String?=null,
    val updated_contact_id: Any?=null,
    val updated_user_id: UpdatedUserId?=null,
    val viewed_by: Any?=null,
    val viewed_by_owner: Int?=null
)
{
    val _owner_roles: Any?
        get() = when (owner_roles) {
            is List<*> -> owner_roles.filterIsInstance<OwnerRole>()
            is String -> owner_roles
            else -> null
        }
}

