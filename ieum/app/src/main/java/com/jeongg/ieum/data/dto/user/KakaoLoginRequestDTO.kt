package com.jeongg.ieum.data.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class KakaoLoginRequestDTO(
    val code: String
)
