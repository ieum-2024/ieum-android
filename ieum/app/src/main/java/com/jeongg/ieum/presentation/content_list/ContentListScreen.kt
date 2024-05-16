package com.jeongg.ieum.presentation.content_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jeongg.ieum.R
import com.jeongg.ieum.presentation._common.Divider
import com.jeongg.ieum.presentation._common.noRippleClickable
import com.jeongg.ieum.presentation._navigation.Screen
import com.jeongg.ieum.presentation._util.NoRippleInteractionSource
import com.jeongg.ieum.ui.theme.Dimens
import com.jeongg.ieum.ui.theme.color_B1B1B1
import com.jeongg.ieum.ui.theme.color_C2C2C2
import com.jeongg.ieum.ui.theme.main_orange
import kotlinx.coroutines.launch

@Composable
fun ContentListScreen(
    navController: NavController
) {
    val selectedTabIndex = rememberSaveable { mutableIntStateOf(0) }
    Scaffold(
        floatingActionButton = {
            FloatingButton{ navController.navigate(Screen.ContentAddScreen.route) }
        }
    ) {
        Column(
            modifier = Modifier.padding(Dimens.ScreenPadding)
        ) {
            ContentListTitle()
            TabLayerContent(
                selectedTabIndex = selectedTabIndex.intValue,
                interestList = listOf("전세/사기", "이혼", "결혼", "취업", "취업사기", "행복한 우리집"),
                onClick = { navController.navigate(Screen.ContentDetailScreen.route) },
                onTabClick = { tabIndex ->
                    if (selectedTabIndex.intValue != tabIndex) {
                        selectedTabIndex.intValue = tabIndex
                    }
                }
            )
        }
        it
    }
}

@Composable
fun FloatingButton(
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(9.dp)
            .size(56.dp)
            .shadow(3.dp, shape = CircleShape)
            .clip(CircleShape),
        colors = ButtonDefaults.buttonColors(main_orange),
        contentPadding = PaddingValues(0.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.add),
            contentDescription = "add contents",
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun ContentListTitle() {
    Row(
        modifier = Modifier.height(28.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.ieum_kor),
            contentDescription = "ieum kor",
            modifier = Modifier.padding(3.dp)
        )
        Image(
            painter = painterResource(R.drawable.ieum_icon),
            contentDescription = "ieum icon",
        )
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun TabLayerContent(
    onTabClick: (Int) -> Unit,
    interestList: List<String>,
    selectedTabIndex: Int,
    onClick: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState { interestList.size }

    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            val index = pagerState.currentPage
            if (index < interestList.size)
                onTabClick(index)
        }
    }
    Column {
        TopInterestList(
            selectedTabIndex = selectedTabIndex,
            tabs = interestList,
            onTabClick = { index ->
                if (index < interestList.size) {
                    onTabClick(index)
                    scope.launch { pagerState.scrollToPage(index) }
                }
            }
        )
        HorizontalPager(
            state = pagerState,
            pageSpacing = 15.dp,
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.Top
        ) { page ->
            HorizontalPagerContent(onClick)
        }
    }
}

@Composable
private fun HorizontalPagerContent(
    onClick: () -> Unit
) {
    LazyColumn {
        repeat(10) {
            item {
                ContentItem(
                    Modifier
                        .noRippleClickable(onClick)
                        .padding(vertical = 20.dp))
            }
            item {
                Divider()
            }
            item {
                ContentWithImageItem(onClick)
            }
            item {
                Divider()
            }
        }
    }
}

@Composable
fun ContentItem(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "멘토를 찾습니다!멘토를 찾습니다!멘토를 찾습니다!멘토를 찾습니다!멘토를 찾습니다!멘토를 찾습니다!멘토를 찾습니다!",
            style = MaterialTheme.typography.headlineSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = "벽지 시공에 능숙하신 멘토 분을 찾습니다!!지 시공에 능숙하신 멘토 분을 찾습니다!!지 시공에 능숙하신 멘토 분을 찾습니다!!지 시공에 능숙하신 멘토 분을 찾습니다!!지 시공에 능숙하신 멘토 분을 찾습니다!!지 시공에 능숙하신 멘토 분을 찾습니다!! ",
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.padding(top = 12.dp, bottom = 19.dp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            color = color_B1B1B1
        )
        Text(
            text = "남원정 / 2023. 06. 29.",
            style = MaterialTheme.typography.labelLarge,
            maxLines = 1,
            color = color_B1B1B1
        )
    }
}

@Composable
fun ContentWithImageItem(
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .noRippleClickable(onClick = onClick)
            .padding(vertical = 20.dp)
            .fillMaxWidth()
    ) {
        ContentItem(
            modifier = Modifier.padding(end = 100.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.rec),
            contentDescription = "image",
            modifier = Modifier
                .clip(MaterialTheme.shapes.large)
                .size(83.dp)
                .align(Alignment.TopEnd),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun TopInterestList(
    selectedTabIndex: Int,
    tabs: List<String>,
    onTabClick: (Int) -> Unit
) {
    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        contentColor = Color.White,
        edgePadding = 0.dp,
        containerColor = Color.White,
        indicator = { TabLayerIndicator(it, selectedTabIndex) },
        divider = { Divider() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        tabs.forEachIndexed { index, value ->
            val selected = selectedTabIndex == index
            Tab(
                selected = selected,
                onClick = { onTabClick(index) },
                text = { TabText(value, selected) },
                interactionSource = NoRippleInteractionSource,
                selectedContentColor = main_orange,
                unselectedContentColor = color_C2C2C2
            )
        }
    }
}

@Composable
private fun TabText(text: String, selected: Boolean) {
    Text(
        text = text,
        style = if (selected) MaterialTheme.typography.titleSmall
                else MaterialTheme.typography.bodyLarge,
    )
}

@Composable
private fun TabLayerIndicator(
    tabPositionList: List<TabPosition>,
    selectedTabIndex: Int
) {
    TabRowDefaults.SecondaryIndicator(
        modifier = Modifier.tabIndicatorOffset(
            currentTabPosition = tabPositionList[selectedTabIndex]
        ),
        color = main_orange
    )
}
