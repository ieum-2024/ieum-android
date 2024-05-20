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
    LazyColumn {
        item { ContentImageList() }
        item {
            Column(
                modifier = Modifier.padding(Dimens.NormalPadding)
            ){
                ContentProfile()
                ContentTitle()
                ContentDescription()
                ContentStartButton{ viewModel.contactMentee() }
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
fun ContentDescription() {
    Text(
        text = "집 구하면서 나도 전세 사기 당하진 않을까 걱정한 " +
                "사람들은 이것만 명심하자.안녕하세요? 전직 공인중개사 시니어 멘토 윤현주" +
                "입니다. 집 구하실 때 고민이 많으셨을거에요. 20년 " +
                "동안 공인중개사에 몸을 담그며 얻은 노하우를 여러" +
                "분께 전수해드리려고 합니다. " +
                "실제로 집을 찾고 계신 분들도 환영이에요^^",
        style = MaterialTheme.typography.headlineSmall
    )

}

@Composable
fun ContentTitle() {
    Column {
        Text(
            text = "전세 사기 피하는 팁",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = "2023. 06. 29. THU",
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
