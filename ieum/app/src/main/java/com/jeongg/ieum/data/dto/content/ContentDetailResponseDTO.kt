package com.jeongg.ieum.data.dto.content

import kotlinx.serialization.Serializable

@Serializable
data class ContentDetailResponseDTO(
    val menteeId: Int = -1,
    val title: String = "",
    val pubDate: String = "",
    val description: String = "",
    val nickname: String = "",
    val images: List<String> = emptyList()
)
