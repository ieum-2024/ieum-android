package com.jeongg.ieum.domain.usecase.content

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.jeongg.ieum.data._const.PagingConst
import com.jeongg.ieum.data.dto.content.Content
import com.jeongg.ieum.data.paging.ContentPagingSource
import com.jeongg.ieum.domain.repository.ContentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetContentByInterestId @Inject constructor(
    private val contentRepository: ContentRepository
) {
    operator fun invoke(interestId: Long?): Flow<PagingData<Content>> {
        return Pager(
            config = PagingConfig(
                enablePlaceholders = false,
                pageSize = PagingConst.PER_PAGE_SIZE.value,
                prefetchDistance = PagingConst.PREFETCH_DISTANCE.value
            ),
            pagingSourceFactory = { ContentPagingSource(contentRepository, interestId) }
        ).flow
    }
}