package com.jeongg.ieum.presentation._navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jeongg.ieum.presentation.splash.SplashScreen

fun NavGraphBuilder.ieumGraph(
    navController: NavController
){
    composable(
        route = Screen.SplashScreen.route,
        content = { SplashScreen(navController) }
    )

}
