package com.example.sd.domain.bits.bidStore

data class BidStore(
    val message: String,
    val messageCode: Int?,
    val statusCode: Int?,
    val statue:Boolean,
    val bid_origin_id:List<String>?,
    val bid_category_id:List<String>?,
    val service_pact_id:List<String>?,

)

