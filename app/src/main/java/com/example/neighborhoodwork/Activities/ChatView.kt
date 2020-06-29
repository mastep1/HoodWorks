package com.example.neighborhoodwork.Activities

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.neighborhoodwork.Adapters.ChatViewAdapter
import com.example.neighborhoodwork.Adapters.OnSelectConConversationV
import com.example.neighborhoodwork.Models.MessageModel
import com.example.neighborhoodwork.R
import com.example.neighborhoodwork.support.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.chat_view.*
import java.util.*

class ChatView : AppCompatActivity(), OnSelectConConversationV {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_view)
        
        
        setHeaderLine()

        rc12ChatView.apply {
            layoutManager = LinearLayoutManager(this@ChatView)
            addItemDecoration(TopSpacingItemDecoration(30))
            adapter = ChatViewAdapter(this@ChatView)
        }

        send()
    }

    fun send(){

        FAB12Send.setOnClickListener {

            if(Etx12Message.text.toString() != ""){
                val timestamp = System.currentTimeMillis() //pobiera czas jako timestamp

                val date = Date(timestamp) // tworzy obiekt daty na podstawie timestamp


                var message = Etx12Message.text.toString()

                adddMessage(this, message, date.time.toString(), true)

                var bazaDane = FirebaseDatabase.getInstance()
                var link =
                    bazaDane.getReference("Users").child("${dane.Contasts[dane.openConversation]}")
                        .child("Conversation").child("${date.time}")


                auth = FirebaseAuth.getInstance()
                val currentUser = auth.currentUser
                val name = currentUser!!.displayName.toString()


                var messageToPush = MessageModel(message, false, "${date.time}", name)
                link.setValue(messageToPush)

                Etx12Message.setText("")



                rc12ChatView.adapter = ChatViewAdapter(this@ChatView)

                rc12ChatView.scrollToPosition(dane.messages.size-1)
            }

        }

    }

    fun setHeaderLine(){
        tx12Imie.text = dane.Contasts[dane.openConversation]
    }

    override fun onItemClickV(selectUser: String, position: Int) {
                        Toast.makeText(applicationContext,"$selectUser,", Toast.LENGTH_LONG).show()
    }

}


