package com.jeongg.ieum.presentation.content_add

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.jeongg.ieum.R
import com.jeongg.ieum.presentation._common.Divider
import com.jeongg.ieum.presentation._common.IeumBasicTextField
import com.jeongg.ieum.presentation._common.IeumThemeWithName
import com.jeongg.ieum.presentation._common.LongButton
import com.jeongg.ieum.presentation._common.noRippleClickable
import com.jeongg.ieum.presentation._navigation.Screen
import com.jeongg.ieum.ui.theme.color_808080
import com.jeongg.ieum.ui.theme.color_ebebeb

@Composable
fun ContentAddScreen(
    navController: NavController
) {
    IeumThemeWithName("멘토 구하기") {
        TitleField()
        CategoryField()
        DescriptionField()
        CameraField()
        AddButton{ navController.navigate(Screen.ContentListScreen.route) }
    }

}

@Composable
fun TitleField() {
    IeumBasicTextField(placeholder = "제목")
    Divider()
}

@Composable
fun CategoryField() {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("안녕하세요") }
    val category = listOf("안녕하세요", "감사해요", "취업/진로")
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
                modifier = Modifier.padding(start = 10.dp).height(7.dp)
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            category.forEach {
                DropdownMenuItem(onClick = {
                    selectedItem =  it
                    expanded = expanded.not()
                }) {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.headlineSmall,
                    )
                }
            }
        }
    }

}


@Composable
fun DescriptionField() {
    IeumBasicTextField(placeholder = "게시글 내용을 작성해주세요.(상대를 향한 비방글 등 불쾌감을 조성하는 게시글은 삭제될 수 있습니다.)")
}

@Composable
fun CameraField() {
    var images by remember { mutableStateOf<List<Uri>>(emptyList())  }
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia()) {
        images = it
    }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 120.dp, bottom = 50.dp),
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
    LongButton("게시글 등록하기", onClick, true)
}
