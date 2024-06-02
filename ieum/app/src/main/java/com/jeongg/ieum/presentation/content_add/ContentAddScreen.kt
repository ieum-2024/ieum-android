package com.jeongg.ieum.presentation.content_add

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.jeongg.ieum.R
import com.jeongg.ieum.data.dto.interest.InterestAllDTO
import com.jeongg.ieum.presentation._common.Divider
import com.jeongg.ieum.presentation._common.IeumBasicTextField
import com.jeongg.ieum.presentation._common.IeumThemeWithName
import com.jeongg.ieum.presentation._common.LaunchedEffectEvent
import com.jeongg.ieum.presentation._common.LongButton
import com.jeongg.ieum.presentation._common.addFocusCleaner
import com.jeongg.ieum.presentation._common.noRippleClickable
import com.jeongg.ieum.presentation._navigation.Screen
import com.jeongg.ieum.presentation._util.FileUtil
import com.jeongg.ieum.ui.theme.color_808080
import com.jeongg.ieum.ui.theme.color_ebebeb
import java.io.File


@Composable
fun ContentAddScreen(
    navController: NavController,
    viewModel: ContentAddViewModel = hiltViewModel()
) {
    val focusManager = LocalFocusManager.current
    LaunchedEffectEvent(eventFlow = viewModel.eventFlow) {
        navController.popBackStack()
        navController.navigate(Screen.ContentListScreen.route)
    }
    Scaffold(
        floatingActionButton = {
            AddButton { viewModel.onEvent(ContentAddEvent.SaveContent) }
        }
    ) {
        IeumThemeWithName(
            modifier = Modifier
                .padding(it)
                .addFocusCleaner(focusManager),
            title = "멘토 구하기"
        ) {
            TitleField(
                text = viewModel.content.value.title,
                onValueChange = { viewModel.onEvent(ContentAddEvent.EnterTitle(it)) }
            )
            CategoryField(
                interestList = viewModel.interestList.value,
                onValueChange = { viewModel.onEvent(ContentAddEvent.EnterInterest(it)) }
            )
            DescriptionField(
                text = viewModel.content.value.description,
                onValueChange = { viewModel.onEvent(ContentAddEvent.EnterDescription(it)) }
            )
            CameraField { viewModel.onEvent(ContentAddEvent.EnterImage(it)) }
        }
    }

}

@Composable
fun TitleField(
    text: String,
    onValueChange: (String) -> Unit = {}
) {
    IeumBasicTextField(
        text = text,
        onValueChange = onValueChange,
        placeholder = "제목"
    )
    Divider()
}

@Composable
fun CategoryField(
    interestList: List<InterestAllDTO>,
    onValueChange: (Long) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("카테고리 선택") }
    Column(
        modifier = Modifier
            .padding(vertical = 20.dp)
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd),
        horizontalAlignment = Alignment.End
    ){
        Row(
            modifier = Modifier
                .border(BorderStroke(0.5.dp, color_808080), RoundedCornerShape(32.dp))
                .noRippleClickable { expanded = expanded.not() }
                .padding(vertical = 8.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectedItem,
                style = MaterialTheme.typography.headlineSmall,
                color = color_808080
            )
            Image(
                painter = painterResource(id = R.drawable.triangle),
                contentDescription = "expand drop box",
                modifier = Modifier
                    .padding(start = 10.dp)
                    .height(7.dp)
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            interestList.forEach {
                DropdownMenuItem(
                    onClick = {
                        selectedItem =  it.name
                        expanded = expanded.not()
                        onValueChange(it.interestId)
                }) {
                    Text(
                        text = it.name,
                        style = MaterialTheme.typography.headlineSmall,
                    )
                }
            }
        }
    }

}


@Composable
fun DescriptionField(
    text: String,
    onValueChange: (String) -> Unit = {}
) {
    IeumBasicTextField(
        text = text,
        onValueChange = onValueChange,
        placeholder = "게시글 내용을 작성해주세요.(상대를 향한 비방글 등 불쾌감을 조성하는 게시글은 삭제될 수 있습니다.)"
    )
}


@Composable
fun CameraField(
    onValueChange: (List<File>) -> Unit
) {
    var images by remember { mutableStateOf<List<Uri>>(emptyList())  }
    val context = LocalContext.current
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia()) {
        images = it
        val fileList = it.map { a -> FileUtil.fileFromContentUri(a, context) }
        onValueChange(fileList)
    }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 100.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ){
        item {
            PhotoImage(
                onClick = { galleryLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )},
                size = images.size
            )
        }
        itemsIndexed(images) { index: Int, item: Uri ->
            AsyncImage(
                model = item,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .border(width = 1.dp, color = color_ebebeb, RoundedCornerShape(4.dp))
            )
        }
    }
}

@Composable
fun PhotoImage(
    size: Int = 0,
    onClick: () -> Unit = {}
){
    Column(
        modifier = Modifier
            .size(100.dp)
            .clip(RoundedCornerShape(4.dp))
            .border(width = 1.dp, color = color_ebebeb, RoundedCornerShape(4.dp))
            .noRippleClickable(onClick = onClick),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(R.drawable.camera),
            contentDescription = "add images",
            modifier = Modifier
                .padding(bottom = 20.dp)
                .size(50.dp)
        )
        Text(
            text = "${size}/3",
            color = color_ebebeb,
            style = MaterialTheme.typography.displaySmall
        )
    }
}



@Composable
fun AddButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier.padding(46.dp, 0.dp, 14.dp, 30.dp)
    ) {
        LongButton("게시글 등록하기", onClick, true)
    }

}
