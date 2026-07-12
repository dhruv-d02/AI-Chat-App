package com.ai.aichatapp.domain.usecase

import com.ai.aichatapp.domain.model.ChatResponseState
import com.ai.aichatapp.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow

class SendMessageUseCase(
    private val chatRepository: ChatRepository
) {
    operator fun invoke(message: String): Flow<ChatResponseState> {
        return chatRepository.sendMessage(message)
    }
}
