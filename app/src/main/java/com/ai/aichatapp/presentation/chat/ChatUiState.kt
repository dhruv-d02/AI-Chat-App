package com.ai.aichatapp.presentation.chat

data class ChatUiState(
    val messages: List<ChatUiMessage> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
