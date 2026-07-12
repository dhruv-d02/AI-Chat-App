package com.ai.aichatapp.data.remote

import com.google.gson.annotations.SerializedName

data class OpenAiResponseDto(
    val output: List<OpenAiOutputDto> = emptyList()
) {
    fun outputText(): String {
        return output
            .flatMap { it.content }
            .firstOrNull { it.type == "output_text" }
            ?.text
            .orEmpty()
    }
}

data class OpenAiOutputDto(
    val content: List<OpenAiOutputContentDto> = emptyList()
)

data class OpenAiOutputContentDto(
    val type: String = "",
    @SerializedName("text")
    val text: String = ""
)
