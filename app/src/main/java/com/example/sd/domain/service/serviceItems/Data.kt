package com.example.sd.domain.service.serviceItems

data class Data(
    val available: Int,
    val bid_category_id: BidCategoryId,
    val created_at: String,
    val created_by: CreatedBy,
    val created_by_contact: Any,
    val id: String,
    val name: String,
    val number: Any,
    val reaction_time: Int,
    val reaction_time_unit_id: ReactionTimeUnitId,
    val service_category_id: ServiceCategoryId,
    val service_status_id: ServiceStatusId,
    val solution_time: Int,
    val solution_time_unit_id: SolutionTimeUnitId,
    val updated_at: String,
    val updated_by: UpdatedBy,
    val updated_by_contact: UpdatedByContact
)