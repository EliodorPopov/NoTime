package com.example.notime

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.notime.data.FakeMessageDao
import com.example.notime.data.Message
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

public class ExampleUnitTest {
    @Rule @JvmField
    var  rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun message_to_string_works() {
        // Arrange
        val text = "this is a message text"
        val message = Message(text)

        // Act
        val messageToString = message.toString()

        // Assert
        assertEquals(messageToString, text)
    }

    @Test
    fun messageDao_adds_message() {
        // Arrange
        val messageDao = FakeMessageDao()
        val testMessage = Message("test message");

        // Act
        messageDao.addMessage(testMessage);

        // Assert
        assertEquals(1, messageDao.getMessages().value?.size)
        assertEquals(testMessage.messageText, messageDao.getMessages().value?.first()?.messageText)
    }

    @Test
    fun messageDao_gets_messages() {
        // Arrange
        val messageDao = FakeMessageDao()
        val testMessage = Message("test message");
        val testMessage2 = Message("test message 2")

        messageDao.addMessage(testMessage);
        messageDao.addMessage(testMessage2);

        assertEquals(2, messageDao.getMessages().value?.size)
        assertEquals(testMessage.messageText, messageDao.getMessages().value?.first()?.messageText)
        assertEquals(testMessage2.messageText, messageDao.getMessages().value?.first { x -> x.messageText == testMessage2.messageText }?.messageText)
    }
}
