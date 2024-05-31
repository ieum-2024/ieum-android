package com.jeongg.ieum.domain.repository

import com.jeongg.ieum.data.dto.content.ContentRequestDTO
import io.ktor.client.statement.HttpResponse
import java.io.File

interface ContentRepository {
    suspend fun getContentByInterestId(interestId: Long?, cursor: String?): HttpResponse
    suspend fun getContentDetail(contentId: Long): HttpResponse
    suspend fun createContent(contentRequestDTO: ContentRequestDTO, img: List<File>): HttpResponse
}