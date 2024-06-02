package com.jeongg.ieum.presentation._navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jeongg.ieum.presentation.chat_detail.ChatDetailScreen
import com.jeongg.ieum.presentation.chat_list.ChatListScreen
import com.jeongg.ieum.presentation.content_add.ContentAddScreen
import com.jeongg.ieum.presentation.content_detail.ContentDetailScreen
import com.jeongg.ieum.presentation.content_list.ContentListScreen
import com.jeongg.ieum.presentation.interest.InterestScreen
import com.jeongg.ieum.presentation.login.LoginScreen
import com.jeongg.ieum.presentation.onboarding_interest.OnboardingInterestScreen
import com.jeongg.ieum.presentation.onboarding_user.OnboardingUserScreen
import com.jeongg.ieum.presentation.splash.SplashScreen
import kotlinx.serialization.json.JsonNull.content

fun NavGraphBuilder.ieumGraph(
    navController: NavController
){
    composable(
        route = Screen.SplashScreen.route,
        content = { SplashScreen(navController) }
    )
    composable(
        route = Screen.LoginScreen.route,
        content = { LoginScreen(navController) }
    )
    composable(
        route = Screen.OnboardingInterestScreen.route,
        content = { OnboardingInterestScreen(navController) }
    )
    composable(
        route = Screen.OnboardingUserScreen.route,
        content = { OnboardingUserScreen(navController)}
    )
    composable(
        route = Screen.ContentListScreen.route,
        content = { ContentListScreen(navController) }
    )
    composable(
        route = Screen.ContentDetailScreen.route + "?contentId={contentId}",
        content = { ContentDetailScreen(navController) },
        arguments = listOf(
            navArgument("contentId") {
                type = NavType.LongType
                defaultValue = -1L
            }
        )
    )
    composable(
        route = Screen.InterestScreen.route,
        content = { InterestScreen(navController) }
    )
    composable(
        route = Screen.ContentAddScreen.route,
        content = { ContentAddScreen(navController) }
    )
    composable(
        route = Screen.ChatListScreen.route,
        content = { ChatListScreen(navController) }
    )
    composable(
        route = Screen.ChatDetailScreen.route + "?chatId={chatId}&nickname={nickname}",
        content = { ChatDetailScreen(navController) },
        arguments = listOf(
            navArgument("chatId") {
                type = NavType.StringType
                defaultValue = ""
            },
            navArgument("nickname") {
                type = NavType.StringType
                defaultValue = ""
            }
        )
    )
}
