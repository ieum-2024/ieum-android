package com.jeongg.ieum.domain.usecase.user

import com.jeongg.ieum.data._const.DataStoreKey
import com.jeongg.ieum.data.data_store.IeumDataStore
import com.jeongg.ieum.data.dto.user.KakaoLoginRequestDTO
import com.jeongg.ieum.data.dto.user.KakaoLoginResponseDTO
import com.jeongg.ieum.domain.repository.UserRepository
import com.jeongg.ieum.util.Resource
import com.jeongg.ieum.util.getErrorMessage
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class KakaoLogin @Inject constructor(
    private val userRepository: UserRepository,
    private val dataStore: IeumDataStore
){
    operator fun invoke(kakaoToken: KakaoLoginRequestDTO): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val response = userRepository.kakaoLogin(kakaoToken)
            val body = response.body<KakaoLoginResponseDTO>()

            if (response.status == HttpStatusCode.OK) {
                dataStore.setData(DataStoreKey.ACCESS_TOKEN_KEY.name, body.accessToken)
                dataStore.setData(DataStoreKey.REFRESH_TOKEN_KEY.name, body.refreshToken)
                dataStore.setData(DataStoreKey.ID_KEY.name, body.userId.toString())
                emit(Resource.Success("카카오 로그인 성공"))
            }
            else {
                emit(Resource.Error("카카오 로그인 실패"))
            }
        } catch(error: Exception){
            emit(Resource.Error(getErrorMessage(error)))
        }
    }
}