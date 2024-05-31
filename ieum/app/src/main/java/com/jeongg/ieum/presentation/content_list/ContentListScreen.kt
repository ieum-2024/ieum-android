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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.jeongg.ieum.R
import com.jeongg.ieum.data.dto.content.Content
import com.jeongg.ieum.data.dto.interest.InterestPrivateDTO
import com.jeongg.ieum.presentation._common.CircularProgress
import com.jeongg.ieum.presentation._common.Divider
import com.jeongg.ieum.presentation._common.LaunchedEffectEvent
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
    navController: NavController,
    viewModel: ContentListViewModel = hiltViewModel()
) {
    val selectedTabIndex = rememberSaveable { mutableIntStateOf(0) }
    val contents = viewModel.state.value.contentList.collectAsLazyPagingItems()

    LaunchedEffectEvent(eventFlow = viewModel.eventFlow)
    Scaffold(
        floatingActionButton = {
            if (viewModel.isMentor()) {
                FloatingButton { navController.navigate(Screen.ContentAddScreen.route) }
            }
        }
    ) {
        Column(
            modifier = Modifier.padding(Dimens.ScreenPadding)
        ) {
            ContentListTitle()
            TabLayerContent(
                contents = contents,
                selectedTabIndex = selectedTabIndex.intValue,
                interestList = viewModel.state.value.interestList,
                onClick = { contentId -> navController.navigate(Screen.ContentDetailScreen.route + "?contentId=" + contentId) },
                onTabClick = { tabIndex, interestId ->
                    if (selectedTabIndex.intValue != tabIndex) {
                        selectedTabIndex.intValue = tabIndex
                        viewModel.getContentList(interestId)
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
    contents: LazyPagingItems<Content>,
    onTabClick: (Int, Long) -> Unit,
    interestList: List<InterestPrivateDTO>,
    selectedTabIndex: Int,
    onClick: (Long) -> Unit
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState { interestList.size }

    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            val index = pagerState.currentPage
            if (index < interestList.size)
                onTabClick(index, interestList[index].interestId)
        }
    }
    Column {
        TopInterestList(
            selectedTabIndex = selectedTabIndex,
            tabs = interestList,
            onTabClick = { index ->
                if (index < interestList.size) {
                    onTabClick(index, interestList[index].interestId)
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
            HorizontalPagerContent(contents, { onClick(it) }, page == selectedTabIndex)
        }
    }
}

@Composable
private fun HorizontalPagerContent(
    contents: LazyPagingItems<Content>,
    onClick: (Long) -> Unit,
    isSamePage: Boolean
) {
    if (!isSamePage || contents.loadState.refresh is LoadState.Loading || contents.itemCount == 0){
        CircularProgress()
        return
    }
    LazyColumn {
        items(
            count = contents.itemCount,
            key = contents.itemKey { it.contentId }
        ) { index ->
            val content = contents[index] ?: Content()
            if (content.thumbnail.isEmpty()) {
                ContentItem(
                    modifier = Modifier
                        .noRippleClickable{ onClick(content.contentId) }
                        .padding(vertical = 20.dp),
                    content = content
                )
            }
            else ContentWithImageItem(content)
            Divider()
        }
    }
}

@Composable
fun ContentItem(
    modifier: Modifier = Modifier,
    content: Content,
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = content.title,
            style = MaterialTheme.typography.headlineSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = content.description,
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.padding(top = 12.dp, bottom = 19.dp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            color = color_B1B1B1
        )
        Text(
            text = "${content.nickname} / ${content.pubDate}.",
            style = MaterialTheme.typography.labelLarge,
            maxLines = 1,
            color = color_B1B1B1
        )
    }
}

@Composable
fun ContentWithImageItem(
    content: Content,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .noRippleClickable(onClick = onClick)
            .padding(vertical = 20.dp)
            .fillMaxWidth()
    ) {
        ContentItem(
            modifier = Modifier.padding(end = 100.dp),
            content = content
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
    tabs: List<InterestPrivateDTO>,
    onTabClick: (Int) -> Unit
) {
    if (tabs.isNotEmpty()) {
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
                    text = { TabText(value.name, selected) },
                    interactionSource = NoRippleInteractionSource,
                    selectedContentColor = main_orange,
                    unselectedContentColor = color_C2C2C2
                )
            }
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
