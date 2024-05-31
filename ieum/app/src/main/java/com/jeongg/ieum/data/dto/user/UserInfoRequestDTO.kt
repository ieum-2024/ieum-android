package com.jeongg.ieum.data.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class UserInfoRequestDTO(
    val id: Int,
    val nickname: String,
    val role: String
)
