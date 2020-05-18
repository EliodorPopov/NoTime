package com.example.notime.data

class MessageRepository  private constructor(private val messageDao: FakeMessageDao){

    fun addMessage(message: Message) {
        messageDao.addMessage(message)
    }

    fun getMessages() = messageDao.getMessages()

    companion object {
        @Volatile private var instance: MessageRepository? = null

        fun getInstance(messageDao: FakeMessageDao) =
            instance ?: synchronized(this){
                instance ?: MessageRepository(messageDao).also { instance = it }
            }
    }

}