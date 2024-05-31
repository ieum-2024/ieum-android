package com.jeongg.ieum.presentation.interest

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeongg.ieum.data.dto.interest.InterestPublicDTO
import com.jeongg.ieum.domain.usecase.interest.CreateInterest
import com.jeongg.ieum.domain.usecase.interest.DeleteInterest
import com.jeongg.ieum.domain.usecase.interest.GetInterestPublic
import com.jeongg.ieum.presentation._util.IeumEvent
import com.jeongg.ieum.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InterestViewModel @Inject constructor(
    private val getInterestPublic: GetInterestPublic,
    private val _createInterest: CreateInterest,
    private val _deleteInterest: DeleteInterest
): ViewModel() {

    private val _interestList = mutableStateOf<List<InterestPublicDTO>>(emptyList())
    val interestList = _interestList

    private val _eventFlow = MutableSharedFlow<IeumEvent>()
    val eventFlow = _eventFlow

    init {
        getInterestList()
    }

    fun createInterest(interestId: Long) {
        viewModelScope.launch{
            _createInterest(interestId).collect { response ->
                when(response) {
                    is Resource.Error -> _eventFlow.emit(IeumEvent.MakeToast(response.message))
                    is Resource.Loading -> _eventFlow.emit(IeumEvent.Loading)
                    is Resource.Success -> {  }
                }
            }
        }
    }

    fun deleteInterest(interestId: Long) {
        viewModelScope.launch{
            _deleteInterest(interestId).collect { response ->
                when(response) {
                    is Resource.Error -> _eventFlow.emit(IeumEvent.MakeToast(response.message))
                    is Resource.Loading -> _eventFlow.emit(IeumEvent.Loading)
                    is Resource.Success -> {  }
                }
            }
        }
    }

    private fun getInterestList() {
        viewModelScope.launch {
            getInterestPublic().collect { response ->
                when(response) {
                    is Resource.Error -> _eventFlow.emit(IeumEvent.MakeToast(response.message))
                    is Resource.Loading -> _eventFlow.emit(IeumEvent.Loading)
                    is Resource.Success -> { _interestList.value = response.data ?: emptyList() }
                }
            }
        }
    }

}