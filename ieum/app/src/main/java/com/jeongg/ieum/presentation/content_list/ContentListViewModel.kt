package com.jeongg.ieum.presentation.content_list

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.jeongg.ieum.data._const.DataStoreKey
import com.jeongg.ieum.data._const.Role
import com.jeongg.ieum.data.data_store.IeumDataStore
import com.jeongg.ieum.domain.usecase.content.GetContentByInterestId
import com.jeongg.ieum.domain.usecase.interest.GetInterestPrivate
import com.jeongg.ieum.presentation._util.IeumEvent
import com.jeongg.ieum.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ContentListViewModel @Inject constructor(
    private val getContentByInterestId: GetContentByInterestId,
    private val getInterestPrivate: GetInterestPrivate,
    private val dataStore: IeumDataStore,
): ViewModel() {

    private val _state = mutableStateOf(ContentListState())
    val state = _state

    private val _eventFlow = MutableSharedFlow<IeumEvent>()
    val eventFlow = _eventFlow

    init {
        getInterestList()
        if (state.value.interestList.isNotEmpty()) {
            val firstId = state.value.interestList.first().interestId
            getContentList(firstId)
        }
    }

    fun isMentor(): Boolean {
        val role = dataStore.getData(DataStoreKey.ROLE_KEY.name)
        return role == Role.MENTOR.eng
    }

    fun getContentList(interestId: Long) {
        _state.value = _state.value.copy(
            contentList = getContentByInterestId(interestId).cachedIn(viewModelScope)
        )
    }

    private fun getInterestList() {
        getInterestPrivate().onEach { response ->
            _state.value = _state.value.copy(
                isLoading = response is Resource.Loading,
                errorMessage = if (response is Resource.Error) response.message else "",
                interestList = response.data ?: emptyList(),
            )
        }.launchIn(viewModelScope)
    }

}