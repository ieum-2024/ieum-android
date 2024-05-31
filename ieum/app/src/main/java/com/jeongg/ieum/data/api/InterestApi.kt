package com.jeongg.ieum.data.api

import com.jeongg.ieum.data._const.HttpRoutes
import com.jeongg.ieum.domain.repository.InterestRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse

class InterestApi(
    private val client: HttpClient
): InterestRepository {
    override suspend fun getInterestAll(): HttpResponse {
        return client.get(HttpRoutes.GET_INTEREST_ALL.path)
    }

    override suspend fun getInterestPublic(): HttpResponse {
        return client.get(HttpRoutes.GET_INTEREST_PUBLIC.path)
    }

    override suspend fun getInterestPrivate(): HttpResponse {
        return client.get(HttpRoutes.GET_INTEREST_PRIVATE.path)
    }

    override suspend fun createInterest(interestId: Long): HttpResponse {
        return client.post(HttpRoutes.CREATE_INTEREST.path + "/$interestId")
    }

    override suspend fun deleteInterest(interestId: Long): HttpResponse {
        return client.post(HttpRoutes.DELETE_INTEREST.path + "/$interestId")
    }
}