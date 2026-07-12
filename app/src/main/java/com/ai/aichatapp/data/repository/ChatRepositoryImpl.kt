package com.ai.aichatapp.data.repository

import com.ai.aichatapp.data.remote.OpenAiApiService
import com.ai.aichatapp.data.remote.OpenAiResponseRequest
import com.ai.aichatapp.domain.model.ChatResponseState
import com.ai.aichatapp.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ChatRepositoryImpl(
    private val openAiApiService: OpenAiApiService,
    private val openAiApiKey: String
) : ChatRepository {

    override fun sendMessage(message: String): Flow<ChatResponseState> = flow {
        emit(ChatResponseState.Loading)

        if (openAiApiKey.isBlank()) {
            emit(ChatResponseState.Error("OpenAI API key is missing."))
            return@flow
        }

        try {
            val response = openAiApiService.createResponse(
                authorization = "Bearer $openAiApiKey",
                request = OpenAiResponseRequest(
                    model = OPEN_AI_MODEL,
                    input = message
                )
            )

            val outputText = response.
            outputText()

            if (outputText.isBlank()) {
                emit(ChatResponseState.Error("OpenAI returned an empty response."))
            } else {
                emit(ChatResponseState.Success(outputText))
            }
        } catch (exception: Exception) {
            emit(ChatResponseState.Error("Unable to get a response. Please try again."))
        }
    }

    private companion object {
        const val OPEN_AI_MODEL = "gpt-4.1-mini"
    }
}
