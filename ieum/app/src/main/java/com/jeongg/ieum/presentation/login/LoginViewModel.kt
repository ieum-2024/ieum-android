package com.jeongg.ieum.presentation.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeongg.ieum.data.dto.user.KakaoLoginRequestDTO
import com.jeongg.ieum.domain.usecase.user.KakaoLogin
import com.jeongg.ieum.presentation._util.IeumEvent
import com.jeongg.ieum.util.Resource
import com.jeongg.ieum.util.log
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val kakaoLoginUseCase: KakaoLogin,
): ViewModel() {

    private val _eventFlow = MutableSharedFlow<IeumEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun kakaoLogin(context: Context) {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error == null && token != null) {
                kakaoToServer(token.accessToken)
            }
        }
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }
                    UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                }
                else if (token != null) {
                    kakaoToServer(token.accessToken)
                }
            }
        }
        else UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
    }

    private fun kakaoToServer(accessToken: String) {
        viewModelScope.launch {
            kakaoLoginUseCase(KakaoLoginRequestDTO(accessToken)).collect { response ->
                when(response){
                    is Resource.Loading -> _eventFlow.emit(IeumEvent.Loading)
                    is Resource.Success -> _eventFlow.emit(IeumEvent.Navigate)
                    is Resource.Error -> _eventFlow.emit(IeumEvent.MakeToast(response.message))
                }
            }
        }
    }
}
