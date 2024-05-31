package com.jeongg.ieum.data.repository

import com.jeongg.ieum.data.api.InterestApi
import com.jeongg.ieum.domain.repository.InterestRepository
import io.ktor.client.statement.HttpResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InterestRepositoryImpl @Inject constructor(
    private val interestApi: InterestApi
): InterestRepository{
    override suspend fun getInterestAll(): HttpResponse {
        return interestApi.getInterestAll()
    }

    override suspend fun getInterestPublic(): HttpResponse {
        return interestApi.getInterestPublic()
    }

    override suspend fun getInterestPrivate(): HttpResponse {
        return interestApi.getInterestPrivate()
    }

    override suspend fun createInterest(interestId: Long): HttpResponse {
        return interestApi.createInterest(interestId)
    }

    override suspend fun deleteInterest(interestId: Long): HttpResponse {
        return interestApi.deleteInterest(interestId)
    }

}