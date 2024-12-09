package com.example.sd.domain.contacts

data class GetContacts(
    val `data`: List<Data>,
    val links: Links,
    val meta: Meta
)