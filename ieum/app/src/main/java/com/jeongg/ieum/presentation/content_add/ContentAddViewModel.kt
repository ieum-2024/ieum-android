package com.jeongg.ieum.presentation.content_add

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeongg.ieum.data.dto.content.ContentRequestDTO
import com.jeongg.ieum.data.dto.interest.InterestAllDTO
import com.jeongg.ieum.domain.usecase.content.CreateContent
import com.jeongg.ieum.domain.usecase.interest.GetInterestAll
import com.jeongg.ieum.presentation._util.IeumEvent
import com.jeongg.ieum.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ContentAddViewModel @Inject constructor(
    private val _createContent: CreateContent,
    private val _getInterestAll: GetInterestAll
): ViewModel() {

    private val _content = mutableStateOf(ContentRequestDTO("", -1, ""))
    val content = _content

    private val _interestList = mutableStateOf<List<InterestAllDTO>>(emptyList())
    val interestList = _interestList

    private val image = mutableStateOf<List<File>>(emptyList())

    private val _eventFlow = MutableSharedFlow<IeumEvent>()
    val eventFlow = _eventFlow

    init {
        getInterestAll()
    }

    fun onEvent(event: ContentAddEvent) {
        when (event) {
            is ContentAddEvent.EnterDescription -> {
                _content.value = _content.value.copy(
                    description = event.description
                )
            }
            is ContentAddEvent.EnterInterest -> {
                _content.value = _content.value.copy(
                    subCategory = event.interestId
                )
            }
            is ContentAddEvent.EnterTitle -> {
                _content.value = _content.value.copy(
                    title = event.title
                )
            }
            is ContentAddEvent.EnterImage -> {
                image.value = event.uri
            }
            is ContentAddEvent.SaveContent -> {
                createContent()
            }
        }
    }

    private fun getInterestAll() {
        viewModelScope.launch {
            _getInterestAll().collect { response ->
                when(response) {
                    is Resource.Error -> _eventFlow.emit(IeumEvent.MakeToast(response.message))
                    is Resource.Loading -> _eventFlow.emit(IeumEvent.Loading)
                    is Resource.Success -> { _interestList.value = response.data ?: emptyList() }
                }
            }
        }
    }

    private fun createContent() {
        viewModelScope.launch {
            _createContent(content.value, image.value).collect { response ->
                when (response) {
                    is Resource.Loading -> _eventFlow.emit(IeumEvent.Loading)
                    is Resource.Success -> _eventFlow.emit(IeumEvent.Navigate)
                    is Resource.Error -> _eventFlow.emit(IeumEvent.MakeToast(response.message))
                }
            }
        }
    }
}