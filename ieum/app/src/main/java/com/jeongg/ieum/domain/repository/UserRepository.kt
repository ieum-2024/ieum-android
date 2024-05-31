package com.jeongg.ieum.domain.repository

import com.jeongg.ieum.data.dto.user.KakaoLoginRequestDTO
import com.jeongg.ieum.data.dto.user.UserInfoRequestDTO
import io.ktor.client.statement.HttpResponse

interface UserRepository {
    suspend fun kakaoLogin(kakaoToken: KakaoLoginRequestDTO): HttpResponse
    suspend fun addInfo(userInfoRequestDTO: UserInfoRequestDTO): HttpResponse
}