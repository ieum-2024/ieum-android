package com.jeongg.ieum.presentation._navigation

sealed class Screen (val route: String) {
    data object SplashScreen: Screen("splash_screen")
    data object LoginScreen: Screen("login_screen")
    data object OnboardingInterestScreen: Screen("onboarding_interest_screen")
    data object OnboardingUserScreen: Screen("onboarding_user_screen")
    data object InterestScreen: Screen("interest_screen")
    data object ContentListScreen: Screen("content_list_screen")
    data object ContentDetailScreen: Screen("content_detail_screen")
    data object ContentAddScreen: Screen("content_add_screen")
    data object ChatListScreen: Screen("chat_list_screen")
    data object ChatDetailScreen: Screen("chat_detail_screen")
}