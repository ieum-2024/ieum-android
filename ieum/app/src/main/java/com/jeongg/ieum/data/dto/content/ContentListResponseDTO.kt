package com.jeongg.ieum.data.dto.content

import kotlinx.serialization.Serializable

@Serializable
data class ContentListResponseDTO(
    val contentList: List<Content>,
    val cursor: NextCursorDTO
)

@Serializable
data class Content(
    val contentId: Long = -1,
    val title: String = "",
    val pubDate: String = "",
    val description: String = "",
    val nickname: String = "",
    val thumbnail: String = "",
)

@Serializable
data class NextCursorDTO(
    val cursor: String,
    val hasNext: Boolean
)
