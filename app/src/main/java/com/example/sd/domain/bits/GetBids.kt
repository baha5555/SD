package com.example.sd.domain.bits

data class GetBids(
    val `data`: List<Data>,
    val links: Links,
    val meta: Meta
)