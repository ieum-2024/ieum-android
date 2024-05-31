package com.jeongg.ieum.data.repository

import com.jeongg.ieum.data.api.UserApi
import com.jeongg.ieum.data.dto.user.KakaoLoginRequestDTO
import com.jeongg.ieum.data.dto.user.UserInfoRequestDTO
import com.jeongg.ieum.domain.repository.UserRepository
import io.ktor.client.statement.HttpResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi,
): UserRepository {
    override suspend fun kakaoLogin(kakaoToken: KakaoLoginRequestDTO): HttpResponse {
        return userApi.kakaoLogin(kakaoToken)
    }

    override suspend fun addInfo(userInfoRequestDTO: UserInfoRequestDTO): HttpResponse {
        return userApi.addInfo(userInfoRequestDTO)
    }

}