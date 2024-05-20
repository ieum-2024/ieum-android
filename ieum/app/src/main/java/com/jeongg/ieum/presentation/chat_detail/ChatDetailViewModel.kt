package com.jeongg.ieum.presentation.chat_detail

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.jeongg.ieum.data.chat.Message
import com.jeongg.ieum.presentation._util.log
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.inject.Inject

@HiltViewModel
class ChatDetailViewModel @Inject constructor(

): ViewModel() {

    private lateinit var database: DatabaseReference
    var messages: MutableList<Message> = mutableStateListOf()
    var isAdd: MutableState<Boolean> = mutableStateOf(false)
    init {
        getChattingInfo()
    }

    private fun getChattingInfo() {
        database = Firebase.database.reference
        database.child("chatrooms")
            .child("1-2-3")
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
                    "canceled".log()
                }

            })
    }

    private fun updateLastMessage() {
        if (messages.isEmpty()) return
        database.child("chatrooms")
            .child("1-2-3")
            .child("lastMessage").setValue(messages.last())
    }

    fun chageAddState() {
        isAdd.value = false
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun send(text: String) {
        val message = Message("3", text, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
        database = Firebase.database.reference
        database.child("chatrooms")
            .child("1-2-3")
            .child("message").push().setValue(message)
    }
}