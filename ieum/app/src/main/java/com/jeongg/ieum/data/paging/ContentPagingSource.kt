package com.jeongg.ieum.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jeongg.ieum.data.dto.common.ApiUtils
import com.jeongg.ieum.data.dto.content.Content
import com.jeongg.ieum.data.dto.content.ContentListResponseDTO
import com.jeongg.ieum.domain.repository.ContentRepository
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode

class ContentPagingSource(
    private val contentRepository: ContentRepository,
    private val interestId: Long?
): PagingSource<String, Content>() {
    override fun getRefreshKey(state: PagingState<String, Content>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPageIndex = state.pages.indexOf(state.closestPageToPosition(anchorPosition))
            state.pages.getOrNull(anchorPageIndex - 1)?.nextKey
        }
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Content> {
        return try {
            val cursor = params.key
            val response = contentRepository.getContentByInterestId(interestId, cursor)
            val body = response.body<ApiUtils.ApiResult<ContentListResponseDTO>>()
            val contents = body.response?.contentList ?: emptyList()
            val errorMessage = body.error?.message ?: "게시글 리스트를 불러오는데 실패하였습니다."

            if (response.status == HttpStatusCode.OK && body.success) {
                val hasNext = body.response?.cursor?.hasNext .let { it == true }
                LoadResult.Page(
                    data = contents,
                    prevKey = cursor,
                    nextKey = if(hasNext) body.response?.cursor?.cursor else null
                )
            }
            else LoadResult.Error(Exception(errorMessage))
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}