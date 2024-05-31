package com.jeongg.ieum.data.dto.interest

import kotlinx.serialization.Serializable

@Serializable
data class InterestAllDTO (
    val interestId: Long,
    val name: String
)