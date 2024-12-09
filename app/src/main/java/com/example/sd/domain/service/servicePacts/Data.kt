package com.example.sd.domain.service.servicePacts

data class Data(
    val created_at: String,
    val created_by: CreatedBy,
    val end_date: String,
    val id: String,
    val name: String,
    val notes: Any,
    val number: String,
    val owner_id: OwnerId,
    val service_pact_status_id: ServicePactStatusId,
    val service_pact_type_id: ServicePactTypeId,
    val service_provider_contact_id: ServiceProviderContactId,
    val service_provider_id: ServiceProviderId,
    val start_date: String,
    val updated_at: String,
    val updated_by: UpdatedBy
)