package com.jeongg.ieum.data.api

import com.jeongg.ieum.data._const.HttpRoutes
import com.jeongg.ieum.data.dto.content.ContentRequestDTO
import com.jeongg.ieum.domain.repository.ContentRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class ContentApi(
    private val client: HttpClient
): ContentRepository {
    override suspend fun getContentByInterestId(interestId: Long?, cursor: String?): HttpResponse {
        return client.get(HttpRoutes.GET_CONTENT_LIST.path + "/$interestId"){
            parameter("cursor", cursor)
        }
    }

    override suspend fun getContentDetail(contentId: Long): HttpResponse {
        return client.get(HttpRoutes.GET_CONTENT_DETAIL.path + "/$contentId")
    }

    override suspend fun createContent(contentRequestDTO: ContentRequestDTO, img: List<File>): HttpResponse {
        return client.submitFormWithBinaryData(
            url = HttpRoutes.CREATE_CONTENT.path,
            formData = formData {
                img.forEach {
                    append("file", it.readBytes(), Headers.build {
                        append(HttpHeaders.ContentType, "image/*")
                        append(HttpHeaders.ContentDisposition, "filename=${it.name}")
                    })
                }
                append("data", Json.encodeToString(contentRequestDTO), Headers.build {
                    append(HttpHeaders.ContentType, "application/json")
                })
            }
        )
    }
}