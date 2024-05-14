package com.jeongg.ieum.presentation.interest.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jeongg.ieum.presentation._common.Divider
import com.jeongg.ieum.presentation._common.ShortButton

@Composable
fun InterestItemList(
    mainCategory: String = "",
    subCategory: List<String> = emptyList()
) {
    Column {
        MainCategory(mainCategory)
        SubCategory(subCategory)
        Divider(Modifier.padding(vertical = 36.dp))
    }
}

@Composable
private fun MainCategory(mainCategory: String) {
    Text(
        text = mainCategory,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(bottom = 16.dp)
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SubCategory(subCategory: List<String>) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        subCategory.forEach { category ->
            ShortButton(category, {})
        }
    }
}