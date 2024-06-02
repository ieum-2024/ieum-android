package com.jeongg.ieum.presentation.chat_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jeongg.ieum.R
import com.jeongg.ieum.data.chat.Message
import com.jeongg.ieum.presentation._common.Divider
import com.jeongg.ieum.presentation._common.IeumThemeWithName
import com.jeongg.ieum.presentation._common.noRippleClickable
import com.jeongg.ieum.presentation._navigation.Screen
import com.jeongg.ieum.presentation._util.DateConverter
import com.jeongg.ieum.ui.theme.color_B1B1B1
import kotlinx.serialization.json.JsonNull.content

@Composable
fun ChatListScreen(
    navController: NavController,
    viewModel: ChatViewModel = hiltViewModel()
) {
    IeumThemeWithName (title = "채팅") {
        viewModel.chatList.forEach {
            val other = viewModel.getOther(it.chat)
            ChatItem(
                username = other,
                lastMessage = it.chat.lastMessage,
                onClick = { navController.navigate(Screen.ChatDetailScreen.route + "?chatId=${it.chatId}&nickname=$other") }
            )
        }

    }

}

@Composable
fun ChatItem(
    username: String,
    lastMessage: Message,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().noRippleClickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
       Image(
           painter = painterResource(id = R.drawable.ieum_profile),
           contentDescription = "profile image",
           modifier = Modifier
               .height(43.dp)
               .padding(end = 14.dp)
       )
        Column {
            Text(
                text = username,
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.padding(bottom = 6.dp)
            )
            Text(
                text = lastMessage.content,
                style = MaterialTheme.typography.displaySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
    Divider(modifier = Modifier.padding(vertical = 30.dp))
}
