package com.jeongg.ieum.presentation.content_detail

import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.jeongg.ieum.data._const.DataStoreKey
import com.jeongg.ieum.data._const.Role
import com.jeongg.ieum.data.chat.Chat
import com.jeongg.ieum.data.data_store.IeumDataStore
import com.jeongg.ieum.data.dto.content.ContentDetailResponseDTO
import com.jeongg.ieum.domain.usecase.content.GetContentDetail
import com.jeongg.ieum.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContentDetailViewModel @Inject constructor(
    private val getContentDetail: GetContentDetail,
    private val dataStore: IeumDataStore,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val contentId = mutableLongStateOf(-1L)

    private val _content = mutableStateOf(ContentDetailResponseDTO())
    val content = _content

    private lateinit var database: DatabaseReference

    init {
        savedStateHandle.get<Long>("contentId")?.let{
            contentId.longValue = it
        }
        getContent()
    }

    fun isMentor(): Boolean {
        val role = dataStore.getData(DataStoreKey.ROLE_KEY.name)
        return role == Role.MENTOR.eng
    }
    fun contactMentee() {

        val mentorId = dataStore.getData(DataStoreKey.ID_KEY.name)
        val mentorName = dataStore.getData(DataStoreKey.NICKNAME_KEY.name)
        val menteeId = content.value.menteeId.toString()
        val menteeName = content.value.nickname

        database = Firebase.database.reference
        database.child("chatrooms")
            .child("$contentId-$mentorId-$menteeId")
            .setValue(Chat(mentorId, mentorName, menteeId, menteeName))
    }


    private fun getContent() {
        viewModelScope.launch {
            getContentDetail(contentId.longValue).collect{ response ->
                if (response is Resource.Success) {
                    _content.value = response.data ?: ContentDetailResponseDTO()
                }
            }
        }
    }

}