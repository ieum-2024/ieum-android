package com.jeongg.ieum.presentation.chat_detail

import android.os.Build
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.jeongg.ieum.data._const.DataStoreKey
import com.jeongg.ieum.data.chat.Message
import com.jeongg.ieum.data.data_store.IeumDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.inject.Inject

@HiltViewModel
class ChatDetailViewModel @Inject constructor(
    private val dataStore: IeumDataStore,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private lateinit var database: DatabaseReference
    private val chatId = mutableStateOf("")

    val nickname = mutableStateOf("")
    var messages: MutableList<Message> = mutableStateListOf()
    var isAdd: MutableState<Boolean> = mutableStateOf(false)

    init {
        savedStateHandle.get<String>("chatId")?.let{
            chatId.value = it
        }
        savedStateHandle.get<String>("nickname")?.let{
            nickname.value = it
        }
        getChattingInfo()
    }

    private fun getChattingInfo() {
        database = Firebase.database.reference
        database.child("chatrooms")
            .child(chatId.value)
            .child("message")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    messages.clear()
                    for (c in snapshot.children) {
                        messages.add(c.getValue(Message::class.java) ?: Message("", ""))
                    }
                    messages.sortBy { it.timestamp }
                    updateLastMessage()
                    isAdd.value = true
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
    }

    private fun updateLastMessage() {
        if (messages.isEmpty()) return
        database.child("chatrooms")
            .child(chatId.value)
            .child("lastMessage").setValue(messages.last())
    }

    fun chageAddState() {
        isAdd.value = false
    }

    fun send(text: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val senderId = dataStore.getData(DataStoreKey.ID_KEY.name)
            val message = Message(senderId, text, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
            database = Firebase.database.reference
            database.child("chatrooms")
                .child(chatId.value)
                .child("message").push().setValue(message)
        }
    }
}