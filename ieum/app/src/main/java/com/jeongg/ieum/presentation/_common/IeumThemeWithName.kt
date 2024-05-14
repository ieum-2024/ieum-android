package com.jeongg.ieum.presentation._common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jeongg.ieum.ui.theme.Dimens

@Composable
fun IeumThemeWithName(
    title: String = "",
    content: @Composable() (LazyItemScope.() -> Unit)
) {
    LazyColumn(
        contentPadding = PaddingValues(Dimens.NormalPadding),
    ){
        item { Title(title) }
        item { content() }
    }

}

@Composable
private fun Title(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge
    )
    Divider(Modifier.padding(top = 8.dp, bottom = 30.dp))
}