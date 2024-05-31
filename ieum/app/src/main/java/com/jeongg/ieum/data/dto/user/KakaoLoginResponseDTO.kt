package com.jeongg.ieum.data.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class KakaoLoginResponseDTO(
    val accessToken: String,
    val refreshToken: String,
    val userId: Long
)