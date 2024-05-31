package com.jeongg.ieum.domain.usecase.user

import com.jeongg.ieum.data._const.DataStoreKey
import com.jeongg.ieum.data.data_store.IeumDataStore
import com.jeongg.ieum.data.dto.common.ApiUtils
import com.jeongg.ieum.data.dto.user.UserInfoRequestDTO
import com.jeongg.ieum.domain.repository.UserRepository
import com.jeongg.ieum.util.Resource
import com.jeongg.ieum.util.getErrorMessage
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddInfo @Inject constructor(
    private val userRepository: UserRepository,
){
    operator fun invoke(userInfoRequestDTO: UserInfoRequestDTO): Flow<Resource<String?>> = flow {
        try {
            emit(Resource.Loading())
            val response = userRepository.addInfo(userInfoRequestDTO)
            val body = response.body<ApiUtils.ApiResult<String?>>()
            val success = body.response ?: ""
            val errorMessage = body.error?.message ?: "유저 정보 추가에 실패하였습니다."

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