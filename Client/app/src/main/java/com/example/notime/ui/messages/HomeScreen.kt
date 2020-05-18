package com.example.notime.ui.messages

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.notime.R
import com.example.notime.data.Message
import com.example.notime.utilities.InjectorUtils
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import com.microsoft.signalr.HubConnectionState
import com.xwray.groupie.Group
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.chat_message_incoming.*
import kotlinx.android.synthetic.main.home_screen.*
import kotlinx.android.synthetic.main.chat_message_incoming.view.*
import java.lang.StringBuilder

class HomeScreen : AppCompatActivity() {

    lateinit var hubConnection: HubConnection;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_screen)
        var adapter = GroupAdapter<ViewHolder>()
        initializeUi(adapter)
    }

    private fun initializeUi(adapter: GroupAdapter<ViewHolder>) {
        val factory = InjectorUtils.provideMessagesViewModelFactory()
        val viewModel = ViewModelProviders.of(this, factory)
            .get(MessagesViewModel::class.java)

        viewModel.getMessages().observe(this, Observer { messages ->
            messages.forEach{message ->
                adapter.add(ChatIncomingItem(message.toString()))
            }
        })

        send_button.setOnClickListener {
            val message = Message(message_input_field.text.toString())
        }

        chat_view.adapter = adapter

        hubConnection = HubConnectionBuilder.create("http://10.0.2.2:5000/chathub").build();
        hubConnection.on("ReceiveNewMessage", {message ->
            adapter.add(ChatIncomingItem(message));
        }, String::class.java);

        if (hubConnection.connectionState == HubConnectionState.DISCONNECTED)
            hubConnection.start();

        send_button.setOnClickListener{
            val text = message_input_field.text.toString()
            message_input_field.text.clear()
            adapter.add(ChatOutgoingItem(text))

            if (hubConnection.connectionState == HubConnectionState.CONNECTED)
                hubConnection.send("MessageFromServer", text)
            else {
                Toast.makeText(
                    applicationContext,
                    "" + hubConnection.connectionState,
                    Toast.LENGTH_SHORT
                ).show();
            }

            chat_view.smoothScrollToPosition(adapter.itemCount - 1);
        }
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