package com.jeongg.ieum.data.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class RefreshResponseDTO(
    val accessToken: String,
    val refreshToken: String
)
