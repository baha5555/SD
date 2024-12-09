package com.example.sd.domain.bits.bidStore

data class ResponseDate(
    val account_id: List<String>,
    val bid_category_id: List<String>,
    val bid_origin_id: List<String>,
    val bid_priority_id: List<String>,
    val bid_status_id: List<String>,
    val contact_id: List<String>,
    val feed_collection: List<String>,
    val file_collection: List<String>,
    val service_item_id: List<String>,
    val service_pact_id: List<String>,
    val support_level_id: List<String>
)