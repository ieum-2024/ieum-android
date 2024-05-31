package com.jeongg.ieum.domain.repository

import io.ktor.client.statement.HttpResponse

interface InterestRepository {
    suspend fun getInterestAll(): HttpResponse
    suspend fun getInterestPublic(): HttpResponse
    suspend fun getInterestPrivate(): HttpResponse
    suspend fun createInterest(interestId: Long): HttpResponse
    suspend fun deleteInterest(interestId: Long): HttpResponse
}