package com.jeongg.ieum.presentation.onboarding_user

import com.jeongg.ieum.data._const.Role

sealed class OnboardingUserEvent {
    data class EnterNickname(val nickname: String): OnboardingUserEvent()
    data class EnterRole(val role: Role): OnboardingUserEvent()
    data object SaveUser: OnboardingUserEvent()
}