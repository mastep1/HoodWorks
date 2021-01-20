package com.example.neighborhoodwork.Activities

import android.content.Context
import android.os.Bundle
import android.util.JsonWriter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.neighborhoodwork.Adapters.ChatViewAdapter
import com.example.neighborhoodwork.Adapters.OnSelectConConversationV
import com.example.neighborhoodwork.Models.MessageModel
import com.example.neighborhoodwork.R
import com.example.neighborhoodwork.support.*
import com.facebook.internal.Utility
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.util.JsonMapper
import com.google.gson.Gson
import kotlinx.android.synthetic.main.chat_view.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class ChatView : AppCompatActivity(), OnSelectConConversationV {

    private lateinit var auth: FirebaseAuth

    override fun onResume() {
        super.onResume()
        dane.avoid = 0

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_view)

        dane.currentActivity = "ChatView"
        dane.recycler = rc12ChatView

        setHeaderLine()

         rc12ChatView.apply {
        layoutManager = LinearLayoutManager(this@ChatView)
        addItemDecoration(TopSpacingItemDecoration(30))
        adapter = ChatViewAdapter(this@ChatView)
    }

    byReadyForsend()

    }

    fun byReadyForsend(){

        FAB12Send.setOnClickListener {
            sendToInterlocutor()
        }
    }

    fun send(){
        if(Etx12Message.text.toString() != ""){
            val timestamp = System.currentTimeMillis() //pobiera czas jako timestamp

            val date = Date(timestamp) // tworzy obiekt daty na podstawie timestamp


            var message = Etx12Message.text.toString()

            addMessage(this, message, date.time.toString(), true)
            var bazaDane = FirebaseDatabase.getInstance()
            var link = bazaDane.getReference("Users").child("${dane.Contasts[dane.openConversation]}")
                .child("Conversation").child("${date.time}")


            auth = FirebaseAuth.getInstance()
            val currentUser = auth.currentUser
            val name = currentUser!!.displayName.toString()


            var messageToPush = MessageModel(message, false, "${date.time}", name, false )
            link.setValue(messageToPush)

            Etx12Message.setText("")


            dane.avoid = 0
            rc12ChatView.adapter = ChatViewAdapter(this@ChatView)

            //rc12ChatView.scrollToPosition(dane.messages.size-1)
            //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


        }
    }

    fun sendToInterlocutor(){
        var messageNumber : Int = 0

        if(Etx12Message.text.toString() != ""){
            val timestamp = System.currentTimeMillis() //pobiera czas jako timestamp

            val date = Date(timestamp) // tworzy obiekt daty na podstawie timestamp


            var message = Etx12Message.text.toString()

            addMessage(this, message, date.time.toString(), true)



            var dataBaseLink = FirebaseDatabase.getInstance()
            var messagesMeter = dataBaseLink.getReference("Users").child("${dane.Contasts[dane.openConversation]}").child("messagesMeter")
        
            messagesMeter.addValueEventListener(object : ValueEventListener {

                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(p0: DataSnapshot) {

                    messagesMeter.removeEventListener(this)

                    messageNumber = p0.value.toString().toInt()

                    messagesMeter.setValue(messageNumber + 1)

                    var link = dataBaseLink.getReference("Users").child("${dane.Contasts[dane.openConversation]}")
                        .child("Conversation").child("$messageNumber")

                    auth = FirebaseAuth.getInstance()
                    val currentUser = auth.currentUser
                    val name = currentUser!!.displayName.toString()

                    var messageToPush = MessageModel(message, false, "${date.time}", name, false )
                    link.setValue(messageToPush)

                    Etx12Message.setText("")


                    dane.avoid = 0
                    rc12ChatView.adapter = ChatViewAdapter(this@ChatView)

                    sendToYourSelf( MessageModel(message, true, "${date.time}", "${dane.Contasts[dane.openConversation]}", true ), name)


                }
            })
       }

    }

    fun sendToYourSelf(message : MessageModel, userName : String){

        dane.expectedMessage ++

        var dataBaseLink = FirebaseDatabase.getInstance()

        var link2 = dataBaseLink.getReference("Users").child(userName)
            .child("Conversation").child("${dane.messages.size}")


        link2.setValue(message)

        var linkForMessageIndex = dataBaseLink.getReference("Users").child(userName).child("messagesMeter")

        linkForMessageIndex.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {

                linkForMessageIndex.removeEventListener(this)

                var messageIndex = p0.value.toString().toInt()

                linkForMessageIndex.setValue(messageIndex + 1)
            }
        })
    }



    fun setHeaderLine(){
        tx12Imie.text = dane.Contasts[dane.openConversation]

        BT12Back.setOnClickListener {
            finish()
        }
    }

    override fun onItemClickV(selectUser: String, position: Int) {
                        Toast.makeText(applicationContext,"$selectUser,", Toast.LENGTH_LONG).show()
    }

}


