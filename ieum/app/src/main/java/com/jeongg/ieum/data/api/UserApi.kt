package com.jeongg.ieum.data.api

import com.jeongg.ieum.data._const.HttpRoutes
import com.jeongg.ieum.data.dto.user.KakaoLoginRequestDTO
import com.jeongg.ieum.data.dto.user.UserInfoRequestDTO
import com.jeongg.ieum.domain.repository.UserRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse

class UserApi (
    private val client: HttpClient
): UserRepository {

    override suspend fun kakaoLogin(kakaoToken: KakaoLoginRequestDTO): HttpResponse {
        return client.post(HttpRoutes.KAKAO_LOGIN.path){
            setBody(kakaoToken)
        }
    }

    override suspend fun addInfo(userInfoRequestDTO: UserInfoRequestDTO): HttpResponse {
        return client.post(HttpRoutes.ADD_USER_INFO.path){
            setBody(userInfoRequestDTO)
        }
    }

}
