package com.jeongg.ieum.presentation.chat_list

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.jeongg.ieum.data.chat.Chat
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(

): ViewModel() {

    private lateinit var database: DatabaseReference
    var chatList : MutableList<Chat> = mutableStateListOf()

    init {
        getChatList()
    }

    fun getOther(chat: Chat): String {
        return if (isMentor()) chat.menteeName
            else chat.mentorName
    }

    private fun isMentor(): Boolean {
        // todo
        return true
    }

    private fun getChatList() {
        database = Firebase.database.reference

        val path = if (isMentor()) "mentorId" else "menteeId"
        val userId = "2"

        database.child("chatrooms")
            .orderByChild(path)
            .equalTo(userId)
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    chatList.clear()
                    for (c in snapshot.children) {
                        val chat = c.getValue(Chat::class.java) ?: Chat()
                        chatList.add(chat)
                    }
                    chatList.sortByDescending { it.lastMessage.timestamp }
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
    }


}