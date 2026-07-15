package com.ai.aichatapp.presentation.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ai.aichatapp.presentation.components.MessageBubble
import com.ai.aichatapp.presentation.components.MessageInputBar
import com.ai.aichatapp.ui.theme.AIChatAppTheme

@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    viewModel: ChatViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    ChatContent(
        uiState = uiState,
        onSendMessage = viewModel::onSendMessage,
        modifier = modifier
    )
}

@Composable
fun ChatContent(
    uiState: ChatUiState,
    onSendMessage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(uiState.messages, key = { it.id }) { message ->
                MessageBubble(message = message)
            }
        }

        uiState.errorMessage?.let { errorMessage ->
            Text(
                text = errorMessage,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        MessageInputBar(
            onSendMessage = onSendMessage,
            isLoading = uiState.isLoading
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ChatScreenPreview() {
    AIChatAppTheme {
        ChatContent(
            uiState = ChatUiState(
                messages = listOf(
                    ChatUiMessage(
                        id = 1L,
                        text = "Preview user message",
                        sender = MessageSender.User
                    ),
                    ChatUiMessage(
                        id = 2L,
                        text = "Preview assistant message",
                        sender = MessageSender.Assistant
                    )
                )
            ),
            onSendMessage = {}
        )
    }
}
