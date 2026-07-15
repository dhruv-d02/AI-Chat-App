package com.ai.aichatapp.presentation.chat

import com.ai.aichatapp.di.AppModule
import com.ai.aichatapp.domain.model.ChatResponseState
import com.ai.aichatapp.domain.usecase.SendMessageUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatViewModel(
    private val sendMessageUseCase: SendMessageUseCase = AppModule.sendMessageUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    fun onSendMessage(text: String) {
        val trimmedText = text.trim()

        if (trimmedText.isBlank()) return

        addMessage(
            text = trimmedText,
            sender = MessageSender.User
        )

        viewModelScope.launch {
            sendMessageUseCase(trimmedText).collect { responseState ->
                when (responseState) {
                    ChatResponseState.Loading -> {
                        _uiState.update { currentState ->
                            currentState.copy(
                                isLoading = true,
                                errorMessage = null
                            )
                        }
                    }

                    is ChatResponseState.Success -> {
                        addMessage(
                            text = responseState.message,
                            sender = MessageSender.Assistant
                        )
                        _uiState.update { currentState ->
                            currentState.copy(isLoading = false)
                        }
                    }

                    is ChatResponseState.Error -> {
                        _uiState.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                errorMessage = responseState.message
                            )
                        }
                    }
                }
            }
        }
    }

    private fun addMessage(
        text: String,
        sender: MessageSender
    ) {
        _uiState.update { currentState ->
            val nextMessage = ChatUiMessage(
                id = currentState.messages.size.toLong() + 1L,
                text = text,
                sender = sender
            )

            currentState.copy(
                messages = currentState.messages + nextMessage
            )
        }
    }
}
