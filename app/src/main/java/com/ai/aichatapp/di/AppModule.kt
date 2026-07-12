package com.ai.aichatapp.di

import com.ai.aichatapp.BuildConfig
import com.ai.aichatapp.data.remote.OpenAiApiService
import com.ai.aichatapp.data.remote.RetrofitServiceFactory
import com.ai.aichatapp.data.repository.ChatRepositoryImpl
import com.ai.aichatapp.domain.repository.ChatRepository
import com.ai.aichatapp.domain.usecase.SendMessageUseCase

object AppModule {
    private const val OPEN_AI_BASE_URL = "https://api.openai.com/v1/"

    val openAiApiService: OpenAiApiService =
        RetrofitServiceFactory.createService(
            serviceClass = OpenAiApiService::class.java,
            baseUrl = OPEN_AI_BASE_URL
        )

    val openAiApiKey: String = BuildConfig.OPENAI_API_KEY

    val chatRepository: ChatRepository =
        ChatRepositoryImpl(
            openAiApiService = openAiApiService,
            openAiApiKey = openAiApiKey
        )

    val sendMessageUseCase: SendMessageUseCase =
        SendMessageUseCase(chatRepository)
}
