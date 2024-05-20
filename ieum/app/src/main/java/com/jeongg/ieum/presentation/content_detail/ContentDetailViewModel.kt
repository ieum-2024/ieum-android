package com.jeongg.ieum.presentation.content_detail

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.jeongg.ieum.data.chat.Chat
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContentDetailViewModel @Inject constructor(

): ViewModel() {
    private lateinit var database: DatabaseReference

    fun contactMentee() {

        val postId = 1L
        val mentorId =2L
        val menteeId = 3L

        val user = Chat(mentorId.toString(), "mentor", menteeId.toString(), "mentee")

        database = Firebase.database.reference
        database.child("chatrooms")
            .child("$postId-$mentorId-$menteeId")
            .setValue(user)

    }

}