package com.jeongg.ieum.domain.usecase.content

import com.jeongg.ieum.data.dto.common.ApiUtils
import com.jeongg.ieum.data.dto.content.ContentRequestDTO
import com.jeongg.ieum.domain.repository.ContentRepository
import com.jeongg.ieum.util.Resource
import com.jeongg.ieum.util.getErrorMessage
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import javax.inject.Inject

class CreateContent  @Inject constructor(
    private val contentRepository: ContentRepository
){
    operator fun invoke(contentRequestDTO: ContentRequestDTO, img: List<File>): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val response = contentRepository.createContent(contentRequestDTO, img)
            val body = response.body<ApiUtils.ApiResult<String?>>()
            val success = body.response ?: ""
            val errorMessage = body.error?.message ?: "구독 비활성화/활성화에 실패하였습니다."

            if (response.status == HttpStatusCode.OK && body.success) {
                emit(Resource.Success(success))
            }
            else {
                emit(Resource.Error(errorMessage))
            }
        } catch(error: Exception){
            emit(Resource.Error(getErrorMessage(error)))
        }
    }
}