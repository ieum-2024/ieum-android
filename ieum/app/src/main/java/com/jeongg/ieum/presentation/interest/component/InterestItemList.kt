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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jeongg.ieum.data.dto.interest.SubTopic
import com.jeongg.ieum.presentation._common.Divider
import com.jeongg.ieum.presentation._common.ShortButton

@Composable
fun InterestItemList(
    mainCategory: String = "",
    subCategory: List<SubTopic> = emptyList(),
    onAddClick: (Long) -> Unit,
    onDeleteClick: (Long) -> Unit
) {
    Column {
        MainCategory(mainCategory)
        SubCategory(subCategory, onAddClick, onDeleteClick)
        Divider(Modifier.padding(vertical = 20.dp))
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
private fun SubCategory(
    subCategory: List<SubTopic>,
    onAddClick: (Long) -> Unit,
    onDeleteClick: (Long) -> Unit
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        subCategory.forEach { category ->
            val isActive = rememberSaveable { mutableStateOf(category.isActive) }
            ShortButton(
                text = category.name,
                isSelected = isActive.value,
                onClick = {
                    if (isActive.value) onDeleteClick(category.interestId)
                    else onAddClick(category.interestId)

                    isActive.value = isActive.value.not()
                }
            )
        }
    }
}