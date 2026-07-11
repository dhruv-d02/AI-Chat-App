package com.ai.aichatapp.presentation.chat

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ChatViewModel : ViewModel() {

    private val _messages = MutableStateFlow<List<ChatUiMessage>>(emptyList())
    val messages: StateFlow<List<ChatUiMessage>> = _messages.asStateFlow()

    fun onSendMessage(text: String) {
        val trimmedText = text.trim()

        if (trimmedText.isBlank()) return

        _messages.update { currentMessages ->
            currentMessages + ChatUiMessage(
                id = currentMessages.size.toLong() + 1L,
                text = trimmedText,
                sender = MessageSender.User
            )
        }
    }
}
