package com.example.notime.ui.messages

import androidx.lifecycle.ViewModel
import com.example.notime.data.Message
import com.example.notime.data.MessageRepository

class MessagesViewModel(private val messageRepository: MessageRepository)
    : ViewModel() {
    fun getMessages() = messageRepository.getMessages()

    fun addMessage(message: Message) = messageRepository.addMessage(message)
}