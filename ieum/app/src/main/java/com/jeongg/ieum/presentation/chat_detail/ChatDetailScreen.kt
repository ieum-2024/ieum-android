package com.jeongg.ieum.presentation.chat_detail

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jeongg.ieum.R
import com.jeongg.ieum.data.chat.Message
import com.jeongg.ieum.presentation._common.IeumBasicTextField
import com.jeongg.ieum.presentation._common.addFocusCleaner
import com.jeongg.ieum.presentation._common.noRippleClickable
import com.jeongg.ieum.ui.theme.main_orange
import kotlinx.coroutines.launch

@Composable
fun ChatDetailScreen(
    navController: NavController,
    viewModel: ChatDetailViewModel = hiltViewModel()
) {
    val focusManager = LocalFocusManager.current
    Scaffold(
        modifier = Modifier.addFocusCleaner(focusManager),
        topBar = { Title(viewModel.nickname.value) },
        bottomBar = { GetMessage { viewModel.send(it) } },
    ) {
        MessageList(it, viewModel.messages, viewModel.senderId.longValue, viewModel.isAdd.value) {
            viewModel.chageAddState()
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MessageList(
    padding: PaddingValues,
    message: List<Message>, 
    senderId: Long, 
    isAdd: Boolean, 
    onClick: () -> Unit
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    if (message.isNotEmpty() && isAdd){
        coroutineScope.launch {
            listState.scrollToItem(message.size)
            onClick()
        }
    }

    LazyColumn(
        modifier = Modifier.padding(padding).fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        state = listState
    ) {
        items(message) {m ->
            if (m.senderId == senderId.toString()) MyChatItem(m.content)
            else OtherChatItem(m.content)
        }
    }
}

@Composable
fun OtherChatItem(text: String) {
    val clip = RoundedCornerShape(0.dp, 20.dp, 20.dp, 20.dp)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(PaddingValues(start = 20.dp, end = 70.dp))
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier
                .shadow(1.5.dp, shape = clip)
                .align(Alignment.CenterStart)
                .clip(RoundedCornerShape(0.dp, 20.dp, 20.dp, 20.dp))
                .background(Color.White, clip)
                .border(BorderStroke(1.dp, Color(0xFFFFD19B)), clip)
                .padding(15.dp)
        )
    }
}

@Composable
fun MyChatItem(text: String) {
    val clip = RoundedCornerShape(20.dp, 0.dp, 20.dp, 20.dp)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(PaddingValues(start = 70.dp, end = 20.dp))
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
            color = Color.White,
            modifier = Modifier
                .shadow(2.dp, shape = clip)
                .align(Alignment.CenterEnd)
                .clip(clip)
                .background(main_orange, clip)
                .border(BorderStroke(0.dp, Color(0xFFFFD19B)), clip)
                .padding(15.dp)
        )
    }
}

@Composable
fun GetMessage(onClick: (String) -> Unit) {
    val text = remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .background(main_orange.copy(0.1f))
            .padding(vertical = 5.dp, horizontal = 20.dp)
            .wrapContentHeight()
    ) {
        IeumBasicTextField(
            modifier = Modifier.padding(end = 50.dp),
            text = text.value,
            onValueChange = { text.value = it },
            placeholder = "메시지를 입력해주세요.",
        )
        Image(
            painter = painterResource(id = R.drawable.send),
            contentDescription = "send",
            modifier = Modifier
                .height(25.dp)
                .noRippleClickable{
                    onClick(text.value)
                    text.value = ""
                }
                .align(Alignment.CenterEnd)
        )
    }
}

@Composable
private fun Title(title: String) {
    Box(
        modifier = Modifier
            .height(60.dp)
            .background(main_orange)
            .padding(start = 26.dp)
            .fillMaxWidth()
    ){
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.CenterStart),
            color = Color.White,
        )
    }

}
