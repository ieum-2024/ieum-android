package com.jeongg.ieum.presentation._navigation

sealed class Screen (val route: String) {
    data object SplashScreen: Screen("splash_screen")
    data object LoginScreen: Screen("login_screen")
    data object SignupScreen: Screen("sign_up_screen")
    data object InterestScreen: Screen("interest_screen")
    data object ContentListScreen: Screen("content_list_screen")
    data object ContentDetailScreen: Screen("content_detail_screen")
    data object ContentAddScreen: Screen("content_add_screen")
    data object ChattingListScreen: Screen("chatting_list_screen")
    data object ChattingDetailScreen: Screen("chatting_detail_screen")
}