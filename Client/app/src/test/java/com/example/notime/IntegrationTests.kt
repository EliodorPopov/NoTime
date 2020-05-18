package com.example.notime

import com.example.notime.ui.messages.ChatIncomingItem
import com.microsoft.signalr.HubConnectionBuilder
import com.microsoft.signalr.HubConnectionState
import junit.framework.Assert.assertEquals
import org.junit.Assert.*
import org.junit.Test

class IntegrationTests {
    @Test
    fun connection_is_successful() {
        var hubConnection = HubConnectionBuilder.create("http://192.168.1.12:5000/chathub").build()
        hubConnection.start().blockingAwait()

        assertEquals(hubConnection.connectionState, HubConnectionState.CONNECTED)
    }

    @Test
    fun message_is_sent_and_received() {

        // Arrange
        val hubConnection1 = HubConnectionBuilder.create("http://192.168.1.12:5000/chathub").build()
        val hubConnection2 = HubConnectionBuilder.create("http://192.168.1.12:5000/chathub").build()
        hubConnection1.start().blockingAwait()
        hubConnection2.start().blockingAwait()
        val testMessage = "test message"
        var receivedMessage = ""

        hubConnection2.on("ReceiveNewMessage", {message ->
            receivedMessage = message;
        }, String::class.java);

        //Act
        hubConnection1.send("MessageFromServer", testMessage)

        while(receivedMessage == "") { }

        //Assert
        assertEquals(testMessage, receivedMessage)
    }

    @Test
    fun message_is_sent_and_not_received() {
        // Arrange
        val hubConnection1 = HubConnectionBuilder.create("http://192.168.1.12:5000/chathub").build()
        val hubConnection2 = HubConnectionBuilder.create("http://192.168.1.12:5000/chathub").build()
        hubConnection1.start().blockingAwait()
        hubConnection2.start().blockingAwait()
        val testMessage = "test message"
        var receivedMessage = ""

        hubConnection2.on("InvalidMethod", {message ->
            receivedMessage = message;
        }, String::class.java);

        //Act
        hubConnection1.send("MessageFromServer", testMessage)

        while(receivedMessage == "") { }

        //Assert
        assertEquals(testMessage, receivedMessage)
    }

    @Test
    fun message_is_not_sent() {
        // Arrange
        val hubConnection1 = HubConnectionBuilder.create("http://192.168.1.12:5000/chathub").build()
        val hubConnection2 = HubConnectionBuilder.create("http://192.168.1.12:5000/chathub").build()
        hubConnection1.start().blockingAwait()
        hubConnection2.start().blockingAwait()
        val testMessage = "test message"
        var receivedMessage = ""
        hubConnection2.on("ReceiveNewMessage", {message ->
            receivedMessage = message;
        }, String::class.java)

        //Act
        hubConnection1.send("MessageFromServer", testMessage)

        while(receivedMessage == "") { }

        //Assert
        assertEquals(testMessage, receivedMessage)
    }
}