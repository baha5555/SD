package com.example.sd.domain.filterResponse

import com.example.sd.domain.bits.Data

data class FilterResponse(
    val `data`: List<Data>,
    val links: Links,
    val meta: Meta
)