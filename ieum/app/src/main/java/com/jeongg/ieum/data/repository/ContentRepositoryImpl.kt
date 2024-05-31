package com.jeongg.ieum.data.repository

import com.jeongg.ieum.data.api.ContentApi
import com.jeongg.ieum.data.dto.content.ContentRequestDTO
import com.jeongg.ieum.domain.repository.ContentRepository
import io.ktor.client.statement.HttpResponse
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContentRepositoryImpl @Inject constructor(
    private val contentApi: ContentApi
): ContentRepository {

    override suspend fun getContentByInterestId(interestId: Long?, cursor: String?): HttpResponse {
        return contentApi.getContentByInterestId(interestId, cursor)
    }

    override suspend fun getContentDetail(contentId: Long): HttpResponse {
        return contentApi.getContentDetail(contentId)
    }

    override suspend fun createContent(contentRequestDTO: ContentRequestDTO, img: List<File>): HttpResponse {
        return contentApi.createContent(contentRequestDTO, img)
    }
}