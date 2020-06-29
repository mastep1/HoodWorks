package com.example.neighborhoodwork.support

import android.content.Context
import com.example.neighborhoodwork.Models.MessageModel

fun adddMessage(context: Context, message: String, date : String, thisUser : Boolean ){

    lateinit var SQL_MESSAGE : SQL_BASE_MESSAGE

    SQL_MESSAGE = SQL_BASE_MESSAGE(context)            //// SQL
    SQL_MESSAGE.insert(MessageModel(message, thisUser,"$date", dane.Contasts[dane.openConversation]))             /// SQL

    dane.messages.add(MessageModel(message, thisUser,"$date", dane.Contasts[dane.openConversation]))             // RAM
}