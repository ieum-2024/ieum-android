package com.jeongg.ieum.domain.usecase.interest

import com.jeongg.ieum.data.dto.common.ApiUtils
import com.jeongg.ieum.domain.repository.InterestRepository
import com.jeongg.ieum.util.Resource
import com.jeongg.ieum.util.getErrorMessage
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateInterest @Inject constructor(
    private val interestRepository: InterestRepository
){
    operator fun invoke(interestId: Long): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val response = interestRepository.createInterest(interestId)
            val body = response.body<ApiUtils.ApiResult<String>>()
            val successMessage = body.response ?: "성공적으로 관심사를 삭제했습니다."
            val errorMessage = body.error?.message ?: "관심사 삭제에 실패하였습니다."

            if (response.status == HttpStatusCode.OK && body.success) {
                emit(Resource.Success(successMessage))
            }
            else {
                emit(Resource.Error(errorMessage))
            }
        } catch(error: Exception){
            emit(Resource.Error(getErrorMessage(error)))
        }
    }
}