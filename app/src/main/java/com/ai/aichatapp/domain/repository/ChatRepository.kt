package com.ai.aichatapp.domain.repository

import com.ai.aichatapp.domain.model.ChatResponseState
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun sendMessage(message: String): Flow<ChatResponseState>
}
