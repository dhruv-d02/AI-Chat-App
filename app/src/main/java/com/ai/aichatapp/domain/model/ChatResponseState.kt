package com.ai.aichatapp.domain.model

sealed interface ChatResponseState {
    data object Loading : ChatResponseState
    data class Success(val message: String) : ChatResponseState
    data class Error(val message: String) : ChatResponseState
}
