package com.jeongg.ieum.di

import android.content.Context
import android.util.Log
import com.jeongg.ieum.data._const.DataStoreKey
import com.jeongg.ieum.data._const.HttpRoutes
import com.jeongg.ieum.data.api.ContentApi
import com.jeongg.ieum.data.api.InterestApi
import com.jeongg.ieum.data.api.UserApi
import com.jeongg.ieum.data.data_store.IeumDataStore
import com.jeongg.ieum.data.dto.common.ApiUtils
import com.jeongg.ieum.data.dto.user.RefreshRequestDTO
import com.jeongg.ieum.data.dto.user.RefreshResponseDTO
import com.jeongg.ieum.data.repository.ContentRepositoryImpl
import com.jeongg.ieum.data.repository.InterestRepositoryImpl
import com.jeongg.ieum.data.repository.UserRepositoryImpl
import com.jeongg.ieum.domain.repository.ContentRepository
import com.jeongg.ieum.domain.repository.InterestRepository
import com.jeongg.ieum.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.RefreshTokensParams
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.compression.ContentEncoding
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.appendIfNameAbsent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    private val accessKey = DataStoreKey.ACCESS_TOKEN_KEY.name
    private val refreshKey = DataStoreKey.REFRESH_TOKEN_KEY.name

    @Provides
    @Singleton
    fun provideHttpClient(
        @ApplicationContext context: Context
    ): HttpClient {
        return HttpClient(CIO) {
            install(Logging) {
                logger = object: Logger {
                    override fun log(message: String) {
                        Log.d("ieum_api", message)
                    }
                }
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json(Json{
                    prettyPrint = true
                    isLenient = true
                    encodeDefaults = true
                })
            }
            install(HttpTimeout) {
                connectTimeoutMillis = 8000
                requestTimeoutMillis = 8000
                socketTimeoutMillis = 8000
            }
            install(Auth) {
                bearer {
                    refreshTokens {
                        getRefreshToken(context)
                    }
                }
            }
            install(ContentEncoding) {
                deflate(1.0F)
                gzip(0.9F)
            }
            defaultRequest{
                contentType(ContentType.Application.Json)
                url {
                    protocol = URLProtocol.HTTP
                    host = HttpRoutes.BASE_URL.path
                    port = 8080
                }

                val accessToken = "Bearer " + IeumDataStore(context).getData(accessKey)
                if (accessToken.isNotEmpty()) {
                    headers.appendIfNameAbsent(HttpHeaders.Authorization, accessToken)
                }
            }
        }
    }

    private suspend fun RefreshTokensParams.getRefreshToken(context: Context): BearerTokens? {
        val refreshToken = IeumDataStore(context)
            .getData(refreshKey)
            .ifEmpty{ return null }

        val apiResult = client
            .post(HttpRoutes.REFRESH_TOKEN.path) {
                setBody(RefreshRequestDTO(refreshToken))
                markAsRefreshTokenRequest()
            }.body<ApiUtils.ApiResult<RefreshResponseDTO>>()

        val newAccessToken = apiResult.response?.accessToken ?: ""
        val newRefreshToken = apiResult.response?.refreshToken ?: ""

        if (apiResult.success) {
            IeumDataStore(context).setData(accessKey, newAccessToken)
            IeumDataStore(context).setData(refreshKey, newRefreshToken)
        }
        return BearerTokens(
            accessToken = newAccessToken.parseToken(),
            refreshToken = newRefreshToken.parseToken()
        )
    }

    private fun String.parseToken(): String {
        if (this.startsWith("Bearer ")) {
            return this.substring(7)
        }
        return this
    }

    @Provides
    @Singleton
    fun provideUserApi(client: HttpClient): UserRepository {
        return UserApi(client)
    }
    @Provides
    @Singleton
    fun provideUserRepositoryImpl(userApi: UserApi): UserRepositoryImpl {
        return UserRepositoryImpl(userApi)
    }
    @Provides
    @Singleton
    fun provideInterestApi(client: HttpClient): InterestRepository {
        return InterestApi(client)
    }
    @Provides
    @Singleton
    fun provideInterestRepositoryImpl(interestApi: InterestApi): InterestRepositoryImpl {
        return InterestRepositoryImpl(interestApi)
    }
    @Provides
    @Singleton
    fun provideContentApi(client: HttpClient): ContentRepository {
        return ContentApi(client)
    }
    @Provides
    @Singleton
    fun provideContentApiRepositoryImpl(contentApi: ContentApi): ContentRepositoryImpl {
        return ContentRepositoryImpl(contentApi)
    }

}
