package com.example.notime.utilities

import com.example.notime.data.FakeDatabase
import com.example.notime.data.MessageRepository
import com.example.notime.ui.messages.MessagesViewModelFactory

object InjectorUtils {

    fun provideMessagesViewModelFactory(): MessagesViewModelFactory {
        val messageRepository = MessageRepository.getInstance(FakeDatabase.getInstance().messageDao)

        return MessagesViewModelFactory(messageRepository)
    }
}