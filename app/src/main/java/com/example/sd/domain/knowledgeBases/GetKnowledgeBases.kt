package com.example.sd.domain.knowledgeBases

data class GetKnowledgeBases(
    val `data`: List<Data>,
    val links: Links,
    val meta: Meta
)