package com.jeongg.ieum.data.dto.interest

import kotlinx.serialization.Serializable

@Serializable
data class InterestPrivateDTO(
    val interestId: Long,
    val name: String
)