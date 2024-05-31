package com.jeongg.ieum.presentation.chat_list

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.jeongg.ieum.data._const.DataStoreKey
import com.jeongg.ieum.data._const.Role
import com.jeongg.ieum.data.chat.Chat
import com.jeongg.ieum.data.chat.ChatRoom
import com.jeongg.ieum.data.data_store.IeumDataStore
import com.jeongg.ieum.util.log
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val dataStore: IeumDataStore
): ViewModel() {

    private lateinit var database: DatabaseReference
    var chatList : MutableList<ChatRoom> = mutableStateListOf()

    init {
        getChatList()
    }

    fun getOther(chat: Chat): String {
        return if (isMentor()) chat.menteeName else chat.mentorName
    }

    private fun isMentor(): Boolean {
        val role = dataStore.getData(DataStoreKey.ROLE_KEY.name)
        return role == Role.MENTOR.eng
    }

    private fun getChatList() {
        database = Firebase.database.reference

        val path = if (isMentor()) "mentorId" else "menteeId"
        val userId =  dataStore.getData(DataStoreKey.ID_KEY.name)

        database.child("chatrooms")
            .orderByChild(path)
            .equalTo(userId)
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    chatList.clear()
                    for (c in snapshot.children) {
                        val chatId = c.key ?: ""
                        val chat = c.getValue(Chat::class.java) ?: Chat()
                        chatList.add(ChatRoom(chatId, chat))
                    }
                    chatList.sortByDescending { it.chat.lastMessage.timestamp }
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
    }


}