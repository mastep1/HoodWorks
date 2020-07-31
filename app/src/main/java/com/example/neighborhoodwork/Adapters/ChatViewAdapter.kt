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
                    return ViewHolderV(LayoutInflater.from(parent.context).inflate(R.layout.row_chat_view_me, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolderV, position: Int) {
        when(holder) {

            is ViewHolderV -> {
                    holder.initialize2(clickListnerV, position)

            }
        }
    }
    override fun getItemCount(): Int {
        var howManyMessage =  sayHowMany(dane.Contasts[dane.openConversation])
        return howManyMessage
    }

    private fun sayHowMany(openConversation : String): Int{
        var i = 0
        var toReturn = 0
        while(i < dane.messages.size)
        {
            if(dane.messages[i].user==openConversation){
                 toReturn++
            }
            i++
        }
        return toReturn
    }

    class ViewHolderV
    constructor(itemView: View): RecyclerView.ViewHolder(itemView){

        fun initialize2(action : OnSelectConConversationV, position: Int){
            var find = false
             while(find==false){
                 if(dane.messages[dane.avoid].user==dane.Contasts[dane.openConversation]){
                     itemView.tx13Message.text = dane.messages[dane.avoid].message
                      find = true
                     if(dane.messages[dane.avoid].thisUser==false){
                         itemView.l13Main.gravity = Gravity.END
                     }
                }
                 dane.avoid ++

             }

        }





        @SuppressLint("ResourceAsColor")
        fun initialize(action : OnSelectConConversationV, position: Int){


            var i = true;   while(i){
                if(dane.Contasts[dane.openConversation]==dane.messages[dane.avoid].user&&dane.avoid==6){       /// SPRAWDZA CZY WIADOMOŚĆ NALEŻY DO TEJ KONWERSACJI
                    itemView.tx13Message.text = "${dane.messages[dane.avoid].message}"
                    i = false
                    /*                                                                                   /// Jeśli tak, ustawia widomość na odpowidnią treść
                   if(dane.messages[dane.indexMessage].thisUser==true){                               /// Ustala do kogo należy wiadomość. (wiadomość wysłana / wiadomość przychodząca)
                       itemView.tx13Message.setTextColor(R.color.fontColorMe)
                       Toast.makeText(itemView.context, "Start", Toast.LENGTH_LONG).show()
                   };else{
                       itemView.setPadding(100, 0, 0 , 0)

                       itemView.tx13Message.setTextColor(R.color.fontColorOther)
                       Toast.makeText(itemView.context, "END", Toast.LENGTH_LONG).show()
                   }
                   */
                }
                dane.avoid ++
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