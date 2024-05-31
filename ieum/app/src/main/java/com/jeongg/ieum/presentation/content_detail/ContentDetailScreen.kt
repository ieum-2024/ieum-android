package com.jeongg.ieum.presentation.content_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.jeongg.ieum.R
import com.jeongg.ieum.presentation._common.CoilImage
import com.jeongg.ieum.presentation._common.LongButton
import com.jeongg.ieum.ui.theme.Dimens
import com.jeongg.ieum.ui.theme.color_C2C2C2

@Composable
fun ContentDetailScreen(
    navController: NavController,
    viewModel: ContentDetailViewModel = hiltViewModel()
) {
    val content = viewModel.content.value
    LazyColumn {
        item {
            if (content.images.isNotEmpty()) {
                ContentImageList(content.images)
            }
        }
        item {
            Column(
                modifier = Modifier.padding(Dimens.NormalPadding)
            ){
                ContentProfile()
                ContentTitle(content.title, content.pubDate)
                ContentDescription(content.description)
                if (viewModel.isMentor()) {
                    ContentStartButton { viewModel.contactMentee() }
                }
            }
        }
    }
}


@Composable
fun ContentStartButton(onClick: () -> Unit) {
    Spacer(modifier = Modifier.height(60.dp))
    LongButton(text = "멘토링 시작하기", onClick, true)
}

@Composable
fun ContentDescription(
    description: String
) {
    Text(
        text = description,
        style = MaterialTheme.typography.headlineSmall
    )
}

@Composable
fun ContentTitle(
    title: String,
    pubDate: String
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = pubDate,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(top = 5.dp, bottom = 21.dp),
            color = color_C2C2C2
        )
    }
}

@Composable
fun ContentProfile() {
    Row(
        modifier = Modifier.padding(bottom = 22.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            painter = painterResource(R.drawable.ieum_profile),
            contentDescription = "profile",
            modifier = Modifier.size(38.dp)
        )
        Text(
            text = "원정",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(start = 14.dp),
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ContentImageList(
    items: List<String> = (0 until 5).map { "https://url.kr/y6q97h" }
) {
    val pagerState = rememberPagerState()
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopEnd
    ){
        HorizontalPager(count = items.size, state = pagerState) { index ->
            CoilImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp),
                imageUrl = items[index]
            )
        }
        if (items.size >= 2) {
            HorizontalPagerIndicator(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp),
                pagerState = pagerState,
                pageIndexMapping = { it },
                pageCount = items.size,
                spacing = 10.dp,
                indicatorHeight = 8.dp,
                indicatorWidth = 8.dp,
                indicatorShape = CircleShape,
                activeColor = Color.White,
            )
        }
    }
}
