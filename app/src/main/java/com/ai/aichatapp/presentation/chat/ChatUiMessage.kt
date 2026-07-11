package com.ai.aichatapp.presentation.chat

data class ChatUiMessage(
    val id: Long,
    val text: String,
    val sender: MessageSender
)

enum class MessageSender {
    User,
    Assistant
}
