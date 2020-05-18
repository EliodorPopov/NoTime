package com.example.notime.data

class FakeDatabase private constructor() {
    var messageDao = FakeMessageDao()
        private set

    companion object {
        @Volatile private var instance: FakeDatabase? = null

        fun getInstance() =
            instance ?: synchronized(this){
                instance ?: FakeDatabase().also { instance = it }
            }
    }

}