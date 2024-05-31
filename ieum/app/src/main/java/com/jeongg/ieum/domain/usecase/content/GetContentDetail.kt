package com.jeongg.ieum.domain.usecase.content

import com.jeongg.ieum.data.dto.common.ApiUtils
import com.jeongg.ieum.data.dto.content.ContentDetailResponseDTO
import com.jeongg.ieum.domain.repository.ContentRepository
import com.jeongg.ieum.util.Resource
import com.jeongg.ieum.util.getErrorMessage
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetContentDetail @Inject constructor(
    private val contentRepository: ContentRepository
){
    operator fun invoke(contentId: Long): Flow<Resource<ContentDetailResponseDTO>> = flow {
        try {
            emit(Resource.Loading())
            val response = contentRepository.getContentDetail(contentId)
            val body = response.body<ApiUtils.ApiResult<ContentDetailResponseDTO>>()
            val success = body.response ?: throw IllegalArgumentException()
            val errorMessage = body.error?.message ?: "세기슬 상세 조회에 실패하였습니다."

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