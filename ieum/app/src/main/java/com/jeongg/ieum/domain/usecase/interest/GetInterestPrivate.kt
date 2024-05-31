package com.jeongg.ieum.domain.usecase.interest

import com.jeongg.ieum.data.dto.common.ApiUtils
import com.jeongg.ieum.data.dto.interest.InterestAllDTO
import com.jeongg.ieum.data.dto.interest.InterestPrivateDTO
import com.jeongg.ieum.domain.repository.InterestRepository
import com.jeongg.ieum.util.Resource
import com.jeongg.ieum.util.getErrorMessage
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetInterestPrivate @Inject constructor(
    private val interestRepository: InterestRepository
){
    operator fun invoke(): Flow<Resource<List<InterestPrivateDTO>>> = flow {
        try {
            emit(Resource.Loading())
            val response = interestRepository.getInterestPrivate()
            val body = response.body<ApiUtils.ApiResult<List<InterestPrivateDTO>>>()
            val success = body.response ?: emptyList()
            val errorMessage = body.error?.message ?: "관심사 리스트 조회에 실패하였습니다. - private"

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