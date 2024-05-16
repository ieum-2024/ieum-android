package com.jeongg.ieum.presentation._navigation

import androidx.annotation.DrawableRes
import com.jeongg.ieum.R

sealed class BottomNavItem(
    val title: String,
    @DrawableRes val icon: Int,
    val screenList: List<Screen>
) {
    object Home: BottomNavItem(
        title = "홈",
        icon = R.drawable.home_black,
        screenList = listOf(
            Screen.ContentListScreen,
            Screen.ContentAddScreen,
            Screen.ContentDetailScreen
        )
    )

    object Interest: BottomNavItem(
        title = "관심사",
        icon = R.drawable.heart_black,
        screenList = listOf(Screen.InterestScreen))

    object Chatting: BottomNavItem(
        title = "채팅",
        icon = R.drawable.chat_black,
        screenList = listOf(
            Screen.ChattingListScreen,
            Screen.ChattingDetailScreen,
        )
    )

}
