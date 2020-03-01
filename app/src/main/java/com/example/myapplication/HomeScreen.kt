package com.example.myapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.home_screen.*
import kotlinx.android.synthetic.main.chat_message_incoming.view.*
import kotlin.random.Random

class HomeScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_screen)

        val adapter = GroupAdapter<ViewHolder>()
        chat_view.adapter = adapter

        send_button.setOnClickListener{
            val text = message_input_field.text.toString()
            message_input_field.text.clear()
            if (Random.nextBoolean()){
            adapter.add(ChatIncomingItem(text))
            } else {
                adapter.add((ChatOutgoingItem(text)))
            }
            chat_view.smoothScrollToPosition(adapter.itemCount - 1);
        }

        adapter.add(ChatIncomingItem("test"))
        adapter.add(ChatIncomingItem("test"))
        adapter.add(ChatIncomingItem("test"))
        adapter.add(ChatIncomingItem("test"))
        adapter.add(ChatIncomingItem("test"))
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}

class ChatIncomingItem(val text: String): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int){
        viewHolder.itemView.textView.text = text
    }

    override fun getLayout(): Int {
        return R.layout.chat_message_incoming
    }
}

class ChatOutgoingItem(val text: String): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int){
        viewHolder.itemView.textView.text = text
    }

    override fun getLayout(): Int {
        return R.layout.chat_message_outgoing
    }
}
