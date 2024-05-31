package com.jeongg.ieum.presentation.content_list

import androidx.paging.PagingData
import com.jeongg.ieum.data.dto.content.Content
import com.jeongg.ieum.data.dto.interest.InterestPrivateDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

data class ContentListState (
    val isLoading: Boolean = true,
    val interestList: List<InterestPrivateDTO> = emptyList(),
    val contentList: Flow<PagingData<Content>> = flow {},
    val errorMessage: String = "",
)