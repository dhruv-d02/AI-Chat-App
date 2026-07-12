package com.ai.aichatapp.data.remote

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface OpenAiApiService {
    @POST("responses")
    suspend fun createResponse(
        @Header("Authorization") authorization: String,
        @Body request: OpenAiResponseRequest
    ): OpenAiResponseDto
}
