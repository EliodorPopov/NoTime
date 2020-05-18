package com.example.notime.data

data class Message(val messageText: String) {
    override fun toString(): String {
        return messageText;
    }
}