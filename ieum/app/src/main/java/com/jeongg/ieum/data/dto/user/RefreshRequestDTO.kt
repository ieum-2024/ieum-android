package com.jeongg.ieum.data.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class RefreshRequestDTO(
    val refreshToken: String
)
