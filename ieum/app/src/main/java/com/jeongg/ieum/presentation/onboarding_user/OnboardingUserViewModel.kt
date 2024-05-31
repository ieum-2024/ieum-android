package com.jeongg.ieum.presentation.onboarding_user

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeongg.ieum.data._const.DataStoreKey
import com.jeongg.ieum.data.data_store.IeumDataStore
import com.jeongg.ieum.data.dto.user.UserInfoRequestDTO
import com.jeongg.ieum.domain.usecase.user.AddInfo
import com.jeongg.ieum.presentation._util.IeumEvent
import com.jeongg.ieum.util.Resource
import com.jeongg.ieum.util.log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingUserViewModel @Inject constructor(
    private val dataStore: IeumDataStore,
    private val addUserUseCase: AddInfo
): ViewModel() {

    private val _userInfo = mutableStateOf(UserInfoRequestDTO(-1, "", ""))
    val userInfo = _userInfo

    private val _eventFlow = MutableSharedFlow<IeumEvent>()
    val eventFlow = _eventFlow

    fun onEvent(event: OnboardingUserEvent){
        when (event){
            is OnboardingUserEvent.EnterNickname -> {
                _userInfo.value = _userInfo.value.copy(
                    nickname = event.nickname
                )
            }
            is OnboardingUserEvent.EnterRole -> {
                _userInfo.value = _userInfo.value.copy(
                    role = event.role.eng
                )
            }
            is OnboardingUserEvent.SaveUser -> {
                saveUserId()
                saveUserInfoInDataStore()
                sendUserInfoToServer()
            }
        }
    }

    private fun saveUserId() {
        val userId = dataStore.getData(DataStoreKey.ID_KEY.name).toInt()
        _userInfo.value = _userInfo.value.copy(
            id = userId
        )
    }

    private fun saveUserInfoInDataStore() {
        val nickname = userInfo.value.nickname
        val role = userInfo.value.role
        dataStore.setData(DataStoreKey.NICKNAME_KEY.name, nickname)
        dataStore.setData(DataStoreKey.ROLE_KEY.name, role)
    }

    private fun sendUserInfoToServer() {
        viewModelScope.launch {
            if (isInvalid()){
                _eventFlow.emit(IeumEvent.MakeToast("닉네임은 1~10자여야 하고\n역할은 비어있으면 안됩니다."))
                return@launch
            }
            addUserUseCase(userInfo.value).collect {response ->
                when(response){
                    is Resource.Loading -> _eventFlow.emit(IeumEvent.Loading)
                    is Resource.Success -> _eventFlow.emit(IeumEvent.Navigate)
                    is Resource.Error -> _eventFlow.emit(IeumEvent.MakeToast(response.message))
                }
            }
        }
    }

    private fun isInvalid() = userInfo.value.nickname.isEmpty() || userInfo.value.role.isEmpty()
            || userInfo.value.nickname.length > 10
}