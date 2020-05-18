package com.example.notime.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class FakeMessageDao {
    private val messageList = mutableListOf<Message>()
    private val messages = MutableLiveData<List<Message>>()

    init {
        messages.value = messageList
    }

    fun addMessage(message: Message) {
        messageList.add(message)
        messages.value = messageList
    }

    fun getMessages() = messages as LiveData<List<Message>>
}