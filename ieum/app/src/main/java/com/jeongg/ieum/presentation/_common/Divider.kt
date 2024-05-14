package com.jeongg.ieum.presentation._common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jeongg.ieum.ui.theme.color_ebebeb

@Composable
fun Divider(
    modifier: Modifier = Modifier
){
    HorizontalDivider(
        modifier = modifier.fillMaxWidth(),
        thickness = 1.dp,
        color = color_ebebeb
    )
}