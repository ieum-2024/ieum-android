package com.jeongg.ieum.presentation._navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.jeongg.ieum.presentation._common.Divider
import com.jeongg.ieum.presentation._util.NoRippleInteractionSource
import com.jeongg.ieum.ui.theme.color_ebebeb

@Composable
fun BottomNavigationBar(
    navController: NavController
){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val screens = listOf(BottomNavItem.Home, BottomNavItem.Interest, BottomNavItem.Chatting)

    if (shouldShowBottomNavigation(navController)) {
        Column {
            Divider()
            Row(
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .height(64.dp)
                    .fillMaxWidth()
                    .selectableGroup(),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                screens.forEach { screen ->
                    AddItem(
                        bottomNavItem = screen,
                        currentDestination = currentDestination,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
private fun shouldShowBottomNavigation(
    navController: NavController
): Boolean {
    val shouldNotShown = listOf(Screen.LoginScreen.route, Screen.SplashScreen.route,
            Screen.OnboardingInterestScreen.route, Screen.OnboardingUserScreen.route)
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    return currentRoute !in shouldNotShown
}

@Composable
private fun RowScope.AddItem(
    bottomNavItem: BottomNavItem,
    currentDestination: NavDestination?,
    navController: NavController
){
    NavigationBarItem (
        icon = { NavigationItem(bottomNavItem) },
        selected = isSelected(currentDestination, bottomNavItem),
        onClick = {
            navController.navigate(bottomNavItem.screenList.first().route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = false
                }
                launchSingleTop = true
                restoreState = true
            }
        },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = Color.Black,
            unselectedIconColor = color_ebebeb,
            indicatorColor = Color.White,
        ),
        interactionSource = NoRippleInteractionSource
    )
}

@Composable
private fun isSelected(
    currentDestination: NavDestination?,
    bottomNavItem: BottomNavItem
): Boolean {
    return currentDestination?.hierarchy?.any { navDestination ->
        val route = navDestination.route?.split("?")?.get(0) ?: ""
        bottomNavItem.screenList.map { screen -> screen.route }.contains(route)
    } == true
}

@Composable
private fun NavigationItem(bottomNavItem: BottomNavItem) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(bottomNavItem.icon),
            contentDescription = bottomNavItem.title,
            modifier = Modifier.size(30.dp)
        )
        Text(
            text = bottomNavItem.title,
            style = MaterialTheme.typography.labelMedium
        )
    }
}
