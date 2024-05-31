package com.jeongg.ieum.data.dto.content

import kotlinx.serialization.Serializable

@Serializable
data class ContentRequestDTO(
    val title: String,
    val subCategory: Long,
    val description: String
)
