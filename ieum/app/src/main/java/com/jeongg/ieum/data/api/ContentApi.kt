package com.jeongg.ieum.data.api

import com.jeongg.ieum.data._const.HttpRoutes
import com.jeongg.ieum.data.dto.content.ContentRequestDTO
import com.jeongg.ieum.domain.repository.ContentRepository
import com.jeongg.ieum.util.log
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.util.InternalAPI
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
        return client.post(HttpRoutes.CREATE_CONTENT.path) {
            setBody(
                MultiPartFormDataContent(
                    formData {
                        img.forEach {
                            append("image", it.readBytes(), Headers.build {
                                append(HttpHeaders.ContentType, "image/png")
                                append(HttpHeaders.ContentDisposition, "filename=${it.absolutePath}")
                            })
                        }
                        append("data", Json.encodeToString(contentRequestDTO), Headers.build {
                            append(HttpHeaders.ContentType, "application/json")
                        })
                    },
                    boundary = "WebAppBoundary"
                )
            )
        }
    }
}