package com.jeongg.ieum.data.dto.interest

import kotlinx.serialization.Serializable


@Serializable
data class InterestPublicDTO (
    val mainTopic: String,
    val subTopic: List<SubTopic>
)

@Serializable
data class SubTopic(
    val interestId: Long,
    val name: String,
    val isActive: Boolean
)