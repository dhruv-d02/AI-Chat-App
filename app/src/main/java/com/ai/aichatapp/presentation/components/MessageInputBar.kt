package com.ai.aichatapp.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MessageInputBar(
    onSendMessage: (String) -> Unit,
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    var messageText by remember { mutableStateOf("") }

    Surface(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .imePadding()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = messageText,
                onValueChange = { messageText = it },
                enabled = !isLoading,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                placeholder = { Text("Type a message") },
                maxLines = 4
            )

            Button(
                onClick = {
                    val trimmedMessage = messageText.trim()
                    if (trimmedMessage.isNotEmpty()) {
                        onSendMessage(trimmedMessage)
                        messageText = ""
                    }
                },
                enabled = !isLoading
            ) {
                Text(if (isLoading) "Sending..." else "Send")
            }
        }
    }
}
