package com.example.neighborhoodwork.Adapters

import android.annotation.SuppressLint
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
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
        dane.indexMessage = 0
                    return ViewHolderV(LayoutInflater.from(parent.context).inflate(R.layout.row_chat_view_me, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolderV, position: Int) {
        when(holder) {

            is ViewHolderV -> {
                holder.initialize(clickListnerV, position)
            }
        }
    }
    override fun getItemCount(): Int {
        return dane.messages.size
    }

    class ViewHolderV
    constructor(itemView: View): RecyclerView.ViewHolder(itemView){


        @SuppressLint("ResourceAsColor")
        fun initialize(action : OnSelectConConversationV, position: Int){


            var i = true;   while(i){
                if(dane.Contasts[dane.openConversation]==dane.messages[dane.indexMessage].user){       /// SPRAWDZA CZY WIADOMOŚĆ NALEŻY DO TEJ KONWERSACJI
                    itemView.tx13Message.text = "${dane.messages[position].message}"                   /// Jeśli należy ustawia widomość na odpowidnią treść
                    if(dane.messages[dane.indexMessage].thisUser==true){                               /// Ustala do kogo należy wiadomość. (wiadomość wysłana / wiadomość przychodząca)
                        itemView.tx13Message.setTextColor(R.color.fontColorMe)
                    };else{
                        itemView.l13Main.gravity = Gravity.END
                        itemView.tx13Message.setTextColor(R.color.fontColorOther)
                    }

                    i = false
                }
                dane.indexMessage ++

            }

            itemView.setOnClickListener {
                action.onItemClickV(itemView.tx13Message.text.toString(), position)
            }
        }

    }

}

interface OnSelectConConversationV{
    fun onItemClickV(selectUser: String, position: Int)
}