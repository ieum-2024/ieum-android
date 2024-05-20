package com.jeongg.ieum.data.chat


data class Chat(
    val mentorId: String = "",
    val mentorName: String = "",
    val menteeId: String = "",
    val menteeName: String = "",
    var lastMessage: Message = Message()
)
data class Message(
    val senderId: String = "",
    val content: String = "",
    val timestamp: Long = 0L,
)
