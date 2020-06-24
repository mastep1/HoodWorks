package com.example.neighborhoodwork.Adapters



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.neighborhoodwork.R
import com.example.neighborhoodwork.support.dane
import kotlinx.android.synthetic.main.row_chat.view.*


class AdapterChat(var clickListner: OnSelectConConversation) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(
                R.layout.row_chat,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return dane.Contasts.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.initialize(clickListner, position)
    }

}

class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun initialize(action : OnSelectConConversation, position: Int){
        itemView.setOnClickListener {
            action.onItemClick(itemView.tx9Imie.text.toString())
        }
        itemView.tx9Imie.text = dane.Contasts[position]
    }


}

interface OnSelectConConversation{
    fun onItemClick(selectUser: String)
}