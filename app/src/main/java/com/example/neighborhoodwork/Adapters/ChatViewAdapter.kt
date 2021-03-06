package com.example.neighborhoodwork.Adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.neighborhoodwor.ZadanieModel
import com.example.neighborhoodwork.Models.MessageModel
import com.example.neighborhoodwork.R
import com.example.neighborhoodwork.support.dane
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.chat_view.*
import kotlinx.android.synthetic.main.row_chat.view.*
import kotlinx.android.synthetic.main.row_chat_view_me.view.*
import kotlin.coroutines.coroutineContext


class ChatViewAdapter(var clickListnerV: OnSelectConConversationV) : RecyclerView.Adapter<ChatViewAdapter.ViewHolderV>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderV {

                    return ViewHolderV(LayoutInflater.from(parent.context).inflate(R.layout.row_chat_view_me, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolderV, position: Int) {
        when(holder){

            is ViewHolderV -> {
                    holder.initialize2(clickListnerV, position)
            }
        }
    }
    override fun getItemCount(): Int {
        var howManyMessage =  dane.messagesOfSpecificUser.size - 1
        return howManyMessage
    }




    class ViewHolderV
    constructor(itemView: View): RecyclerView.ViewHolder(itemView){

        fun initialize2(action : OnSelectConConversationV, position: Int){
            Log.e("FuckingError", "pozycja $position na ${dane.messagesOfSpecificUser.size - 1}")
            itemView.tx13Message.text = dane.messagesOfSpecificUser[position].message

            if(!dane.messagesOfSpecificUser[position].thisUser){
                itemView.l13Main.gravity = Gravity.END
            }
       }

    }

}

interface OnSelectConConversationV{
    fun onItemClickV(selectUser: String, position: Int)
}
