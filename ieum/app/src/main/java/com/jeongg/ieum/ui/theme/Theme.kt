package com.jeongg.ieum.ui.theme

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.jeongg.ieum.presentation._navigation.Screen
import com.jeongg.ieum.presentation._navigation.ieumGraph

@Composable
fun IeumTheme(
    navController: NavHostController
) {
    val colorScheme = lightColorScheme(
        background = Color.White,
        surface = Color.Black
    )
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = shapes,
        content = { IeumContents(navController) }
    )
}

@Composable
fun IeumContents(
    navController: NavHostController
) {
    Scaffold(
        bottomBar = { /* todo: bottom navigation bar */ },
    ) {
        Surface(
            modifier = Modifier.fillMaxSize().padding(it),
            color = Color.White
        ) {
            NavHost(
                navController = navController,
                startDestination = Screen.LoginScreen.route,
                route = "ieum_route",
                builder = { ieumGraph(navController) }
            )
        }
    }
}